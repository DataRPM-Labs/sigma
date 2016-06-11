/**
 * 
 */
package datarpm.core.workflow;

import org.junit.Before;
import org.junit.Test;

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

  public static class ConditionalWorkflowContext extends datarpm.core.workflow.WorkflowContext<SimpleWorkflowRequest> {

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
