/*******************************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package com.datarpm.sigma.workflow;

import org.junit.Before;
import org.junit.Test;

import com.datarpm.sigma.workflow.ConditionalFlow;
import com.datarpm.sigma.workflow.Context;
import com.datarpm.sigma.workflow.Flow;
import com.datarpm.sigma.workflow.Flows;
import com.datarpm.sigma.workflow.States;
import com.datarpm.sigma.workflow.WorkflowEngine;
import com.datarpm.sigma.workflow.WorkflowRequest;
import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;

import junit.framework.TestCase;

/**
 * @author vishal
 * 
 */
public class TestWorkflowConditional extends TestCase {

  private static final boolean EXECUTED = true;

  @Before
  public void initialize() {
  }

  @Test
  public void testConditionalWorkflowExecution() throws Exception {
    SimpleWorkflowRequest workflowRequest = new SimpleWorkflowRequest();
    workflowRequest.setRequestType("simpleCondition");
    ConditionalWorkflowContext workflowContext = (ConditionalWorkflowContext) new WorkflowEngine()
        .execute(workflowRequest);
    assertTrue(workflowContext.isExecuteStateFlag());
    assertFalse(workflowContext.isDoNotExecuteStateFlag());
    assertTrue(workflowContext.isSecondStateExecuted());
  }

  @WorkflowRequest
  // This declaration provides Flow Definition
  @Flows(flows = {
      // This is a simple flow definition
      // All states are executed sequentially
      @Flow(simple = @States(names = { ExecuteState.class }) ),
      // This is a conditional flow definition
      // states will be executed sequentially, only if the evaluation of the
      // expression returns true
      @Flow(conditional = {
          @ConditionalFlow(exp = "!context.isExecuteStateFlag() && !request.getRequestType().equals(\"simpleCondition\")", states = {
              DoNotExecuteState.class }) }),
      @Flow(conditional = {
          @ConditionalFlow(exp = "context.isExecuteStateFlag()", states = { DoExecuteState.class }) }) })

  @Context(name = ConditionalWorkflowContext.class)
  public static class SimpleWorkflowRequest {
    private String requestType;

    public String getRequestType() {
      return requestType;
    }

    public void setRequestType(String requestType) {
      this.requestType = requestType;
    }

  }

  public static class ExecuteState implements WorkflowState<SimpleWorkflowRequest, ConditionalWorkflowContext> {

    @Override
    public void execute(ConditionalWorkflowContext context) throws WorkflowStateException {
      System.out.println("Workflow State Executed");
      context.setExecuteStateFlag(EXECUTED);
    }

  }

  public static class DoNotExecuteState implements WorkflowState<SimpleWorkflowRequest, ConditionalWorkflowContext> {

    @Override
    public void execute(ConditionalWorkflowContext context) throws WorkflowStateException {
      System.out.println("Report Failure, skipped stated must not be executed after this");
      context.setDoNotExecuteStateFlag(EXECUTED);
    }

  }

  public static class DoExecuteState implements WorkflowState<SimpleWorkflowRequest, ConditionalWorkflowContext> {

    @Override
    public void execute(ConditionalWorkflowContext context) throws WorkflowStateException {
      System.out.println("Report Failure, skipped stated must not be executed after this");
      context.setSecondStateExecuted(EXECUTED);
    }

  }

  public static class ConditionalWorkflowContext extends com.datarpm.sigma.workflow.WorkflowContext<SimpleWorkflowRequest> {

    private static final long serialVersionUID = 1L;
    private boolean doNotExecuteStateFlag;
    private boolean executeStateFlag;
    private boolean secondStateExecuted;

    public boolean isSecondStateExecuted() {
      return secondStateExecuted;
    }

    public void setSecondStateExecuted(boolean secondStateExecuted) {
      this.secondStateExecuted = secondStateExecuted;
    }

    public boolean isDoNotExecuteStateFlag() {
      return doNotExecuteStateFlag;
    }

    public void setDoNotExecuteStateFlag(boolean doNotExecuteStateFlag) {
      this.doNotExecuteStateFlag = doNotExecuteStateFlag;
    }

    public boolean isExecuteStateFlag() {
      return executeStateFlag;
    }

    public void setExecuteStateFlag(boolean executeStateFlag) {
      this.executeStateFlag = executeStateFlag;
    }

  }

}
