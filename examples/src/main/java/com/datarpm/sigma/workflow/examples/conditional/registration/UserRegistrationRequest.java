/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.datarpm.sigma.workflow.examples.conditional.registration;

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
  private String address;       
  
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
}
