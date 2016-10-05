/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package com.datarpm.sigma.workflow.examples.conditional.sql.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;
import com.datarpm.sigma.workflow.examples.conditional.sql.DatabaseQueryContext;
import com.datarpm.sigma.workflow.examples.conditional.sql.DatabaseQueryRequest;

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
