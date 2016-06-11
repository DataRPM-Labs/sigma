/**
 * 
 */
package com.datarpm.core.workflow.plan;

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
public class TestWorkflowSimpleExecutionPlan extends TestCase {

	private static final boolean EXECUTED = true;
	private static final boolean FALSE = false;

	@Before
	public void initialize() {
	}

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
		builder.executeState(new SimpleState());

		SimpleWorkflowContext workflowContext = new WorkflowEngine()
				.execute(builder.getPlan());

		assertEquals(workflowContext.isFailed(), FALSE);
		assertEquals(workflowContext.isStateExecuted(), EXECUTED);
	}

	class SimpleWorkflowRequest {

	}

	public static class SimpleState implements
			WorkflowState<SimpleWorkflowRequest, SimpleWorkflowContext> {

		@Override
		public void execute(SimpleWorkflowContext context)
				throws WorkflowStateException {
			System.out.println("State Executed");
			context.setStateExecuted(EXECUTED);
		}

	}

	static class SimpleWorkflowContext extends
			com.datarpm.core.workflow.WorkflowContext<SimpleWorkflowRequest> {
		private static final long serialVersionUID = 1L;

		private boolean stateExecuted = false;

		boolean isStateExecuted() {
			return stateExecuted;
		}

		void setStateExecuted(boolean stateExecuted) {
			this.stateExecuted = stateExecuted;
		}

	}
}
