/*******************************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *******************************************************************************/
package com.datarpm.sigma.workflow;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.IClassBodyEvaluator;

import com.datarpm.sigma.workflow.WorkflowExecutionPlan.Builder;
import com.datarpm.sigma.workflow.WorkflowExecutionPlan.ExecutionFlow;
import com.datarpm.sigma.workflow.WorkflowExecutionPlan.Instruction;

public class WorkflowEngine implements WorkflowEngineDef {

	private static Logger logger = Logger.getLogger(WorkflowEngine.class);

	private ExecutorService threadPool;
	private final WorkflowEngineConfig config;
	private WorkflowEventListener workflowListner;
	private AtomicInteger statesCounter = new AtomicInteger(0);
	private String workflowId;

	public WorkflowEngine() {
		this(new WorkflowEngineConfig().setWorkerThreadCount(1));
	}

	public WorkflowEngine(WorkflowEngineConfig config) {
		this.config = config;
		workflowListner = config.getWorkflowListner();
		if (workflowListner == null) {
			workflowListner = new DummyWorkflowListner();
		}

		workflowId = config.getTransitWorkflowId();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public WorkflowContext execute(Object workflowRequest) throws WorkflowExecutionException {

		if (!workflowRequest.getClass().isAnnotationPresent(WorkflowRequest.class)
				|| (!workflowRequest.getClass().isAnnotationPresent(States.class)
						&& !workflowRequest.getClass().isAnnotationPresent(Flows.class))) {
			logger.error("Annotation missing on Request");
			throw new WorkflowAnnotationException(ExceptionMessageKeys.ANNOTATION_MISSING_IN_REQUEST);
		}

		WorkflowExecutionPlan executionPlan = null;
		try {
			executionPlan = extractPlanFromAnnotation(workflowRequest);
		} catch (Exception e) {
			throw new WorkflowExecutionException(ExceptionMessageKeys.INVALID_WORKFLOW_DEFINITION, e);
		}
		return executePlan(executionPlan);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private WorkflowExecutionPlan extractPlanFromAnnotation(final Object workflowRequest) throws Exception {

		final WorkflowContext workflowContext = buildContextFromAnnotation(workflowRequest);
		Builder builder = new WorkflowExecutionPlan.Builder(workflowRequest, new WorkflowContextFactory() {
			@Override
			public WorkflowContext create() {
				return workflowContext;
			}
		});

		if (workflowRequest.getClass().isAnnotationPresent(States.class)) {
			States states = workflowRequest.getClass().getAnnotation(States.class);
			for (Class<? extends WorkflowState> state : states.names()) {
				WorkflowState workflowState = state.newInstance();
				builder.executeState(workflowState);
			}
		}

		if (workflowRequest.getClass().isAnnotationPresent(Flows.class)) {
			Flows flowsAnnotation = workflowRequest.getClass().getAnnotation(Flows.class);
			Flow[] flows = flowsAnnotation.flows();
			for (Flow flow : flows) {
				ConditionalFlow[] conditionalFlows = flow.conditional();
				if (conditionalFlows != null) {
					addConditionalFlow(builder, conditionalFlows, workflowContext);
				}

				States simple = flow.simple();
				if (simple != null) {
					for (Class<? extends WorkflowState> state : simple.names()) {
						WorkflowState workflowState = state.newInstance();
						builder.executeState(workflowState);
					}
				}
			}
		}
		return builder.getPlan();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private WorkflowContext buildContextFromAnnotation(Object workflowRequest) {
		WorkflowContext workflowContext = null;
		Context context = workflowRequest.getClass().getAnnotation(Context.class);
		if (context != null) {
			try {
				workflowContext = context.name().newInstance();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		} else {
			workflowContext = new WorkflowContext();
		}
		workflowContext.setRequest(workflowRequest);
		return workflowContext;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addConditionalFlow(Builder builder, ConditionalFlow[] conditionalFlows,
			WorkflowContext workflowContext) throws Exception {
		for (ConditionalFlow conditionalFlow : conditionalFlows) {
			WorkflowCondition condition = generateCondition(conditionalFlow.exp(), workflowContext);
			List<WorkflowState> stateInstances = new ArrayList<WorkflowState>();
			for (Class<? extends WorkflowState> state : conditionalFlow.states()) {
				WorkflowState workflowState = state.newInstance();
				stateInstances.add(workflowState);
			}
			builder.executeConditional(condition, stateInstances.toArray(new WorkflowState[stateInstances.size()]));
		}
	}

	@SuppressWarnings("rawtypes")
	private WorkflowCondition generateCondition(String exp, WorkflowContext workflowContext) throws Exception {

		Object request = workflowContext.getRequest();
		String workflowContectClassName = workflowContext.getClass().getName();
		String className = request.getClass().getName();

		StringBuffer classBody = new StringBuffer();
		classBody.append(" public boolean evaluate(WorkflowContext contextParam, Object requestParam) { ");
		classBody.append(workflowContectClassName).append(" context = (").append(workflowContectClassName)
				.append(") contextParam; ");
		classBody.append(className).append(" request = (").append(className).append(") requestParam;");
		classBody.append("return ").append(exp).append("; ");
		classBody.append(" } ");

		IClassBodyEvaluator evaluator = CompilerFactoryFactory.getDefaultCompilerFactory().newClassBodyEvaluator();
		evaluator.setImplementedInterfaces(new Class[] { WorkflowCondition.class });
		evaluator.setDefaultImports(new String[] { "com.datarpm.sigma.workflow.*" });
		evaluator.setParentClassLoader(WorkflowEngine.class.getClassLoader());
		Reader reader = new StringReader(classBody.toString());
		return (WorkflowCondition) evaluator.createInstance(reader);
	}

	@SuppressWarnings("rawtypes")
	private WorkflowStateInfo stateInfo(WorkflowState state) {
		String stateName;
		if (state instanceof NamedWorkflowState) {
			stateName = ((NamedWorkflowState) state).getName();
		} else {
			stateName = state.getClass().getSimpleName();
		}
		return new WorkflowStateInfo(stateName, statesCounter.incrementAndGet());
	}

	private Exception extractOriginalCause(Exception e) {
		if (e instanceof WorkflowStateException) {
			Throwable cause = e.getCause();
			if (cause != null && cause instanceof Exception) {
				return extractOriginalCause((Exception) cause);
			}
		}
		return e;
	}

	private void shutdownThreadPool() {
		if (this.threadPool != null && !this.threadPool.isShutdown())
			threadPool.shutdown();
	}

	private ExecutorService createWorkflowInstructionThreadPool() {
		int workerThreadCount = this.config.getWorkerThreadCount();
		if (workerThreadCount == 0) {
			workerThreadCount = 1;
		}
		threadPool = Executors.newFixedThreadPool(workerThreadCount);
		return threadPool;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void executeListnerBeforeState(List<WorkflowStateListner> allListners, WorkflowContext workflowContext)
			throws DoNotExecuteStateException, ListnerExecutionFailureException, Exception {
		for (WorkflowStateListner eachListner : allListners) {
			eachListner.before(workflowContext);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void executeListnerAfterState(List<WorkflowStateListner> allListners, WorkflowContext workflowContext)
			throws DoNotExecuteStateException, ListnerExecutionFailureException, Exception {
		for (WorkflowStateListner eachListner : allListners) {
			eachListner.after(workflowContext);
		}
	}

	@Override
	public <R, C extends WorkflowContext<R>> C executePlan(WorkflowExecutionPlan<R, C> executionPlan)
			throws WorkflowExecutionException {
		return execute(executionPlan);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <R, C extends WorkflowContext<R>> C execute(WorkflowExecutionPlan<R, C> executionPlan)
			throws WorkflowExecutionException {

		WorkflowMetaInfo workflowMetaInfo = new WorkflowMetaInfo(getWorkflowId(),
				executionPlan.getNumberOfStates());
		Thread shutdownHookThread = new Thread(new WorkflowShutdown(workflowMetaInfo,workflowListner));
		Runtime.getRuntime().addShutdownHook(shutdownHookThread);
		
		C workflowContext = executionPlan.getFactory().create();
		try {
			R workflowRequest = executionPlan.getRequest();
			workflowContext.setWorkflowId(getWorkflowId());
			workflowContext.setRequest(workflowRequest);
			workflowContext.setWorkflowSignal(new WorkflowSignalInternal());
			threadPool = createWorkflowInstructionThreadPool();
			workflowListner.started(workflowMetaInfo);
			logger.info("Executing workflow ::" + workflowRequest.getClass().getName());

			for (ExecutionFlow eachFlow : executionPlan.getExecutionPlan()) {
				WorkflowCondition condition = eachFlow.getCondition();
				if (condition.evaluate(workflowContext, workflowRequest)) {
					List<Instruction> intructions = eachFlow.getIntructions();
					for (Instruction eachInstruction : intructions) {
						switch (eachInstruction.getType()) {
						case STATE_EXECUTION:
							executeStateInstruction(eachInstruction, workflowContext);
							break;
						default:
							break;
						}
					}
				}
				if (workflowContext.isExitWorkflow()) {
					logger.info("Workflow execution exit has been triggered from state: " + eachFlow.getIntructions());
					break;
				}
			}

			if (workflowContext.isSuccess()) {
				workflowListner.finished(workflowMetaInfo);
				logger.info("Workflow execution successful");
			} else if (workflowContext.isExitWorkflow()) {
				workflowListner.terminated(workflowMetaInfo);
				logger.info("Workflow execution terminated");
			} else {
				workflowListner.finishedWithFailure(workflowMetaInfo);
				logger.info("Workflow execution Unsuccessful. :: " + workflowRequest.getClass().getName());
			}
		} catch (Exception e) {
			workflowContext.setSuccess(false);
			workflowContext.setException(extractOriginalCause(e));
		} finally {
			shutdownThreadPool();
			Runtime.getRuntime().removeShutdownHook(shutdownHookThread);
		}

		return workflowContext;
	}

	@SuppressWarnings({ "rawtypes" })
	private void executeStateInstruction(Instruction instruction, WorkflowContext workflowContext)
			throws WorkflowStateException, DoNotExecuteStateException {

		List<FutureTask<Void>> executionList = new ArrayList<FutureTask<Void>>();
		for (WorkflowState workflowState : instruction.getStates()) {
			List<WorkflowStateListner> allListners = extractAnnotationStateListners(workflowState, instruction);
			if (workflowContext.isFailed()) {
				SkipOnFailure skipState = workflowState.getClass().getAnnotation(SkipOnFailure.class);
				if (skipState != null) {
					logger.info("There has been failure in workflow execution, skipping execution of this state, "
							+ "as this state is marked to skip on failure {state-name : "
							+ workflowState.getClass().getName() + "}");
					workflowListner.onStateExecutionSkipped(stateInfo(workflowState));
					continue;
				}
			}
			FutureTask<Void> command = new FutureTask<Void>(
					new StateExecuteTask(workflowState, allListners, workflowContext));
			threadPool.execute(command);
			executionList.add(command);
		}

		for (FutureTask<Void> futureTask : executionList) {
			try {
				futureTask.get();
			} catch (Exception e) {
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private List<WorkflowStateListner> extractAnnotationStateListners(WorkflowState workflowState,
			Instruction instruction) {
		List<WorkflowStateListner> list = new ArrayList<WorkflowStateListner>();
		try {
			AddStateListner listners = workflowState.getClass().getAnnotation(AddStateListner.class);
			if (listners != null)
				for (Class<? extends WorkflowStateListner> eachClass : listners.names()) {
					list.add(eachClass.newInstance());
				}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

		if (instruction.getListner() != null) {
			list.add(instruction.getListner());
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	class StateExecuteTask implements Callable<Void> {

		private List<WorkflowStateListner> allListners;
		private WorkflowState eachState;
		private WorkflowContext context;

		public StateExecuteTask(WorkflowState eachState, List<WorkflowStateListner> allListners,
				WorkflowContext context) {
			this.eachState = eachState;
			this.allListners = allListners;
			this.context = context;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Void call() throws Exception {

			WorkflowStateInfo stateInfo = stateInfo(eachState);
			workflowListner.onStateExecutionStarted(stateInfo);
			logger.info("Executing State : " + eachState.getClass().getName() + " ...");
			try {
				try {
					executeListnerBeforeState(allListners, context);
				} catch (DoNotExecuteStateException e) {
					throw e;
				} catch (Exception e) {
					logger.error("Failed to execute before method for workflowState listner", e);
				}

				eachState.execute(context);
				workflowListner.onStateExecutionCompleted(stateInfo);
				try {
					executeListnerAfterState(allListners, context);
				} catch (DoNotExecuteStateException e) {
					logger.info("Skip state execution as listner marked this state as DoNotExecute {State : "
							+ eachState.getClass().getName() + "}");
				} catch (Exception e) {
					logger.error("Failed to execute before method for workflowState listner", e);
					throw e;
				}
			} catch (Exception e) {
				logger.error("Execution Unsuccessful for State : " + eachState.getClass().getName(), e);
				context.setSuccess(false);
				context.setException(extractOriginalCause(e));
				workflowListner.onStateExecutionFailed(stateInfo, e);
			}
			return null;
		}

	}

	@Override
	public String getWorkflowId() {
		if (workflowId == null) {
			synchronized (this) {
				if (workflowId == null || workflowId.trim().isEmpty()) {
					workflowId = UUID.randomUUID().toString();
				}
			}
		}
		return workflowId;
	}

	class WorkflowSignalInternal implements WorkflowSignal {

		@Override
		public void signalTransitOn() {
			workflowListner.onWorkflowTransitOn();
		}

		@Override
		public void signalTransitOff() {
			workflowListner.onWorkflowTransitOff();
		}

	}

	class WorkflowShutdown implements Runnable {
		
		private WorkflowEventListener workflowListener;
		private WorkflowMetaInfo workflowMetaInfo;

		public WorkflowShutdown(WorkflowMetaInfo workflowMetaInfo, WorkflowEventListener workflowListner) {
			this.workflowMetaInfo = workflowMetaInfo;
			this.workflowListener = workflowListner;
		}

		@Override
		public void run() {
			this.workflowListener.finishedWithFailure(workflowMetaInfo);
		}
	}
}
