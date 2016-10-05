/**
 * 
 */
package com.datarpm.sigma.workflow.examples.conditional.sql.states;

import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;
import com.datarpm.sigma.workflow.examples.conditional.sql.DatabaseQueryContext;
import com.datarpm.sigma.workflow.examples.conditional.sql.DatabaseQueryRequest;

/**
 * @author vinay
 *
 */
public class PrepareSQLQuery implements WorkflowState<DatabaseQueryRequest, DatabaseQueryContext> {

  @Override
  public void execute(DatabaseQueryContext context) throws WorkflowStateException {
    DatabaseQueryRequest request = context.getRequest();
    String tableName = request.getTableName();
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("select * from ");
    strBuilder.append(tableName);
    strBuilder.append(" limit 10");
    String query = strBuilder.toString();
    context.setSqlQuery(query);
  }
}
