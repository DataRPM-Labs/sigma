package com.datarpm.core.workflow;

public class WorkflowExecutionException extends Exception {

	private static final long serialVersionUID = 1L;

	public WorkflowExecutionException() {
		super();
	}

	public WorkflowExecutionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WorkflowExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowExecutionException(String message) {
		super(message);
	}

	public WorkflowExecutionException(Throwable cause) {
		super(cause);
	}

}
