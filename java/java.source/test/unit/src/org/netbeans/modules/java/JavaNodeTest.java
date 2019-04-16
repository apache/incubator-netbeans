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
package org.netbeans.modules.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.junit.NbTestCase;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node.PropertySet;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;

/**
 *
 * @author lahvac
 */
public class JavaNodeTest extends NbTestCase {
    
    public JavaNodeTest(String name) {
        super(name);
    }
    
    public void testSheetToArrayReturnsModifiableArray() {
        Sheet s = new Sheet();
        s.put(new Sheet.Set());
        PropertySet[] properties = s.toArray();
        properties[0] = null;
        assertNull(s.toArray()[0]);
    }
    
}
