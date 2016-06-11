/**
 * 
 */
package com.datarpm.core.workflow;

/**
 * @author vinay
 *
 */
public abstract class NamedWorkflowState<R, T extends WorkflowContext<R>> implements WorkflowState<R, T> {
	public abstract String getName();
}
