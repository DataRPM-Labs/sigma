/**
 * 
 */
package com.datarpm.core.workflow;

/**
 * @author vishal
 *
 */
public interface WorkflowCondition {

  public boolean evaluate(@SuppressWarnings("rawtypes") WorkflowContext context, Object request);

}
