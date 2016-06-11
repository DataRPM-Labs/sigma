/*******************************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package com.datarpm.core.workflow;

/**
 * @author alok
 * 
 */
public class WorkflowEngineConfig {

	private int workerThreadCount;
	private WorkflowEventListener workflowListner;
	private String transitWorkflowId;

	public int getWorkerThreadCount() {
		return workerThreadCount;
	}

	public WorkflowEngineConfig setWorkerThreadCount(int workerThreadCount) {
		this.workerThreadCount = workerThreadCount;
		return this;
	}

	public WorkflowEventListener getWorkflowListner() {
		return workflowListner;
	}

	public void setWorkflowListner(WorkflowEventListener workflowListner) {
		this.workflowListner = workflowListner;
	}

	public void setTransitWorkflowId(String transitWorkflowId) {
		this.transitWorkflowId = transitWorkflowId;
	}

	public String getTransitWorkflowId() {
		return transitWorkflowId;
	}

}
