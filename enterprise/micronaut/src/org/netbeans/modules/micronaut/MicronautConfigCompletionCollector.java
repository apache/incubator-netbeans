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
package org.netbeans.modules.micronaut;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import javax.swing.text.Document;
import org.netbeans.api.editor.document.EditorDocumentUtils;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.lib.editor.util.ArrayUtilities;
import org.netbeans.spi.editor.completion.CompletionCollector;
import org.openide.filesystems.FileObject;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;

/**
 *
 * @author Dusan Balek
 */
@MimeRegistration(mimeType = "text/x-yaml", service = CompletionCollector.class)
public class MicronautConfigCompletionCollector implements CompletionCollector {

    @Override
    public boolean collectCompletions(Document doc, int offset, Context context, Consumer<Completion> consumer) {
        FileObject fo = EditorDocumentUtils.getFileObject(doc);
        if (fo != null && "application.yml".equalsIgnoreCase(fo.getNameExt())) {
            Project project = FileOwnerQuery.getOwner(fo);
            if (project != null) {
                MicronautConfigProperties configProperties = project.getLookup().lookup(MicronautConfigProperties.class);
                if (configProperties != null) {
                    new MicronautConfigCompletionTask().query(doc, offset, configProperties, new MicronautConfigCompletionTask.ItemFactory<CompletionCollector.Completion>() {
                        @Override
                        public CompletionCollector.Completion createTopLevelPropertyItem(String propName, int offset, int baseIndent, int indentLevelSize) {
                            StringBuilder insertText = new StringBuilder();
                            TextFormat insertTextFormat = TextFormat.PlainText;
                            if ("*".equals(propName)) {
                                insertText.append("$1:\n");
                                ArrayUtilities.appendSpaces(insertText, baseIndent + indentLevelSize);
                                insertTextFormat = TextFormat.Snippet;
                            } else {
                                insertText.append(propName).append(":\n");
                                ArrayUtilities.appendSpaces(insertText, indentLevelSize);
                            }
                            return CompletionCollector.Completion.newBuilder(propName).kind(Kind.Property).sortText(String.format("%4d%s", 10, propName))
                                    .insertText(insertText.toString()).insertTextFormat(insertTextFormat).build();
                        }

                        @Override
                        public CompletionCollector.Completion createPropertyItem(ConfigurationMetadataProperty property, int offset, int baseIndent, int indentLevelSize, int idx) {
                            String[] parts = property.getId().substring(idx).split("\\.");
                            StringBuilder insertText = new StringBuilder();
                            int num = 1;
                            int indent = 0;
                            TextFormat insertTextFormat = TextFormat.PlainText;
                            for (int i = 0; i < parts.length; i++) {
                                String part = parts[i];
                                if ("*".equals(part)) {
                                    insertText.append("$" + num++);
                                    insertTextFormat = TextFormat.Snippet;
                                } else {
                                    insertText.append(part);
                                }
                                if (i < parts.length - 1) {
                                    insertText.append(":\n");
                                    ArrayUtilities.appendSpaces(insertText, (indent = indent + indentLevelSize));
                                } else {
                                    insertText.append(": ");
                                }
                            }
                            Completion.Builder builder = CompletionCollector.Completion.newBuilder(property.getId()).kind(Kind.Property)
                                    .sortText(String.format("%4d%s", property.isDeprecated() ? 30 : 20, property.getId())).insertText(insertText.toString())
                                    .insertTextFormat(insertTextFormat).documentation(CompletableFuture.completedFuture(new MicronautConfigDocumentation(property).getText()));
                            if (property.isDeprecated()) {
                                builder.tags(Collections.singletonList(Tag.Deprecated));
                            }
                            return builder.build();
                        }
                    }).stream().forEach(consumer);
                }
            }
        }
        return true;
    }
}
