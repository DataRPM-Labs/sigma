/**
 * 
 */
package datarpm.core.workflow;

/**
 * @author vishal
 *
 */
public class DummyWorkflowListner implements WorkflowEventListener {

	@Override
	public void started(WorkflowMetaInfo meta) {

	}

	@Override
	public void onStateExecutionStarted(WorkflowStateInfo state) {

	}

	@Override
	public void onStateExecutionCompleted(WorkflowStateInfo state) {

	}

	@Override
	public void onStateExecutionFailed(WorkflowStateInfo state, Exception e) {

	}

	@Override
	public void onStateExecutionTerminated(WorkflowStateInfo state) {

	}

	@Override
	public void onStateExecutionSkipped(WorkflowStateInfo stateInfo) {

	}

	@Override
	public void finished(WorkflowMetaInfo meta) {

	}

	@Override
	public void finishedWithFailure(WorkflowMetaInfo meta) {

	}

	@Override
	public void onWorkflowTransitOn() {

	}

	@Override
	public void onWorkflowTransitOff() {

	}

	@Override
	public void terminated(WorkflowMetaInfo meta) {
		// TODO Auto-generated method stub
		
	}

}
