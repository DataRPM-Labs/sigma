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
package com.datarpm.sigma.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class WorkflowExecutionPlan<R, C extends WorkflowContext<R>> {

  private List<ExecutionFlow> executionPlan;
  private WorkflowContextFactory<R, C> factory;
  private R request;
  private int numberOfStates;

  WorkflowExecutionPlan(R request, WorkflowContextFactory<R, C> factory) {
    executionPlan = new ArrayList<ExecutionFlow>();
    this.request = request;
    this.factory = factory;
  }

  WorkflowContextFactory<R, C> getFactory() {
    return factory;
  }

  List<ExecutionFlow> getExecutionPlan() {
    return executionPlan;
  }

  R getRequest() {
    return request;
  }

  void setExecutionPlan(List<ExecutionFlow> executionPlan) {
    this.executionPlan = executionPlan;
  }

  static enum TYPE {
    STATE_EXECUTION
  };

  static class ExecutionFlow {

    private WorkflowCondition condition;
    private List<Instruction> intructions;

    public ExecutionFlow(WorkflowCondition condition, List<Instruction> intructions) {
      super();
      this.condition = condition;
      this.intructions = intructions;
    }

    public ExecutionFlow(WorkflowCondition condition, Instruction intruction) {
      this.condition = condition;
      this.intructions = new ArrayList<Instruction>();
      intructions.add(intruction);
    }

    public WorkflowCondition getCondition() {
      return condition;
    }

    public void setCondition(WorkflowCondition condition) {
      this.condition = condition;
    }

    public List<Instruction> getIntructions() {
      return intructions;
    }

    public void setIntructions(List<Instruction> intructions) {
      this.intructions = intructions;
    }

  }

  static class Instruction<R, C extends WorkflowContext<R>> {
    private TYPE type;
    private WorkflowState<R, C>[] states;
    private WorkflowStateListner<R, C> listner;

    private Instruction(TYPE type) {
      this.type = type;
    }

    @SuppressWarnings("unchecked")
    void addStates(WorkflowState<R, C>... states) {
      this.states = states;
    }

    void setListner(WorkflowStateListner<R, C> listner) {
      this.listner = listner;
    }

    TYPE getType() {
      return type;
    }

    WorkflowState<R, C>[] getStates() {
      return states;
    }

    WorkflowStateListner<R, C> getListner() {
      return listner;
    }

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Instruction [");
			if (type != null) {
				builder.append("type=");
				builder.append(type);
				builder.append(", ");
			}
			if (states != null) {
				builder.append("states=");
				builder.append(Arrays.toString(states));
			}
			builder.append("]");
			return builder.toString();
		}

  }

  public static class Builder<R, C extends WorkflowContext<R>> {

    List<ExecutionFlow> executionPlan;
    private R request;
    private WorkflowContextFactory<R, C> factory;
    private int numberOfStates;

    public Builder(R request, WorkflowContextFactory<R, C> factory) {
      this.request = request;
      this.factory = factory;
      executionPlan = new ArrayList<ExecutionFlow>();
    }

    @SuppressWarnings("unchecked")
    public Builder<R, C> executeState(WorkflowStateListner<R, C> listener, WorkflowState<R, C> state) {
      Instruction e = new Instruction(TYPE.STATE_EXECUTION);
      e.addStates(state);
      e.setListner(listener);

      ExecutionFlow executionFlow = new ExecutionFlow(new ExecuteAllCondition(), e);

      executionPlan.add(executionFlow);
      numberOfStates++;
      return this;
    }

    @SuppressWarnings("unchecked")
    public Builder<R, C> executeConcurrent(WorkflowStateListner<R, C> listner, WorkflowState<R, C>... states) {
      Instruction e = new Instruction(TYPE.STATE_EXECUTION);
      e.addStates(states);
      e.setListner(listner);

      ExecutionFlow executionFlow = new ExecutionFlow(new ExecuteAllCondition(), e);

      executionPlan.add(executionFlow);
      numberOfStates += states.length;
      return this;
    }

    @SuppressWarnings("unchecked")
    public Builder<R, C> executeState(WorkflowState<R, C> state) {
      Instruction e = new Instruction(TYPE.STATE_EXECUTION);
      e.addStates(state);

      ExecutionFlow executionFlow = new ExecutionFlow(new ExecuteAllCondition(), e);

      executionPlan.add(executionFlow);
      numberOfStates++;
      return this;
    }

    @SuppressWarnings("unchecked")
    public Builder<R, C> executeConcurrent(WorkflowState<R, C>... states) {
      Instruction e = new Instruction(TYPE.STATE_EXECUTION);
      e.addStates(states);

      ExecutionFlow executionFlow = new ExecutionFlow(new ExecuteAllCondition(), e);

      executionPlan.add(executionFlow);
      numberOfStates += states.length;
      return this;
    }

    @SuppressWarnings("unchecked")
    public Builder<R, C> executeConditional(WorkflowCondition condition, WorkflowState<R, C>... states) {
      Instruction e = new Instruction(TYPE.STATE_EXECUTION);
      e.addStates(states);

      ExecutionFlow executionFlow = new ExecutionFlow(condition, e);
      executionPlan.add(executionFlow);
      numberOfStates += states.length;
      return this;
    }

    public WorkflowExecutionPlan<R, C> getPlan() {
      WorkflowExecutionPlan<R, C> workflowExecutionPlan = new WorkflowExecutionPlan<R, C>(request, factory);
      workflowExecutionPlan.setExecutionPlan(executionPlan);
      workflowExecutionPlan.setNumberOfStates(numberOfStates);
      return workflowExecutionPlan;
    }
  }

  public int getNumberOfStates() {
    return numberOfStates;
  }

  public void setNumberOfStates(int numberOfStates) {
    this.numberOfStates = numberOfStates;
  }

  static class ExecuteAllCondition implements WorkflowCondition {

    @Override
    public boolean evaluate(WorkflowContext context, Object request) {
      return true;
    }

  }

}