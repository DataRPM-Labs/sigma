package com.datarpm.core.workflow;

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
