package com.datarpm.core.workflow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalFlow {
  String exp();

  @SuppressWarnings("rawtypes")
  Class<? extends WorkflowState>[] states();
}
