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
package com.datarpm.sigma.workflow.examples.dynamic.sql.states;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.datarpm.sigma.workflow.WorkflowState;
import com.datarpm.sigma.workflow.WorkflowStateException;
import com.datarpm.sigma.workflow.examples.dynamic.sql.DatabaseQueryContext;
import com.datarpm.sigma.workflow.examples.dynamic.sql.DatabaseQueryRequest;

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
