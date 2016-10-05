/**
 * 
 */
package com.datarpm.sigma.workflow.examples.conditional.sql;

import java.sql.Connection;
import java.sql.ResultSet;

import com.datarpm.sigma.workflow.WorkflowContext;

/**
 * @author vinay
 *
 */
public class DatabaseQueryContext extends WorkflowContext<DatabaseQueryRequest> {

  private static final long serialVersionUID = 1L;
  private Connection connection;
  private String sqlQuery;
  private ResultSet resultSet;

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public String getSqlQuery() {
    return sqlQuery;
  }

  public void setSqlQuery(String sqlQuery) {
    this.sqlQuery = sqlQuery;
  }

  public ResultSet getResultSet() {
    return resultSet;
  }

  public void setResultSet(ResultSet resultSet) {
    this.resultSet = resultSet;
  }
}
