/**
 * 
 */
package com.datarpm.sigma.workflow.examples.dynamic;

import java.sql.ResultSet;

import com.datarpm.sigma.workflow.WorkflowContextFactory;
import com.datarpm.sigma.workflow.WorkflowEngine;
import com.datarpm.sigma.workflow.WorkflowExecutionException;
import com.datarpm.sigma.workflow.WorkflowExecutionPlan;
import com.datarpm.sigma.workflow.WorkflowExecutionPlan.Builder;
import com.datarpm.sigma.workflow.examples.dynamic.states.ExecuteSQLQuery;
import com.datarpm.sigma.workflow.examples.dynamic.states.InitializeConnection;
import com.datarpm.sigma.workflow.examples.dynamic.states.PrepareSQLQuery;

/**
 * @author vinay
 *
 */
public class DatabaseQueryWithDynamicWorkflow {

  public static void main(String[] args) {
    DatabaseQueryRequest request = new DatabaseQueryRequest();
    request.setUserName("sigma");
    request.setPassword("sigma");
    request.setTableName("workflow");
    request.setDriver("com.mysql.jdbc.Driver");
    request.setConnectionURL("jdbc:mysql://localhost:3307/sigmaDb");
    Builder<DatabaseQueryRequest, DatabaseQueryContext> builder = null;
    WorkflowContextFactory<DatabaseQueryRequest, DatabaseQueryContext> contextFactory = new WorkflowContextFactory<DatabaseQueryRequest, DatabaseQueryContext>() {
      @Override
      public DatabaseQueryContext create() {
        return new DatabaseQueryContext();
      }
    };

    builder = new WorkflowExecutionPlan.Builder<DatabaseQueryRequest, DatabaseQueryContext>(request,
        contextFactory);
    builder.executeState(new InitializeConnection());
    builder.executeState(new PrepareSQLQuery());
    builder.executeState(new ExecuteSQLQuery());

    try {
      DatabaseQueryContext context = new WorkflowEngine().executePlan(builder.getPlan());
      ResultSet resultSet = context.getResultSet();
      System.out.println("Result Set : " + resultSet.toString());
    } catch (WorkflowExecutionException e) {
      e.printStackTrace();
    }
  }
}
