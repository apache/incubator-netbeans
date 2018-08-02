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
/*
 * TransportConfig.java
 *
 * Created on November 18, 2004, 10:02 AM
 */

package org.netbeans.modules.j2ee.sun.dd.api.ejb;

/**
 *
 * @author  Nitya Doraisamy
 */
public interface TransportConfig extends org.netbeans.modules.j2ee.sun.dd.api.CommonDDBean {

    public static final String INTEGRITY = "Integrity";	// NOI18N
    public static final String CONFIDENTIALITY = "Confidentiality";	// NOI18N
    public static final String ESTABLISH_TRUST_IN_TARGET = "EstablishTrustInTarget";	// NOI18N
    public static final String ESTABLISH_TRUST_IN_CLIENT = "EstablishTrustInClient";	// NOI18N
        
    /** Setter for integrity property
     * @param value property value
     */
    public void setIntegrity(java.lang.String value);
    /** Getter for integrity property.
     * @return property value
     */
    public java.lang.String getIntegrity();
    /** Setter for confidentiality property
     * @param value property value
     */
    public void setConfidentiality(java.lang.String value);
    /** Getter for confidentiality property.
     * @return property value
     */
    public java.lang.String getConfidentiality();
    /** Setter for establish-trust-in-target property
     * @param value property value
     */
    public void setEstablishTrustInTarget(java.lang.String value);
    /** Getter for establish-trust-in-target property.
     * @return property value
     */
    public java.lang.String getEstablishTrustInTarget();
    /** Setter for establish-trust-in-client property
     * @param value property value
     */
    public void setEstablishTrustInClient(java.lang.String value);
    /** Getter for establish-trust-in-client property.
     * @return property value
     */
    public java.lang.String getEstablishTrustInClient();
    
}
