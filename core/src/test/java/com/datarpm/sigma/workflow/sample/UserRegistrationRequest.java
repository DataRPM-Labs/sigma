/*
 * Licensed to DataRPM. This source code is proprietary to DataRPM.
 */
package com.datarpm.sigma.workflow.sample;

import com.datarpm.sigma.workflow.ConditionalFlow;
import com.datarpm.sigma.workflow.Context;
import com.datarpm.sigma.workflow.Flow;
import com.datarpm.sigma.workflow.Flows;
import com.datarpm.sigma.workflow.States;
import com.datarpm.sigma.workflow.WorkflowRequest;

/**
 * @author gautam
 *
 */
@WorkflowRequest
@Context(name = UserRegistrationContext.class)
@Flows(flows = { @Flow(simple = @States(names = { MandatoryFieldValidation.class, UserCreationAPICall.class})),
                  @Flow(conditional = {
                      @ConditionalFlow(exp = "context.isUserApiCallStatus() == true", states = {
                          CreateDefaultProfile.class,SendVerificationEmail.class 
                      })}),
                  @Flow(conditional = {
                      @ConditionalFlow(exp = "context.isUserApiCallStatus() == false", states = {
                          SendAlertToSupport.class 
                      })})
                  })
public class UserRegistrationRequest {
  private String userid;
  private String password;
  private String email;
  private String address;       // This can be another pojo
  private boolean apiSuccessFlag;
  
  public UserRegistrationRequest (String userid, String password, String email, String address) {
    this.userid = userid;
    this.password = password;
    this.email = email;
    this.address = address;
  }
  public String getUserid() {
    return userid;
  }
  public void setUserid(String userid) {
    this.userid = userid;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public boolean isApiSuccessFlag() {
    return apiSuccessFlag;
  }
  public void setApiSuccessFlag(boolean apiSuccessFlag) {
    this.apiSuccessFlag = apiSuccessFlag;
  }
}
