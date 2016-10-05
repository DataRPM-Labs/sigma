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
package com.datarpm.sigma.workflow.examples.conditional.sql;

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
