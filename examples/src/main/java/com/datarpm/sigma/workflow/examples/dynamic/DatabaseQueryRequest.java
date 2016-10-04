/**
 * 
 */
package com.datarpm.sigma.workflow.examples.dynamic;

/**
 * @author vinay
 *
 */
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
