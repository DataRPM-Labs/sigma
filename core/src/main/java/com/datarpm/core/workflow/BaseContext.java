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
