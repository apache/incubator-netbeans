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
 *	This generated bean class PortInfoType matches the schema element 'port-infoType'.
 *  The root bean class is WeblogicWebApp
 *
 *	Generated on Tue Jul 25 03:27:01 PDT 2017
 * @Generated
 */

package org.netbeans.modules.j2ee.weblogic9.dd.web1030;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class PortInfoType extends org.netbeans.modules.schema2beans.BaseBean
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);
	;	// NOI18N

	static public final String PORT_NAME = "PortName";	// NOI18N
	static public final String PORTNAMEJ2EEID = "PortNameJ2eeId";	// NOI18N
	static public final String STUB_PROPERTY = "StubProperty";	// NOI18N
	static public final String CALL_PROPERTY = "CallProperty";	// NOI18N

	public PortInfoType() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public PortInfoType(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(3);
		this.createProperty("port-name", 	// NOI18N
			PORT_NAME, 
			Common.TYPE_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(PORT_NAME, "j2ee:id", "J2eeId", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("stub-property", 	// NOI18N
			STUB_PROPERTY, 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			PropertyNamevalueType.class);
		this.createProperty("call-property", 	// NOI18N
			CALL_PROPERTY, 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			PropertyNamevalueType.class);
		this.initialize(options);
	}

	// Setting the default values of the properties
	void initialize(int options) {

	}

	// This attribute is mandatory
	public void setPortName(java.lang.String value) {
		this.setValue(PORT_NAME, value);
	}

	//
	public java.lang.String getPortName() {
		return (java.lang.String)this.getValue(PORT_NAME);
	}

	// This attribute is optional
	public void setPortNameJ2eeId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(PORT_NAME) == 0) {
			setValue(PORT_NAME, "");
		}
		setAttributeValue(PORT_NAME, "J2eeId", value);
	}

	//
	public java.lang.String getPortNameJ2eeId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(PORT_NAME) == 0) {
			return null;
		} else {
			return getAttributeValue(PORT_NAME, "J2eeId");
		}
	}

	// This attribute is an array, possibly empty
	public void setStubProperty(int index, PropertyNamevalueType value) {
		this.setValue(STUB_PROPERTY, index, value);
	}

	//
	public PropertyNamevalueType getStubProperty(int index) {
		return (PropertyNamevalueType)this.getValue(STUB_PROPERTY, index);
	}

	// Return the number of properties
	public int sizeStubProperty() {
		return this.size(STUB_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setStubProperty(PropertyNamevalueType[] value) {
		this.setValue(STUB_PROPERTY, value);
	}

	//
	public PropertyNamevalueType[] getStubProperty() {
		return (PropertyNamevalueType[])this.getValues(STUB_PROPERTY);
	}

	// Add a new element returning its index in the list
	public int addStubProperty(org.netbeans.modules.j2ee.weblogic9.dd.web1030.PropertyNamevalueType value) {
		int positionOfNewItem = this.addValue(STUB_PROPERTY, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeStubProperty(org.netbeans.modules.j2ee.weblogic9.dd.web1030.PropertyNamevalueType value) {
		return this.removeValue(STUB_PROPERTY, value);
	}

	// This attribute is an array, possibly empty
	public void setCallProperty(int index, PropertyNamevalueType value) {
		this.setValue(CALL_PROPERTY, index, value);
	}

	//
	public PropertyNamevalueType getCallProperty(int index) {
		return (PropertyNamevalueType)this.getValue(CALL_PROPERTY, index);
	}

	// Return the number of properties
	public int sizeCallProperty() {
		return this.size(CALL_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setCallProperty(PropertyNamevalueType[] value) {
		this.setValue(CALL_PROPERTY, value);
	}

	//
	public PropertyNamevalueType[] getCallProperty() {
		return (PropertyNamevalueType[])this.getValues(CALL_PROPERTY);
	}

	// Add a new element returning its index in the list
	public int addCallProperty(org.netbeans.modules.j2ee.weblogic9.dd.web1030.PropertyNamevalueType value) {
		int positionOfNewItem = this.addValue(CALL_PROPERTY, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeCallProperty(org.netbeans.modules.j2ee.weblogic9.dd.web1030.PropertyNamevalueType value) {
		return this.removeValue(CALL_PROPERTY, value);
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public PropertyNamevalueType newPropertyNamevalueType() {
		return new PropertyNamevalueType();
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
		// Validating property portName
		if (getPortName() == null) {
			throw new org.netbeans.modules.schema2beans.ValidateException("getPortName() == null", org.netbeans.modules.schema2beans.ValidateException.FailureType.NULL_VALUE, "portName", this);	// NOI18N
		}
		// has whitespace restriction
		if (restrictionFailure) {
			throw new org.netbeans.modules.schema2beans.ValidateException("getPortName() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "portName", this);	// NOI18N
		}
		// Validating property portNameJ2eeId
		if (getPortNameJ2eeId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getPortNameJ2eeId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "portNameJ2eeId", this);	// NOI18N
			}
		}
		// Validating property stubProperty
		for (int _index = 0; _index < sizeStubProperty(); ++_index) {
			org.netbeans.modules.j2ee.weblogic9.dd.web1030.PropertyNamevalueType element = getStubProperty(_index);
			if (element != null) {
				element.validate();
			}
		}
		// Validating property callProperty
		for (int _index = 0; _index < sizeCallProperty(); ++_index) {
			org.netbeans.modules.j2ee.weblogic9.dd.web1030.PropertyNamevalueType element = getCallProperty(_index);
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
		str.append("PortName");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getPortName();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(PORT_NAME, 0, str, indent);

		str.append(indent);
		str.append("StubProperty["+this.sizeStubProperty()+"]");	// NOI18N
		for(int i=0; i<this.sizeStubProperty(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getStubProperty(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(STUB_PROPERTY, i, str, indent);
		}

		str.append(indent);
		str.append("CallProperty["+this.sizeCallProperty()+"]");	// NOI18N
		for(int i=0; i<this.sizeCallProperty(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getCallProperty(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(CALL_PROPERTY, i, str, indent);
		}

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("PortInfoType\n");	// NOI18N
		this.dump(str, "\n  ");	// NOI18N
		return str.toString();
	}}

// END_NOI18N

