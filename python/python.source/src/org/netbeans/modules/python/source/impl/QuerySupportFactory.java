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
package org.netbeans.modules.python.source.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.netbeans.modules.python.source.PythonIndexer;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

public final class QuerySupportFactory {

    
    public static QuerySupport getQuerySupport(final Collection<FileObject> roots) {
        try {
            return QuerySupport.forRoots(PythonIndexer.NAME,
                    PythonIndexer.VERSION,
                    roots.toArray(new FileObject[roots.size()]));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
    
    public static QuerySupport getQuerySupport(final FileObject source) {
        return getQuerySupport(QuerySupport.findRoots(source,
                null,
                null,
                Collections.<String>emptySet()));
    }

    private QuerySupportFactory() {
    }
    
}
