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

public class WorkflowContext<R> extends BaseContext {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected R request;
	private boolean success;
	private Exception exception;
	private boolean failed;
	private WorkflowSignal workflowSignal;
	private String workflowId;
	private boolean exitWorkflow;
	private boolean secondaryCachingInProgress;

	public WorkflowContext() {
		super();
		success = true;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean running) {
		this.success = running;
	}

	public R getRequest() {
		return request;
	}

	public void setRequest(R request) {
		this.request = request;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.failed = true;
		this.exception = exception;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setWorkflowSignal(WorkflowSignal workflowSignal) {
		this.workflowSignal = workflowSignal;
	}

	public WorkflowSignal getWorkflowSignal() {
		return workflowSignal;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public boolean isExitWorkflow() {
		return exitWorkflow;
	}

	public void exitWorkflow() {
		this.exitWorkflow = true;
		this.success = false;
	}

	public boolean isSecondaryCachingInProgress() {
		return secondaryCachingInProgress;
	}

	public void setSecondaryCachingInProgress(boolean secondaryCachingInProgress) {
		this.secondaryCachingInProgress = secondaryCachingInProgress;
	}

	public void setExitWorkflow(boolean exitWorkflow) {
		this.exitWorkflow = exitWorkflow;
		this.success = false;
	}

}
