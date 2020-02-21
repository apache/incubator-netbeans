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

package org.netbeans.modules.cnd.completion;

import org.netbeans.modules.cnd.completion.cplusplus.ext.CompletionBaseTestCase;

/**
 *
 *
 */
public class TooltipTestCase extends CompletionBaseTestCase {
    public TooltipTestCase(String testName) {
        super(testName, false);
    }

    public void testTooltip() throws Exception {
        super.performTest("file.cpp", 13, 17);
    }

    public void testTooltip2() throws Exception {
        super.performTest("file.cpp", 14, 20);
    }
    
    @Override
    protected void performTest(String source, int lineIndex, int colIndex, String textToInsert, int offsetAfterInsertion, String goldenFileName, String toPerformItemRE, String goldenFileName2) throws Exception {
        super.performTest(source, lineIndex, colIndex, textToInsert, offsetAfterInsertion, goldenFileName, toPerformItemRE, goldenFileName2, true);
    }
    
}
