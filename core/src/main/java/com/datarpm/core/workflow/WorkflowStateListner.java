/**
 * 
 */
package com.datarpm.core.workflow;

/**
 * @author vishal
 * 
 */
public interface WorkflowStateListner<R, T extends WorkflowContext<R>> {

	/**
	 * This method is called before executing the WorkflowState. <br />
	 * 
	 * Throw {@link DoNotExecuteStateException} if {@link WorkflowState} 
	 * shouldn't be executed
	 * 
	 * @throws DoNotExecuteStateException
	 * @throws ListnerExecutionFailureException
	 */
	public void before(T context) throws DoNotExecuteStateException,
			ListnerExecutionFailureException;

	public void after(T context) throws ListnerExecutionFailureException;
}
