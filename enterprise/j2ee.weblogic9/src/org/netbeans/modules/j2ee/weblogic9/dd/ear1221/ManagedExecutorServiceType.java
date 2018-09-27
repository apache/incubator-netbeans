/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 *	This generated bean class ManagedExecutorServiceType matches the schema element 'managed-executor-serviceType'.
 *  The root bean class is WeblogicApplication
 *
 *	Generated on Tue Jul 25 03:26:48 PDT 2017
 * @Generated
 */

package org.netbeans.modules.j2ee.weblogic9.dd.ear1221;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class ManagedExecutorServiceType extends org.netbeans.modules.schema2beans.BaseBean
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);
	;	// NOI18N

	static public final String ID = "Id";	// NOI18N
	static public final String NAME = "Name";	// NOI18N
	static public final String NAMEID = "NameId";	// NOI18N
	static public final String DISPATCH_POLICY = "DispatchPolicy";	// NOI18N
	static public final String DISPATCHPOLICYID = "DispatchPolicyId";	// NOI18N
	static public final String MAX_CONCURRENT_LONG_RUNNING_REQUESTS = "MaxConcurrentLongRunningRequests";	// NOI18N
	static public final String MAXCONCURRENTLONGRUNNINGREQUESTSJ2EEID = "MaxConcurrentLongRunningRequestsJ2eeId";	// NOI18N
	static public final String MAXCONCURRENTLONGRUNNINGREQUESTSJ2EEID2 = "MaxConcurrentLongRunningRequestsJ2eeId2";	// NOI18N
	static public final String LONG_RUNNING_PRIORITY = "LongRunningPriority";	// NOI18N
	static public final String LONGRUNNINGPRIORITYJ2EEID = "LongRunningPriorityJ2eeId";	// NOI18N
	static public final String LONGRUNNINGPRIORITYMAXCONCURRENTLONGRUNNINGREQUESTSJ2EEID2 = "LongRunningPriorityMaxConcurrentLongRunningRequestsJ2eeId2";	// NOI18N

	public ManagedExecutorServiceType() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public ManagedExecutorServiceType(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(4);
		this.createProperty("name", 	// NOI18N
			NAME, 
			Common.TYPE_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(NAME, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("dispatch-policy", 	// NOI18N
			DISPATCH_POLICY, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(DISPATCH_POLICY, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("max-concurrent-long-running-requests", 	// NOI18N
			MAX_CONCURRENT_LONG_RUNNING_REQUESTS, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.math.BigInteger.class);
		this.createAttribute(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "j2ee:id", "J2eeId", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "j2ee:id", "MaxConcurrentLongRunningRequestsJ2eeId2", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("long-running-priority", 	// NOI18N
			LONG_RUNNING_PRIORITY, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.math.BigInteger.class);
		this.createAttribute(LONG_RUNNING_PRIORITY, "j2ee:id", "J2eeId", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(LONG_RUNNING_PRIORITY, "j2ee:id", "MaxConcurrentLongRunningRequestsJ2eeId2", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.initialize(options);
	}

	// Setting the default values of the properties
	void initialize(int options) {

	}

	// This attribute is optional
	public void setId(java.lang.String value) {
		setAttributeValue(ID, value);
	}

	//
	public java.lang.String getId() {
		return getAttributeValue(ID);
	}

	// This attribute is mandatory
	public void setName(java.lang.String value) {
		this.setValue(NAME, value);
	}

	//
	public java.lang.String getName() {
		return (java.lang.String)this.getValue(NAME);
	}

	// This attribute is optional
	public void setNameId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(NAME) == 0) {
			setValue(NAME, "");
		}
		setAttributeValue(NAME, "Id", value);
	}

	//
	public java.lang.String getNameId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(NAME) == 0) {
			return null;
		} else {
			return getAttributeValue(NAME, "Id");
		}
	}

	// This attribute is optional
	public void setDispatchPolicy(java.lang.String value) {
		this.setValue(DISPATCH_POLICY, value);
	}

	//
	public java.lang.String getDispatchPolicy() {
		return (java.lang.String)this.getValue(DISPATCH_POLICY);
	}

	// This attribute is optional
	public void setDispatchPolicyId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(NAME) == 0) {
			setValue(NAME, "");
		}
		setAttributeValue(NAME, "Id", value);
	}

	//
	public java.lang.String getDispatchPolicyId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(NAME) == 0) {
			return null;
		} else {
			return getAttributeValue(NAME, "Id");
		}
	}

	// This attribute is optional
	public void setMaxConcurrentLongRunningRequests(java.math.BigInteger value) {
		this.setValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, value);
	}

	//
	public java.math.BigInteger getMaxConcurrentLongRunningRequests() {
		return (java.math.BigInteger)this.getValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS);
	}

	// This attribute is optional
	public void setMaxConcurrentLongRunningRequestsJ2eeId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(MAX_CONCURRENT_LONG_RUNNING_REQUESTS) == 0) {
			setValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "");
		}
		setAttributeValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "J2eeId", value);
	}

	//
	public java.lang.String getMaxConcurrentLongRunningRequestsJ2eeId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(MAX_CONCURRENT_LONG_RUNNING_REQUESTS) == 0) {
			return null;
		} else {
			return getAttributeValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "J2eeId");
		}
	}

	// This attribute is optional
	public void setMaxConcurrentLongRunningRequestsJ2eeId2(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(MAX_CONCURRENT_LONG_RUNNING_REQUESTS) == 0) {
			setValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "");
		}
		setAttributeValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "MaxConcurrentLongRunningRequestsJ2eeId2", value);
	}

	//
	public java.lang.String getMaxConcurrentLongRunningRequestsJ2eeId2() {
		// If our element does not exist, then the attribute does not exist.
		if (size(MAX_CONCURRENT_LONG_RUNNING_REQUESTS) == 0) {
			return null;
		} else {
			return getAttributeValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "MaxConcurrentLongRunningRequestsJ2eeId2");
		}
	}

	// This attribute is optional
	public void setLongRunningPriority(java.math.BigInteger value) {
		this.setValue(LONG_RUNNING_PRIORITY, value);
	}

	//
	public java.math.BigInteger getLongRunningPriority() {
		return (java.math.BigInteger)this.getValue(LONG_RUNNING_PRIORITY);
	}

	// This attribute is optional
	public void setLongRunningPriorityJ2eeId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(MAX_CONCURRENT_LONG_RUNNING_REQUESTS) == 0) {
			setValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "");
		}
		setAttributeValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "J2eeId", value);
	}

	//
	public java.lang.String getLongRunningPriorityJ2eeId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(MAX_CONCURRENT_LONG_RUNNING_REQUESTS) == 0) {
			return null;
		} else {
			return getAttributeValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "J2eeId");
		}
	}

	// This attribute is optional
	public void setLongRunningPriorityMaxConcurrentLongRunningRequestsJ2eeId2(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(MAX_CONCURRENT_LONG_RUNNING_REQUESTS) == 0) {
			setValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "");
		}
		setAttributeValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "MaxConcurrentLongRunningRequestsJ2eeId2", value);
	}

	//
	public java.lang.String getLongRunningPriorityMaxConcurrentLongRunningRequestsJ2eeId2() {
		// If our element does not exist, then the attribute does not exist.
		if (size(MAX_CONCURRENT_LONG_RUNNING_REQUESTS) == 0) {
			return null;
		} else {
			return getAttributeValue(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, "MaxConcurrentLongRunningRequestsJ2eeId2");
		}
	}

	//
	public static void addComparator(org.netbeans.modules.schema2beans.BeanComparator c) {
		comparators.add(c);
	}

	//
	public static void removeComparator(org.netbeans.modules.schema2beans.BeanComparator c) {
		comparators.remove(c);
	}
	public void validate() throws org.netbeans.modules.schema2beans.ValidateException {
		boolean restrictionFailure = false;
		boolean restrictionPassed = false;
		// Validating property id
		if (getId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "id", this);	// NOI18N
			}
		}
		// Validating property name
		if (getName() == null) {
			throw new org.netbeans.modules.schema2beans.ValidateException("getName() == null", org.netbeans.modules.schema2beans.ValidateException.FailureType.NULL_VALUE, "name", this);	// NOI18N
		}
		// Validating property nameId
		if (getNameId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getNameId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "nameId", this);	// NOI18N
			}
		}
		// Validating property dispatchPolicy
		// Validating property dispatchPolicyId
		if (getDispatchPolicyId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getDispatchPolicyId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "dispatchPolicyId", this);	// NOI18N
			}
		}
		// Validating property maxConcurrentLongRunningRequests
		if (getMaxConcurrentLongRunningRequests() != null) {
			if (getMaxConcurrentLongRunningRequests().compareTo(new java.math.BigInteger("0")) < 0) {
				restrictionFailure = true;
			}
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getMaxConcurrentLongRunningRequests() minInclusive (0)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "maxConcurrentLongRunningRequests", this);	// NOI18N
			}
		}
		// Validating property maxConcurrentLongRunningRequestsJ2eeId
		if (getMaxConcurrentLongRunningRequestsJ2eeId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getMaxConcurrentLongRunningRequestsJ2eeId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "maxConcurrentLongRunningRequestsJ2eeId", this);	// NOI18N
			}
		}
		// Validating property maxConcurrentLongRunningRequestsJ2eeId2
		if (getMaxConcurrentLongRunningRequestsJ2eeId2() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getMaxConcurrentLongRunningRequestsJ2eeId2() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "maxConcurrentLongRunningRequestsJ2eeId2", this);	// NOI18N
			}
		}
		// Validating property longRunningPriority
		if (getLongRunningPriority() != null) {
			if (getLongRunningPriority().compareTo(new java.math.BigInteger("0")) < 0) {
				restrictionFailure = true;
			}
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getLongRunningPriority() minInclusive (0)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "longRunningPriority", this);	// NOI18N
			}
		}
		// Validating property longRunningPriorityJ2eeId
		if (getLongRunningPriorityJ2eeId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getLongRunningPriorityJ2eeId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "longRunningPriorityJ2eeId", this);	// NOI18N
			}
		}
		// Validating property longRunningPriorityMaxConcurrentLongRunningRequestsJ2eeId2
		if (getLongRunningPriorityMaxConcurrentLongRunningRequestsJ2eeId2() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getLongRunningPriorityMaxConcurrentLongRunningRequestsJ2eeId2() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "longRunningPriorityMaxConcurrentLongRunningRequestsJ2eeId2", this);	// NOI18N
			}
		}
	}

	// Dump the content of this bean returning it as a String
	public void dump(StringBuffer str, String indent){
		String s;
		Object o;
		org.netbeans.modules.schema2beans.BaseBean n;
		str.append(indent);
		str.append("Name");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getName();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(NAME, 0, str, indent);

		str.append(indent);
		str.append("DispatchPolicy");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getDispatchPolicy();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(DISPATCH_POLICY, 0, str, indent);

		str.append(indent);
		str.append("MaxConcurrentLongRunningRequests");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getMaxConcurrentLongRunningRequests();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(MAX_CONCURRENT_LONG_RUNNING_REQUESTS, 0, str, indent);

		str.append(indent);
		str.append("LongRunningPriority");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getLongRunningPriority();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(LONG_RUNNING_PRIORITY, 0, str, indent);

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("ManagedExecutorServiceType\n");	// NOI18N
		this.dump(str, "\n  ");	// NOI18N
		return str.toString();
	}}

// END_NOI18N

