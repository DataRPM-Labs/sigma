/**
 * 
 */
package com.datarpm.core.workflow.plan;

import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.datarpm.core.workflow.WorkflowContextFactory;
import com.datarpm.core.workflow.WorkflowEngine;
import com.datarpm.core.workflow.WorkflowExecutionPlan;
import com.datarpm.core.workflow.WorkflowState;
import com.datarpm.core.workflow.WorkflowStateException;
import com.datarpm.core.workflow.WorkflowExecutionPlan.Builder;

/**
 * @author vishal
 * 
 */
public class TestWorkflowMultiExecutionPlan extends TestCase {

	private static final boolean EXECUTED = true;
	private static final boolean FALSE = false;

	@Before
	public void initialize() {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSimpleWorkflow() throws Exception {

		SimpleWorkflowRequest simpleRequest = new SimpleWorkflowRequest();
		Builder<SimpleWorkflowRequest, SimpleWorkflowContext> builder = null;
		WorkflowContextFactory<SimpleWorkflowRequest, SimpleWorkflowContext> contextFactory = new WorkflowContextFactory<SimpleWorkflowRequest, SimpleWorkflowContext>() {
			@Override
			public SimpleWorkflowContext create() {
				return new SimpleWorkflowContext();
			}
		};

		builder = new WorkflowExecutionPlan.Builder<SimpleWorkflowRequest, SimpleWorkflowContext>(
				simpleRequest, contextFactory);
		builder.executeState(new FirstState())
				.executeConcurrent(new ConcurrentState("T1"),
						new ConcurrentState("T2"))
				.executeState(new NextState());

		SimpleWorkflowContext workflowContext = new WorkflowEngine()
				.execute(builder.getPlan());

		assertEquals(workflowContext.isFailed(), FALSE);
		assertEquals(workflowContext.isState1Executed(), EXECUTED);
		assertEquals(workflowContext.getSharedFlag().get(), EXECUTED);
		assertEquals(workflowContext.isState2Executed(), EXECUTED);
	}

	class SimpleWorkflowRequest {

	}

	public static class FirstState implements
			WorkflowState<SimpleWorkflowRequest, SimpleWorkflowContext> {

		@Override
		public void execute(SimpleWorkflowContext context)
				throws WorkflowStateException {
			System.out.println("State 1 Executed");
			context.setState1Executed(EXECUTED);
		}

	}

	public static class NextState implements
			WorkflowState<SimpleWorkflowRequest, SimpleWorkflowContext> {

		@Override
		public void execute(SimpleWorkflowContext context)
				throws WorkflowStateException {
			System.out
					.println("State 2 Executed after State 1 and concurrent state execution");
			if (context.isState1Executed() && context.getSharedFlag().get())
				context.setState2Executed(EXECUTED);
		}

	}

	public static class ConcurrentState implements
			WorkflowState<SimpleWorkflowRequest, SimpleWorkflowContext> {

		private String name;

		public ConcurrentState(String name) {
			this.name = name;
		}

		@Override
		public void execute(SimpleWorkflowContext context)
				throws WorkflowStateException {
			System.out.println("State " + name + " Executed  Flag : "
					+ context.getSharedFlag().get());
			context.getSharedFlag().set(EXECUTED);
		}

	}

	static class SimpleWorkflowContext extends
			com.datarpm.core.workflow.WorkflowContext<SimpleWorkflowRequest> {
		private static final long serialVersionUID = 1L;

		private boolean state1Executed = false;
		private boolean state2Executed = false;
		private AtomicBoolean sharedFlag = new AtomicBoolean(false);

		boolean isState1Executed() {
			return state1Executed;
		}

		void setState1Executed(boolean state1Executed) {
			this.state1Executed = state1Executed;
		}

		boolean isState2Executed() {
			return state2Executed;
		}

		void setState2Executed(boolean state2Executed) {
			this.state2Executed = state2Executed;
		}

		AtomicBoolean getSharedFlag() {
			return sharedFlag;
		}

	}
}
