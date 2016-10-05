/**
 * 
 */
package com.datarpm.sigma.workflow.examples.conditional.sql.states;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;
import com.datarpm.sigma.workflow.examples.conditional.sql.DatabaseQueryContext;
import com.datarpm.sigma.workflow.examples.conditional.sql.DatabaseQueryRequest;

/**
 * @author vinay
 *
 */
public class ExecuteSQLQuery implements WorkflowState<DatabaseQueryRequest, DatabaseQueryContext> {

  @Override
  public void execute(DatabaseQueryContext context) throws WorkflowStateException {
    Connection connection = context.getConnection();
    String sqlQuery = context.getSqlQuery();
    ResultSet resultSet;
    try {
      Statement statement = connection.createStatement();
      resultSet = statement.executeQuery(sqlQuery);
      context.setResultSet(resultSet);
    } catch (SQLException e) {
      context.setSuccess(false);
      throw new WorkflowStateException("Could not execute the SQL", e);
    }
  }
}
