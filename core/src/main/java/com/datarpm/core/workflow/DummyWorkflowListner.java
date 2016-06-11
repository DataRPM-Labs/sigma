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
 * @author vishal
 *
 */
public class DummyWorkflowListner implements WorkflowEventListener {

	@Override
	public void started(WorkflowMetaInfo meta) {

	}

	@Override
	public void onStateExecutionStarted(WorkflowStateInfo state) {

	}

	@Override
	public void onStateExecutionCompleted(WorkflowStateInfo state) {

	}

	@Override
	public void onStateExecutionFailed(WorkflowStateInfo state, Exception e) {

	}

	@Override
	public void onStateExecutionTerminated(WorkflowStateInfo state) {

	}

	@Override
	public void onStateExecutionSkipped(WorkflowStateInfo stateInfo) {

	}

	@Override
	public void finished(WorkflowMetaInfo meta) {

	}

	@Override
	public void finishedWithFailure(WorkflowMetaInfo meta) {

	}

	@Override
	public void onWorkflowTransitOn() {

	}

	@Override
	public void onWorkflowTransitOff() {

	}

	@Override
	public void terminated(WorkflowMetaInfo meta) {
		// TODO Auto-generated method stub
		
	}

}
