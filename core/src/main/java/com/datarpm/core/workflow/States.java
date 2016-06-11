package com.datarpm.core.workflow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface States {
	@SuppressWarnings("rawtypes")
	Class<? extends WorkflowState>[] names();
}
