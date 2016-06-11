/**
 * 
 */
package com.datarpm.core.workflow;

/**
 * @author vishal
 *
 */
public class ListnerExecutionFailureException extends WorkflowExecutionException {

	private static final long serialVersionUID = 1L;

	public ListnerExecutionFailureException() {
		super();
	}

	public ListnerExecutionFailureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ListnerExecutionFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ListnerExecutionFailureException(String message) {
		super(message);
	}

	public ListnerExecutionFailureException(Throwable cause) {
		super(cause);
	}

}
