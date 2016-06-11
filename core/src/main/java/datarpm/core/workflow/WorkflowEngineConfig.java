/**
 * 
 */
package datarpm.core.workflow;

/**
 * @author alok
 * 
 */
public class WorkflowEngineConfig {

	private int workerThreadCount;
	private WorkflowEventListener workflowListner;
	private String transitWorkflowId;

	public int getWorkerThreadCount() {
		return workerThreadCount;
	}

	public WorkflowEngineConfig setWorkerThreadCount(int workerThreadCount) {
		this.workerThreadCount = workerThreadCount;
		return this;
	}

	public WorkflowEventListener getWorkflowListner() {
		return workflowListner;
	}

	public void setWorkflowListner(WorkflowEventListener workflowListner) {
		this.workflowListner = workflowListner;
	}

	public void setTransitWorkflowId(String transitWorkflowId) {
		this.transitWorkflowId = transitWorkflowId;
	}

	public String getTransitWorkflowId() {
		return transitWorkflowId;
	}

}
