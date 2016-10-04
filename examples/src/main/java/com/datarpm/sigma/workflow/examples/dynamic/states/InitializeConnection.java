/**
 * 
 */
package com.datarpm.sigma.workflow.examples.dynamic.states;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;
import com.datarpm.sigma.workflow.examples.dynamic.DatabaseQueryContext;
import com.datarpm.sigma.workflow.examples.dynamic.DatabaseQueryRequest;

/**
 * @author vinay
 *
 */
public class InitializeConnection
    implements WorkflowState<DatabaseQueryRequest, DatabaseQueryContext> {

  @Override
  public void execute(DatabaseQueryContext context) throws WorkflowStateException {
    DatabaseQueryRequest request = context.getRequest();
    String userName = request.getUserName();
    String password = request.getPassword();
    String connectionURL = request.getConnectionURL();
    String driver = request.getDriver();

    try {
      Class.forName(driver);
      Connection connection = DriverManager.getConnection(connectionURL, userName, password);
      context.setConnection(connection);
    } catch (SQLException | ClassNotFoundException e) {
      context.setSuccess(false);
      throw new WorkflowStateException("Could not initialize the connection.", e);
    }
  }
}
