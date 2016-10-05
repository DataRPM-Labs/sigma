/*
 * Licensed to DataRPM. This source code is proprietary to DataRPM.
 */
package com.datarpm.sigma.workflow.examples.conditional.registration;

import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;

/**
 * @author gautam
 *
 */
public class UserCreationAPICall
    implements WorkflowState<UserRegistrationRequest, UserRegistrationContext> {

  @Override
  public void execute(UserRegistrationContext context) throws WorkflowStateException {
    System.out.println("-Executing---" + this.getClass().getName());
    // Create user and set following flag is successful
    boolean userCreationFlag = true;
    context.setUserApiCallStatus(userCreationFlag);
  }

}
