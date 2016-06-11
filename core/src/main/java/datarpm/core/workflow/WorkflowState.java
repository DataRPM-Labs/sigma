package datarpm.core.workflow;

public interface WorkflowState<R, T extends WorkflowContext<R>> {

	public void execute(T context) throws WorkflowStateException;

}
