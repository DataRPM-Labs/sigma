/**
 * 
 */
package com.datarpm.sigma.workflow.examples.conditional;

import com.datarpm.sigma.workflow.ConditionalFlow;
import com.datarpm.sigma.workflow.Context;
import com.datarpm.sigma.workflow.Flow;
import com.datarpm.sigma.workflow.Flows;
import com.datarpm.sigma.workflow.States;
import com.datarpm.sigma.workflow.WorkflowRequest;
import com.datarpm.sigma.workflow.examples.conditional.states.ExecuteSQLQuery;
import com.datarpm.sigma.workflow.examples.conditional.states.ExportResultSetToCSV;
import com.datarpm.sigma.workflow.examples.conditional.states.InitializeConnection;
import com.datarpm.sigma.workflow.examples.conditional.states.PrepareSQLQuery;

/**
 * @author vinay
 *
 */
@WorkflowRequest
@Flows(flows = {
    @Flow(simple = @States(names = { InitializeConnection.class, PrepareSQLQuery.class,
        ExecuteSQLQuery.class })),
    @Flow(conditional = {
        @ConditionalFlow(exp = "request.isExportResultSet() && request.getExportFormat() == \"CSV\"", states = {
            ExportResultSetToCSV.class }) }) })
@Context(name = DatabaseQueryContext.class)
public class DatabaseQueryRequest {
  private String connectionURL;
  private String userName;
  private String password;
  private String tableName;
  private boolean isExportResultSet;
  private String exportFormat;
  private String exportPath;
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

  public boolean isExportResultSet() {
    return isExportResultSet;
  }

  public void setExportResultSet(boolean isExportResultSet) {
    this.isExportResultSet = isExportResultSet;
  }

  public String getExportFormat() {
    return exportFormat;
  }

  public void setExportFormat(String exportFormat) {
    this.exportFormat = exportFormat;
  }

  public void setExportPath(String exportPath) {
    this.exportPath = exportPath;
  }

  public String getExportPath() {
    return exportPath;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public String getDriver() {
    return driver;
  }
}
