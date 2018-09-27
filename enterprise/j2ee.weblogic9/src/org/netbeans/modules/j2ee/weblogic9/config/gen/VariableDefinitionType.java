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
 *	This generated bean class VariableDefinitionType matches the schema element 'variable-definitionType'.
 *  The root bean class is DeploymentPlan
 *
 *	Generated on Tue Jul 25 03:27:07 PDT 2017
 * @Generated
 */

package org.netbeans.modules.j2ee.weblogic9.config.gen;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class VariableDefinitionType extends org.netbeans.modules.schema2beans.BaseBean
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);
	;	// NOI18N

	static public final String VARIABLE = "Variable";	// NOI18N

	public VariableDefinitionType() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public VariableDefinitionType(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(1);
		this.createProperty("variable", 	// NOI18N
			VARIABLE, 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			VariableType.class);
		this.initialize(options);
	}

	// Setting the default values of the properties
	void initialize(int options) {

	}

	// This attribute is an array, possibly empty
	public void setVariable(int index, VariableType value) {
		this.setValue(VARIABLE, index, value);
	}

	//
	public VariableType getVariable(int index) {
		return (VariableType)this.getValue(VARIABLE, index);
	}

	// Return the number of properties
	public int sizeVariable() {
		return this.size(VARIABLE);
	}

	// This attribute is an array, possibly empty
	public void setVariable(VariableType[] value) {
		this.setValue(VARIABLE, value);
	}

	//
	public VariableType[] getVariable() {
		return (VariableType[])this.getValues(VARIABLE);
	}

	// Add a new element returning its index in the list
	public int addVariable(org.netbeans.modules.j2ee.weblogic9.config.gen.VariableType value) {
		int positionOfNewItem = this.addValue(VARIABLE, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeVariable(org.netbeans.modules.j2ee.weblogic9.config.gen.VariableType value) {
		return this.removeValue(VARIABLE, value);
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public VariableType newVariableType() {
		return new VariableType();
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
		// Validating property variable
		for (int _index = 0; _index < sizeVariable(); ++_index) {
			org.netbeans.modules.j2ee.weblogic9.config.gen.VariableType element = getVariable(_index);
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
		str.append("Variable["+this.sizeVariable()+"]");	// NOI18N
		for(int i=0; i<this.sizeVariable(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getVariable(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(VARIABLE, i, str, indent);
		}

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("VariableDefinitionType\n");	// NOI18N
		this.dump(str, "\n  ");	// NOI18N
		return str.toString();
	}}

// END_NOI18N

