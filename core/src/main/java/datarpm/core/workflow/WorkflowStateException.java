package datarpm.core.workflow;

public class WorkflowStateException extends WorkflowExecutionException {

	private static final long serialVersionUID = 1L;

	public WorkflowStateException() {
		super();
	}

	public WorkflowStateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WorkflowStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowStateException(String message) {
		super(message);
	}

	public WorkflowStateException(Throwable cause) {
		super(cause);
	}

}
