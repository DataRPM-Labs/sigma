/*
 * Licensed to DataRPM. This source code is proprietary to DataRPM.
 */
package com.datarpm.sigma.workflow.examples.conditional.registration;

import com.datarpm.sigma.workflow.WorkflowEngine;
import com.datarpm.sigma.workflow.WorkflowExecutionException;

/**
 * @author gautam
 *
 */
public class UserRegistrationFlowMain {

  public static void main(String[] args) throws WorkflowExecutionException {
    UserRegistrationRequest request = new UserRegistrationRequest("user123", "passxyz",
        "user@user.com", "Address 123");
    UserRegistrationContext workflowContext = (UserRegistrationContext) new WorkflowEngine()
        .execute(request);
    // do something the workflow execution status
  }

}
