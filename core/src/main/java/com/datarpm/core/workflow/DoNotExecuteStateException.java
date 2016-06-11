/**
 * 
 */
package com.datarpm.core.workflow;

/**
 * @author vishal
 * 
 */
public class DoNotExecuteStateException extends WorkflowExecutionException {

	private static final long serialVersionUID = 1L;

	public DoNotExecuteStateException() {
		super();
	}

	public DoNotExecuteStateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DoNotExecuteStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DoNotExecuteStateException(String message) {
		super(message);
	}

	public DoNotExecuteStateException(Throwable cause) {
		super(cause);
	}

}
