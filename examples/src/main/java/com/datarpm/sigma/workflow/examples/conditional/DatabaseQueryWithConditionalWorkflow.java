/**
 * 
 */
package com.datarpm.sigma.workflow.examples.conditional;

import java.sql.ResultSet;

import com.datarpm.sigma.workflow.WorkflowEngine;
import com.datarpm.sigma.workflow.WorkflowExecutionException;

/**
 * @author vinay
 *
 */
public class DatabaseQueryWithConditionalWorkflow {
  public static void main(String[] args) {
    DatabaseQueryRequest request = new DatabaseQueryRequest();
    request.setUserName("sigma");
    request.setPassword("sigma");
    request.setTableName("workflow");
    request.setDriver("com.mysql.jdbc.Driver");
    request.setConnectionURL("jdbc:mysql://localhost:3307/sigmaDb");
    request.setExportResultSet(true);
    request.setExportFormat("CSV");
    request.setExportPath("/tmp/resultSet.csv");
    try {
      DatabaseQueryContext context = (DatabaseQueryContext) new WorkflowEngine().execute(request);
      ResultSet resultSet = context.getResultSet();
      System.out.println("Result Set : " + resultSet.toString());
    } catch (WorkflowExecutionException e) {
      e.printStackTrace();
    }
  }
}
