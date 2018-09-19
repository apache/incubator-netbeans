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
package cmp;


/**
 * This is the local-home interface for Customer enterprise bean.
 */
public interface CustomerLocalHome extends javax.ejb.EJBLocalHome {


    cmp.CustomerLocal findByPrimaryKey(java.lang.Long key)  throws javax.ejb.FinderException;

    public cmp.CustomerLocal create(java.lang.Long id, java.lang.String lastName, java.lang.String firstName) throws javax.ejb.CreateException;

    java.util.Collection findById(java.lang.Long id) throws javax.ejb.FinderException;

    java.util.Collection findByLastName(java.lang.String lastName) throws javax.ejb.FinderException;

    java.util.Collection findByFirstName(java.lang.String firstName) throws javax.ejb.FinderException;


}
