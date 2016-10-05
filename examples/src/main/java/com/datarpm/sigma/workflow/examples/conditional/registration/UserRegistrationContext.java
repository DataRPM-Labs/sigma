/*
 * Licensed to DataRPM. This source code is proprietary to DataRPM.
 */
package com.datarpm.sigma.workflow.examples.conditional.registration;

import com.datarpm.sigma.workflow.WorkflowContext;

/**
 * @author gautam
 *
 */
public class UserRegistrationContext extends WorkflowContext<UserRegistrationRequest>{
  private static final long serialVersionUID = 1L;
  
  private boolean userApiCallStatus;
  private String userProfileId;
  
  public boolean isUserApiCallStatus() {
    return userApiCallStatus;
  }
  public void setUserApiCallStatus(boolean userApiCallStatus) {
    this.userApiCallStatus = userApiCallStatus;
  }
  public String getUserProfileId() {
    return userProfileId;
  }
  public void setUserProfileId(String userProfileId) {
    this.userProfileId = userProfileId;
  }
}
