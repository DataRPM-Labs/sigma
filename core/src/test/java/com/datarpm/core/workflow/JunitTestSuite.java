package com.datarpm.core.workflow;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.datarpm.core.workflow.plan.TestWorkflowMultiExecutionPlan;
import com.datarpm.core.workflow.plan.TestWorkflowSimpleExecutionPlan;

/**
 * @author vishal
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TestWorkflowSkipOnFailure.class, TestWorkflowStateListner.class,
    TestWorkflowMultiExecutionPlan.class, TestWorkflowSimpleExecutionPlan.class, TestWorkflowConditional.class })
public class JunitTestSuite {

  /**
   * 
   */
  public JunitTestSuite() {
  }

}
