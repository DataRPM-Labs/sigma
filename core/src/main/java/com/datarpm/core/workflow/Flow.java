package com.datarpm.core.workflow;

public @interface Flow {

  ConditionalFlow[] conditional() default @ConditionalFlow(exp = "false", states = {})
  ;

  States simple() default @States(names = {})
  ;

}
