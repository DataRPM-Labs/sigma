/*
 * Licensed to DataRPM. This source code is proprietary to DataRPM.
 */
package com.datarpm.sigma.workflow.sample;

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
    // You set the ApiCall Status value based on success / failure of the API
    // call. For simplicity We are setting it from request object
    context.setUserApiCallStatus(context.getRequest().isApiSuccessFlag());
  }

}
