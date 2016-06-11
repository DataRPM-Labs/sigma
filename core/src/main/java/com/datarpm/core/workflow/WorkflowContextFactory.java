package com.datarpm.core.workflow;

public interface WorkflowContextFactory<R, C extends WorkflowContext<R>> {

	public C create();
}
