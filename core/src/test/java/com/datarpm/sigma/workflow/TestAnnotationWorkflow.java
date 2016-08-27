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
  public void testConditionalWorkflowExecution() throws Exception {
    SimpleWorkflowRequest workflowRequest = new SimpleWorkflowRequest();
    WorkflowEngineConfig conf = new WorkflowEngineConfig();
    conf.setWorkerThreadCount(2);
    SimpleWorkflowContext workflowContext = (SimpleWorkflowContext) new WorkflowEngine(conf)
        .execute(workflowRequest);
    assertEquals(EXECUTED, workflowContext.isAcknowledgeMessage());
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
