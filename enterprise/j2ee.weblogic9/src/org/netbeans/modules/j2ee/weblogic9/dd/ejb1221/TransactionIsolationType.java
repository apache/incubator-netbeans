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
 *	This generated bean class TransactionIsolationType matches the schema element 'transaction-isolationType'.
 *  The root bean class is WeblogicEjbJar
 *
 *	Generated on Tue Jul 25 03:26:55 PDT 2017
 * @Generated
 */

package org.netbeans.modules.j2ee.weblogic9.dd.ejb1221;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class TransactionIsolationType extends org.netbeans.modules.schema2beans.BaseBean
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);
	;	// NOI18N

	static public final String ID = "Id";	// NOI18N
	static public final String ISOLATION_LEVEL = "IsolationLevel";	// NOI18N
	static public final String ISOLATIONLEVELID = "IsolationLevelId";	// NOI18N
	static public final String METHOD = "Method";	// NOI18N

	public TransactionIsolationType() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public TransactionIsolationType(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(2);
		this.createProperty("isolation-level", 	// NOI18N
			ISOLATION_LEVEL, 
			Common.TYPE_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(ISOLATION_LEVEL, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("method", 	// NOI18N
			METHOD, 
			Common.TYPE_1_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			MethodType.class);
		this.createAttribute(METHOD, "id", "Id", 
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
	public void setIsolationLevel(java.lang.String value) {
		this.setValue(ISOLATION_LEVEL, value);
	}

	//
	public java.lang.String getIsolationLevel() {
		return (java.lang.String)this.getValue(ISOLATION_LEVEL);
	}

	// This attribute is optional
	public void setIsolationLevelId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(ISOLATION_LEVEL) == 0) {
			setValue(ISOLATION_LEVEL, "");
		}
		setAttributeValue(ISOLATION_LEVEL, "Id", value);
	}

	//
	public java.lang.String getIsolationLevelId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(ISOLATION_LEVEL) == 0) {
			return null;
		} else {
			return getAttributeValue(ISOLATION_LEVEL, "Id");
		}
	}

	// This attribute is an array containing at least one element
	public void setMethod(int index, MethodType value) {
		this.setValue(METHOD, index, value);
	}

	//
	public MethodType getMethod(int index) {
		return (MethodType)this.getValue(METHOD, index);
	}

	// Return the number of properties
	public int sizeMethod() {
		return this.size(METHOD);
	}

	// This attribute is an array containing at least one element
	public void setMethod(MethodType[] value) {
		this.setValue(METHOD, value);
	}

	//
	public MethodType[] getMethod() {
		return (MethodType[])this.getValues(METHOD);
	}

	// Add a new element returning its index in the list
	public int addMethod(org.netbeans.modules.j2ee.weblogic9.dd.ejb1221.MethodType value) {
		int positionOfNewItem = this.addValue(METHOD, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeMethod(org.netbeans.modules.j2ee.weblogic9.dd.ejb1221.MethodType value) {
		return this.removeValue(METHOD, value);
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public MethodType newMethodType() {
		return new MethodType();
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
		// Validating property isolationLevel
		if (getIsolationLevel() == null) {
			throw new org.netbeans.modules.schema2beans.ValidateException("getIsolationLevel() == null", org.netbeans.modules.schema2beans.ValidateException.FailureType.NULL_VALUE, "isolationLevel", this);	// NOI18N
		}
		// Validating property isolationLevelId
		if (getIsolationLevelId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getIsolationLevelId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "isolationLevelId", this);	// NOI18N
			}
		}
		// Validating property method
		if (sizeMethod() == 0) {
			throw new org.netbeans.modules.schema2beans.ValidateException("sizeMethod() == 0", org.netbeans.modules.schema2beans.ValidateException.FailureType.NULL_VALUE, "method", this);	// NOI18N
		}
		for (int _index = 0; _index < sizeMethod(); ++_index) {
			org.netbeans.modules.j2ee.weblogic9.dd.ejb1221.MethodType element = getMethod(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	// Dump the content of this bean returning it as a String
	public void dump(StringBuffer str, String indent){
		String s;
		Object o;
		org.netbeans.modules.schema2beans.BaseBean n;
		str.append(indent);
		str.append("IsolationLevel");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getIsolationLevel();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(ISOLATION_LEVEL, 0, str, indent);

		str.append(indent);
		str.append("Method["+this.sizeMethod()+"]");	// NOI18N
		for(int i=0; i<this.sizeMethod(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getMethod(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(METHOD, i, str, indent);
		}

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("TransactionIsolationType\n");	// NOI18N
		this.dump(str, "\n  ");	// NOI18N
		return str.toString();
	}}

// END_NOI18N

