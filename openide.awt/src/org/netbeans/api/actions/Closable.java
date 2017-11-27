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
package org.netbeans.api.actions;

/** A context interface representing ability of an object to be closed.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 * @since 7.10
 */
public interface Closable {
    /** Closes the object if it is open.
    * @return <code>true</code> if it was really closed; <code>false</code> if the user cancelled the request
    */
    public boolean close();
}
