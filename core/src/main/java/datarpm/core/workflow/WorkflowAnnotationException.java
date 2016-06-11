/**
 * 
 */
package datarpm.core.workflow;

/**
 * @author vishal
 * 
 */
public class WorkflowAnnotationException extends WorkflowExecutionException {

	private static final long serialVersionUID = 1L;

	public WorkflowAnnotationException() {
		super();
	}

	public WorkflowAnnotationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WorkflowAnnotationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowAnnotationException(String message) {
		super(message);
	}

	public WorkflowAnnotationException(Throwable cause) {
		super(cause);
	}

}
