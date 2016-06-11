/**
 * 
 */
package datarpm.core.workflow;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import datarpm.core.workflow.Context;
import datarpm.core.workflow.ExceptionMessageKeys;
import datarpm.core.workflow.SkipOnFailure;
import datarpm.core.workflow.States;
import datarpm.core.workflow.WorkflowEngine;
import datarpm.core.workflow.WorkflowState;
import datarpm.core.workflow.WorkflowStateException;

/**
 * @author vishal
 * 
 */
public class TestWorkflowSkipOnFailure extends TestCase {

	private static final boolean EXECUTED = true;
	private static final boolean SKIPPED = false;

	@Before
	public void initialize() {
	}

	@Test
	public void testSkipWorkflow() throws Exception {
		WorkflowContext context = (WorkflowContext) new WorkflowEngine()
				.execute(new WorkflowRequest());
		assertEquals(context.isSkipStateExecuted(), SKIPPED);
	}

	@datarpm.core.workflow.WorkflowRequest
	@States(names = { FailState.class, SkipState.class })
	@Context(name = WorkflowContext.class)
	class WorkflowRequest {

	}

	public static class FailState implements
			WorkflowState<WorkflowRequest, WorkflowContext> {

		@Override
		public void execute(WorkflowContext context)
				throws WorkflowStateException {
			System.out
					.println("Report Failure, skipped stated must not be executed after this");
			throw new WorkflowStateException(ExceptionMessageKeys.FAILED_STATE);
		}

	}

	@SkipOnFailure
	public static class SkipState implements
			WorkflowState<WorkflowRequest, WorkflowContext> {

		@Override
		public void execute(WorkflowContext context)
				throws WorkflowStateException {
			System.out.println("This shouldn't print");
			context.setSkipStateExecuted(EXECUTED);
		}

	}

	public static class WorkflowContext extends
			datarpm.core.workflow.WorkflowContext<WorkflowRequest> {
		private static final long serialVersionUID = 1L;

		private boolean skipStateExecuted = false;

		public boolean isSkipStateExecuted() {
			return skipStateExecuted;
		}

		public void setSkipStateExecuted(boolean skipStateExecuted) {
			this.skipStateExecuted = skipStateExecuted;
		}
	}
}
