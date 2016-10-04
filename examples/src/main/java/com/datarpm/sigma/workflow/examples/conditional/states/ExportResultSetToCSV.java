/**
 * 
 */
package com.datarpm.sigma.workflow.examples.conditional.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;
import com.datarpm.sigma.workflow.examples.conditional.DatabaseQueryContext;
import com.datarpm.sigma.workflow.examples.conditional.DatabaseQueryRequest;

/**
 * @author vinay
 *
 */
public class ExportResultSetToCSV
    implements WorkflowState<DatabaseQueryRequest, DatabaseQueryContext> {

  @Override
  public void execute(DatabaseQueryContext context) throws WorkflowStateException {
    ResultSet resultSet = context.getResultSet();
    DatabaseQueryRequest request = context.getRequest();
    try {
      exportToCSV(resultSet, request.getExportPath());
    } catch (FileNotFoundException | SQLException e) {
      throw new WorkflowStateException("Could not export the ResultSet to CSV", e);
    }
  }

  private void exportToCSV(ResultSet resultSet, String exportPath)
      throws FileNotFoundException, SQLException {
    PrintWriter csvWriter = new PrintWriter(new File(exportPath));
    ResultSetMetaData meta = resultSet.getMetaData();
    int numberOfColumns = meta.getColumnCount();
    String dataHeaders = "\"" + meta.getColumnName(1) + "\"";
    for (int i = 2; i < numberOfColumns + 1; i++) {
      dataHeaders += ",\"" + meta.getColumnName(i) + "\"";
    }
    csvWriter.println(dataHeaders);
    while (resultSet.next()) {
      String row = "\"" + resultSet.getString(1) + "\"";
      for (int i = 2; i < numberOfColumns + 1; i++) {
        row += ",\"" + resultSet.getString(i) + "\"";
      }
      csvWriter.println(row);
    }
    csvWriter.close();
  }
}
