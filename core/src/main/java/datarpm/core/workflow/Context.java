package datarpm.core.workflow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Context {
	@SuppressWarnings("rawtypes")
	Class<? extends WorkflowContext> name();
}
