/*
 * Licensed to DataRPM. This source code is proprietary to DataRPM.
 */
package com.datarpm.sigma.workflow.sample;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.datarpm.sigma.workflow.WorkflowEngine;
import com.datarpm.sigma.workflow.WorkflowExecutionException;

/**
 * @author gautam
 *
 */
public class UserRegistrationFlowTest {
  
  @Before
  public void initialize() {
  }
  
  @Test
  public void runUserRegistrationFlowWithAPISuccess () {
    UserRegistrationRequest request = new UserRegistrationRequest("user123", "passxyz", "user@user.com", "Address 123");
    request.setApiSuccessFlag(true);
    try {
      UserRegistrationContext workflowContext = (UserRegistrationContext) new WorkflowEngine()
          .execute(request);
      assertTrue(workflowContext.isSuccess());
      System.out.println("Print--User Profile Id--" + workflowContext.getUserProfileId());
      assertTrue(workflowContext.getUserProfileId() != null);
    } catch (WorkflowExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  public void runUserRegistrationFlowWithAPIFailure () {
    UserRegistrationRequest request = new UserRegistrationRequest("user123", "passxyz", "user@user.com", "Address 123");
    request.setApiSuccessFlag(false);
    try {
      UserRegistrationContext workflowContext = (UserRegistrationContext) new WorkflowEngine()
          .execute(request);
      assertTrue(workflowContext.isSuccess());
      System.out.println("Print--User Profile Id--" + workflowContext.getUserProfileId());
      assertTrue(workflowContext.getUserProfileId() != null);
    } catch (WorkflowExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
