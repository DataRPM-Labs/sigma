/**
 * 
 */
package datarpm.core.workflow;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import datarpm.core.workflow.AddStateListner;
import datarpm.core.workflow.Context;
import datarpm.core.workflow.DoNotExecuteStateException;
import datarpm.core.workflow.ExceptionMessageKeys;
import datarpm.core.workflow.ListnerExecutionFailureException;
import datarpm.core.workflow.States;
import datarpm.core.workflow.WorkflowEngine;
import datarpm.core.workflow.WorkflowState;
import datarpm.core.workflow.WorkflowStateException;
import datarpm.core.workflow.WorkflowStateListner;

/**
 * @author vishal
 * 
 */
public class TestWorkflowStateListner extends TestCase {

	private static final boolean EXECUTED = true;
	private static final boolean DO_NOT_EXECUTE = false;

	@Before
	public void initialize() {
	}

	@Test
	public void testWorkflowStateListnerExecution() throws Exception {

		CheckListnerWorkflowContext context = (CheckListnerWorkflowContext) new WorkflowEngine()
				.execute(new CheckListnerWorkflowRequest());

		assertEquals(context.isBeforelistnerExecuted(), EXECUTED);
		assertEquals(context.isStateExecuted(), EXECUTED);
		assertEquals(context.isAfterlistnerExecuted(), EXECUTED);
	}

	@Test
	public void testWorkflowStateListnerDoNotExecuteState() throws Exception {

		DoNotExecuteWorkflowContext context = (DoNotExecuteWorkflowContext) new WorkflowEngine()
				.execute(new DoNotExecuteWorkflowRequest());

		assertEquals(context.isBeforelistnerExecuted(), EXECUTED);
		assertEquals(context.isStateExecuted(), DO_NOT_EXECUTE);
		assertEquals(context.isAfterlistnerExecuted(), DO_NOT_EXECUTE);
	}

	@datarpm.core.workflow.WorkflowRequest
	@States(names = { ExecuteState.class })
	@Context(name = CheckListnerWorkflowContext.class)
	class CheckListnerWorkflowRequest {

	}

	@AddStateListner(names = { ExecuteStateListner.class })
	public static class ExecuteState
			implements
			WorkflowState<CheckListnerWorkflowRequest, CheckListnerWorkflowContext> {

		@Override
		public void execute(CheckListnerWorkflowContext context)
				throws WorkflowStateException {
			context.setStateExecuted(EXECUTED);
			System.out.println("Workflow State Executed");
		}

	}

	public static class ExecuteStateListner
			implements
			WorkflowStateListner<CheckListnerWorkflowRequest, CheckListnerWorkflowContext> {

		@Override
		public void before(CheckListnerWorkflowContext context)
				throws DoNotExecuteStateException,
				ListnerExecutionFailureException {

			context.setBeforelistnerExecuted(EXECUTED);
		}

		@Override
		public void after(CheckListnerWorkflowContext context)
				throws ListnerExecutionFailureException {

			context.setAfterlistnerExecuted(EXECUTED);
		}

	}

	@datarpm.core.workflow.WorkflowRequest
	@States(names = { DoNotExecuteState.class })
	@Context(name = DoNotExecuteWorkflowContext.class)
	class DoNotExecuteWorkflowRequest {

	}

	public static class DoNotExecuteStateListner
			implements
			WorkflowStateListner<DoNotExecuteWorkflowRequest, DoNotExecuteWorkflowContext> {

		@Override
		public void before(DoNotExecuteWorkflowContext context)
				throws DoNotExecuteStateException,
				ListnerExecutionFailureException {

			context.setBeforelistnerExecuted(EXECUTED);
			throw new DoNotExecuteStateException(ExceptionMessageKeys.SKIP_STATE_EXECUTION);
		}

		@Override
		public void after(DoNotExecuteWorkflowContext context)
				throws ListnerExecutionFailureException {

			context.setAfterlistnerExecuted(EXECUTED);
		}

	}

	@AddStateListner(names = { DoNotExecuteStateListner.class })
	public static class DoNotExecuteState
			implements
			WorkflowState<CheckListnerWorkflowRequest, CheckListnerWorkflowContext> {

		@Override
		public void execute(CheckListnerWorkflowContext context)
				throws WorkflowStateException {
			System.out
					.println("Report Failure, skipped stated must not be executed after this");
			context.setStateExecuted(EXECUTED);
		}

	}

	public static class CheckListnerWorkflowContext extends
			datarpm.core.workflow.WorkflowContext<CheckListnerWorkflowRequest> {
		private static final long serialVersionUID = 1L;

		private boolean stateExecuted;
		private boolean beforelistnerExecuted;
		private boolean afterlistnerExecuted;

		public boolean isAfterlistnerExecuted() {
			return afterlistnerExecuted;
		}

		public void setAfterlistnerExecuted(boolean afterlistnerExecuted) {
			this.afterlistnerExecuted = afterlistnerExecuted;
		}

		public boolean isBeforelistnerExecuted() {
			return beforelistnerExecuted;
		}

		public void setBeforelistnerExecuted(boolean beforelistnerExecuted) {
			this.beforelistnerExecuted = beforelistnerExecuted;
		}

		public boolean isStateExecuted() {
			return stateExecuted;
		}

		public void setStateExecuted(boolean stateExecuted) {
			this.stateExecuted = stateExecuted;
		}
	}

	public static class DoNotExecuteWorkflowContext extends
			datarpm.core.workflow.WorkflowContext<DoNotExecuteWorkflowRequest> {

		private static final long serialVersionUID = 1L;
		private boolean stateExecuted;
		private boolean beforelistnerExecuted;
		private boolean afterlistnerExecuted;

		public boolean isAfterlistnerExecuted() {
			return afterlistnerExecuted;
		}

		public void setAfterlistnerExecuted(boolean afterlistnerExecuted) {
			this.afterlistnerExecuted = afterlistnerExecuted;
		}

		public boolean isBeforelistnerExecuted() {
			return beforelistnerExecuted;
		}

		public void setBeforelistnerExecuted(boolean beforelistnerExecuted) {
			this.beforelistnerExecuted = beforelistnerExecuted;
		}

		public boolean isStateExecuted() {
			return stateExecuted;
		}

		public void setStateExecuted(boolean stateExecuted) {
			this.stateExecuted = stateExecuted;
		}

	}
}
