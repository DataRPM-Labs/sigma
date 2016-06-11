/**
 * 
 */
package com.datarpm.core.workflow;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vishal
 * 
 */
public class BaseContext implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * This {@link Map} instance holds parameter information, identified by
	 * parameter id.
	 */
	private Map<String, String> parameter;
	/**
	 * This {@link Map} instance holds attribute information identified by
	 * attribute id.
	 */
	private Map<String, Object> attributes;

	public BaseContext() {
		parameter = new ConcurrentHashMap<String, String>();
		attributes = new ConcurrentHashMap<String, Object>();
	}

	/**
	 * This method set the parameter value.
	 * 
	 * @param id
	 * @param value
	 */
	public void setParameter(String id, String value) {
		parameter.put(id, value);
	}

	/**
	 * @param id
	 * @return
	 */
	public String getParameter(String id) {
		return parameter.get(id);
	}

	public void setAttribute(String id, Object value) {
		attributes.put(id, value);
	}

	public Object getAttribute(String id) {
		return attributes.get(id);
	}
}
