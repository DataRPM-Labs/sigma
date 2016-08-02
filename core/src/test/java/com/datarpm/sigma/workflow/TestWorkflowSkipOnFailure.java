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
/**
 * 
 */
package com.datarpm.sigma.workflow;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.datarpm.sigma.workflow.Context;
import com.datarpm.sigma.workflow.ExceptionMessageKeys;
import com.datarpm.sigma.workflow.SkipOnFailure;
import com.datarpm.sigma.workflow.States;
import com.datarpm.sigma.workflow.WorkflowEngine;
import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;

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

	@com.datarpm.sigma.workflow.WorkflowRequest
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
			com.datarpm.sigma.workflow.WorkflowContext<WorkflowRequest> {
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
