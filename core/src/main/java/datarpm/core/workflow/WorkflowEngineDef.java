package datarpm.core.workflow;

public interface WorkflowEngineDef {

	WorkflowEngineDef ENGINE = new WorkflowEngine();

	@SuppressWarnings("rawtypes")
	public WorkflowContext execute(Object workflowRequest)
			throws WorkflowExecutionException, WorkflowStateException,
			Exception;

	public <R, C extends WorkflowContext<R>> C execute(
			WorkflowExecutionPlan<R, C> executionPlan)
			throws WorkflowExecutionException;

	public <R, C extends WorkflowContext<R>> C executePlan(
			WorkflowExecutionPlan<R, C> executionPlan)
			throws WorkflowExecutionException;

	public String getWorkflowId();
}
