package org.netbeans.modules.java.completion;
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


import java.util.ArrayList;
import java.util.List;
import javax.lang.model.SourceVersion;
import javax.swing.event.ChangeListener;
import org.netbeans.junit.NbTestSuite;
import org.netbeans.modules.java.source.parsing.JavacParser;
import org.netbeans.spi.java.queries.CompilerOptionsQueryImplementation;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;
/**
 *
 * @author arusinha
 */
public class JavaCompletionTask117FeaturesTest extends CompletionTestBase {

    private static String SOURCE_LEVEL = "17"; //NOI18N

    public JavaCompletionTask117FeaturesTest(String testName) {
        super(testName);
    }

    public static NbTestSuite suite() {
        NbTestSuite suite = new NbTestSuite();
        try {
            SourceVersion.valueOf("RELEASE_17"); //NOI18N
            suite.addTestSuite(JavaCompletionTask117FeaturesTest.class);
        } catch (IllegalArgumentException ex) {
            //OK, no RELEASE_13, skip tests
            suite.addTest(new JavaCompletionTask117FeaturesTest("noop")); //NOI18N
        }
        return suite;
    }

    
    public void testVariableNameSuggestion() throws Exception {
        EXTRA_OPTIONS.add("--enable-preview");
        performTest("SwitchPatternMatching", 975, null, "switchPatternMatching.pass", getLatestSource());
    } 
    
    public void noop() {
    }

    static {
        JavacParser.DISABLE_SOURCE_LEVEL_DOWNGRADE = true;
    }
    
    private String getLatestSource(){
        return SourceVersion.latest().name().substring(SourceVersion.latest().name().indexOf("_")+1);
    }
    private static final List<String> EXTRA_OPTIONS = new ArrayList<>();
    @ServiceProvider(service = CompilerOptionsQueryImplementation.class, position = 100)
    public static class TestCompilerOptionsQueryImplementation implements CompilerOptionsQueryImplementation {

        @Override
        public CompilerOptionsQueryImplementation.Result getOptions(FileObject file) {
            return new CompilerOptionsQueryImplementation.Result() {
                @Override
                public List<? extends String> getArguments() {
                    return EXTRA_OPTIONS;
                }

                @Override
                public void addChangeListener(ChangeListener listener) {
                }

                @Override
                public void removeChangeListener(ChangeListener listener) {
                }
            };
        }

    }
}
