/**
 * 
 */
package com.datarpm.core.workflow;

/**
 * @author vishal
 *
 */
public interface WorkflowEventListener {

	public void started(WorkflowMetaInfo meta);

	public void onWorkflowTransitOn();

	public void onWorkflowTransitOff();

	public void onStateExecutionStarted(WorkflowStateInfo state);

	public void onStateExecutionCompleted(WorkflowStateInfo state);

	public void onStateExecutionFailed(WorkflowStateInfo state, Exception e);

	public void onStateExecutionTerminated(WorkflowStateInfo state);

	public void onStateExecutionSkipped(WorkflowStateInfo stateInfo);

	public void finished(WorkflowMetaInfo meta);

	public void finishedWithFailure(WorkflowMetaInfo meta);
	
	public void terminated(WorkflowMetaInfo meta);
}
