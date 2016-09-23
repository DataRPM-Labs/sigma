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
public class MandatoryFieldValidation implements WorkflowState<UserRegistrationRequest, UserRegistrationContext>{

  @Override
  public void execute(UserRegistrationContext context) throws WorkflowStateException {
    UserRegistrationRequest request = context.getRequest();
    // Logic to validate attributes
    System.out.println("-Executing---" + this.getClass().getName());
  }

}
