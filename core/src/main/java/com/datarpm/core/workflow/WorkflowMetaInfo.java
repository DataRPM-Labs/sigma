/**
 * 
 */
package com.datarpm.core.workflow;

/**
 * @author vishal
 *
 */
public class WorkflowMetaInfo {

	private int totalStates;
	private String workflowId;

	public WorkflowMetaInfo(String workflowId, int totalStates) {
		super();
		this.totalStates = totalStates;
		this.workflowId = workflowId;
	}

	public int getTotalStates() {
		return totalStates;
	}

	public void setTotalStates(int totalStates) {
		this.totalStates = totalStates;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

}
