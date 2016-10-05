/**
 * 
 */
package com.datarpm.sigma.workflow.examples.simple.sql;

import com.datarpm.sigma.workflow.Context;
import com.datarpm.sigma.workflow.States;
import com.datarpm.sigma.workflow.WorkflowRequest;
import com.datarpm.sigma.workflow.examples.simple.sql.states.ExecuteSQLQuery;
import com.datarpm.sigma.workflow.examples.simple.sql.states.InitializeConnection;
import com.datarpm.sigma.workflow.examples.simple.sql.states.PrepareSQLQuery;

/**
 * @author vinay
 *
 */
@WorkflowRequest
@States(names = { InitializeConnection.class, PrepareSQLQuery.class, ExecuteSQLQuery.class })
@Context(name = DatabaseQueryContext.class)
public class DatabaseQueryRequest {
  private String connectionURL;
  private String userName;
  private String password;
  private String tableName;
  private String driver;

  public String getConnectionURL() {
    return connectionURL;
  }

  public void setConnectionURL(String connectionURL) {
    this.connectionURL = connectionURL;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getDriver() {
    return this.driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }
}
