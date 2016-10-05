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
package com.datarpm.sigma.workflow;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TestAnnotationWorkflow extends TestCase {

  private static final boolean EXECUTED = true;

  @Before
  public void initialize() {
  }

  @Test
  public void testWorkflowExecution() throws Exception {
    SimpleWorkflowRequest workflowRequest = new SimpleWorkflowRequest();
    WorkflowEngineConfig conf = new WorkflowEngineConfig();
    conf.setWorkerThreadCount(2);
    SimpleWorkflowContext workflowContext = (SimpleWorkflowContext) new WorkflowEngine(conf)
        .execute(workflowRequest);
    assertEquals(EXECUTED, workflowContext.isAcknowledgeMessage());
  }

  @Test
  public void testWorkflowExit() throws Exception {
    SimpleExitWorkflowRequest workflowRequest = new SimpleExitWorkflowRequest();
    SimpleExitWorkflowContext workflowContext = (SimpleExitWorkflowContext) new WorkflowEngine()
        .execute(workflowRequest);
    assertEquals(EXECUTED, !workflowContext.isDoNotExecuteStateStatus());

    workflowRequest = new SimpleExitConditionalWorkflowRequest();
    workflowContext = (SimpleExitWorkflowContext) new WorkflowEngine().execute(workflowRequest);
    assertEquals(EXECUTED, !workflowContext.isDoNotExecuteStateStatus());

  }

  @WorkflowRequest
  @Flows(flows = {
      @Flow(concurrent = @States(names = { ExecuteState1.class, ExecuteState2.class })) })
  @Context(name = SimpleWorkflowContext.class)
  public static class SimpleWorkflowRequest {
    private String requestType;

    public String getRequestType() {
      return requestType;
    }

    public void setRequestType(String requestType) {
      this.requestType = requestType;
    }

  }

  @WorkflowRequest
  @Flows(flows = { @Flow(simple = @States(names = { ExitState.class, DoNotExecuteState.class })) })
  @Context(name = SimpleExitWorkflowContext.class)
  public static class SimpleExitWorkflowRequest {

  }

  @WorkflowRequest
  @Flows(flows = { @Flow(conditional = { 
      @ConditionalFlow(exp = "request.isFlag()", states = { ExitState.class, DoNotExecuteState.class }) }) 
  })
  @Context(name = SimpleExitWorkflowContext.class)
  public static class SimpleExitConditionalWorkflowRequest extends SimpleExitWorkflowRequest {
    private boolean flag = true;

    public boolean isFlag() {
      return flag;
    }

    public void setFlag(boolean flag) {
      this.flag = flag;
    }
  }

  public static class ExitState
      implements WorkflowState<SimpleExitWorkflowRequest, SimpleExitWorkflowContext> {

    @Override
    public void execute(SimpleExitWorkflowContext context) throws WorkflowStateException {
      System.out.println("Exit Workflow !!!");
      context.exitWorkflow();
    }

  }

  public static class DoNotExecuteState
      implements WorkflowState<SimpleExitWorkflowRequest, SimpleExitWorkflowContext> {

    @Override
    public void execute(SimpleExitWorkflowContext context) throws WorkflowStateException {
      System.out.println("Do not execute this state !!!!");
      context.setDoNotExecuteStateStatus(EXECUTED);
    }

  }

  public static class ExecuteState1
      implements WorkflowState<SimpleWorkflowRequest, SimpleWorkflowContext> {

    private static final int RETRY_ACK = 2;

    @Override
    public void execute(SimpleWorkflowContext workflowContext) throws WorkflowStateException {
      for (int i = 0; i < RETRY_ACK; i++) {
        if (workflowContext.isRecivedHello()) {
          workflowContext.setAcknowledgeMessage(true);
          break;
        }

        try {
          Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
          throw new WorkflowStateException(e);
        }
      }
    }

  }

  public static class ExecuteState2
      implements WorkflowState<SimpleWorkflowRequest, SimpleWorkflowContext> {

    @Override
    public void execute(SimpleWorkflowContext context) throws WorkflowStateException {
      context.setRecivedHello(true);
    }

  }

  public static class SimpleExitWorkflowContext
      extends com.datarpm.sigma.workflow.WorkflowContext<SimpleExitWorkflowRequest> {

    private static final long serialVersionUID = 1L;
    private boolean doNotExecuteStateStatus;

    public void setDoNotExecuteStateStatus(boolean doNotExecuteStateStatus) {
      this.doNotExecuteStateStatus = doNotExecuteStateStatus;
    }

    public boolean isDoNotExecuteStateStatus() {
      return doNotExecuteStateStatus;
    }

  }

  public static class SimpleWorkflowContext
      extends com.datarpm.sigma.workflow.WorkflowContext<SimpleWorkflowRequest> {

    private static final long serialVersionUID = 1L;
    private boolean acknowledgeMessage;
    private boolean recivedHello;

    public boolean isRecivedHello() {
      return recivedHello;
    }

    public void setAcknowledgeMessage(boolean acknowledgeMessage) {
      this.acknowledgeMessage = acknowledgeMessage;
    }

    public boolean isAcknowledgeMessage() {
      return acknowledgeMessage;
    }

    public void setRecivedHello(boolean recivedHello) {
      this.recivedHello = recivedHello;
    }

  }

}
