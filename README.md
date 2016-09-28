# DataRPM μFlow
### The Micro Workflow for Java Developers

Key Features:

1. Annotation-based Definition.
2. Ability to define Fixed or Conditional Flow
3. Ability to create Dynamic Flow Definition, based on Run Time context.
4. Ability to share data with different States of a Flow.
5. Execution Thread Pool control.

### Linking
You can link against this library in your program at the following coordinates:

Using Maven:
```
<dependency>
    <groupId>com.datarpm.sigma</groupId>
    <artifactId>datarpm-sigma-core</artifactId>
    <version>1.0</version>
</dependency>
```

### Requirements
You need to have 1.6+ version of Java installed.

### Simple Annotation Flow
Following example connects to database and executes query
```
DatabaseQueryRequest request = DatabaseQueryRequest(connectionURL, userName, password);
DatabaseQueryContext context = new WorkflowEngine().execute(request);
ResultSet resultset = context.getResultSet();
```
###### Workflow Request
```
@WorkflowRequest
@States(names = { InitializeConnection.class, PrepareSQLQuery.class, ExecuteSQLQuery.class })
@Context(name = DatabaseQueryContext.class)
public class DatabaseQueryRequest {
  private String connectionURL;
  private String userName;
  private String password;
  // declare input variables
}
```
###### Workflow Context
```
public class DatabaseQueryContext extends WorkflowContext<DatabaseQueryRequest> {
  private Connection connection;
  private String sqlQuery;
  private ResultSet resultSet;
}
```
###### Workflow State
```
public class InitializeConnection implements WorkflowState<DatabaseQueryRequest, DatabaseQueryContext> {
  
  public void execute(DatabaseQueryContext context) throws WorkflowStateException {
    // Reference to workflow request
    DatabaseQueryRequest request = context.getRequest();
    String userName = request.getUserName();
    String password = request.getPassword();
    Connection jdbcConnection = // initialize connection
    // Set Connection object to context
    context.setConnection(jdbcConnection);
  }
}
```
```
public class ExecuteSQLQuery implements WorkflowState<DatabaseQueryRequest, DatabaseQueryContext> {
  
  public void execute(DatabaseQueryContext context) throws WorkflowStateException {
    // Reference to connection object set from InitializeConnection state 
    Connection jdbcConnection = context.getConnection();
    ResultSet rs = jdbcConnection.executeQuery("-- some query --");
    context.setResultSet(rs)
  }
}
```

### Conditional Annotation Flow

### Dynamic Workflow Plan

### Control Concurrency

```
WorkflowEngineConfig config = new WorkflowEngineConfig();
config.setWorkerThreadCount(NUMBER_OF_THREADS);
WorkflowEngine engine = new WorkflowEngine(config);
```

### Building from source

simply run script ```./sbuild/build.sh``` in cloned directory

### License
```
This software is licensed under the Apache License, version 2 ("ALv2"), quoted below.

Copyright 2012-2016 DataRPM <http://datarpm.com>

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```
