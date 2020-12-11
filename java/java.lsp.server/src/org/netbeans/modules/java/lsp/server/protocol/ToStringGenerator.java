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
package org.netbeans.modules.java.lsp.server.protocol;

import com.google.gson.Gson;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.TreePath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.GeneratorUtilities;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.TreeUtilities;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 *
 * @author Dusan Balek
 */
public final class ToStringGenerator extends CodeGenerator {

    public static final String GENERATE_TO_STRING =  "java.generate.toString";

    private final Set<String> commands = Collections.singleton(GENERATE_TO_STRING);
    private final Gson gson = new Gson();

    ToStringGenerator() {
    }

    @Override
    @NbBundle.Messages({
        "DN_GenerateToString=Generate toString()...",
    })
    public List<CodeAction> getCodeActions(CompilationInfo info, CodeActionParams params) {
        List<String> only = params.getContext().getOnly();
        if (only == null || !only.contains(CodeActionKind.Source)) {
            return Collections.emptyList();
        }
        int offset = getOffset(info, params.getRange().getStart());
        TreePath tp = info.getTreeUtilities().pathFor(offset);
        tp = info.getTreeUtilities().getPathElementOfKind(TreeUtilities.CLASS_TREE_KINDS, tp);
        if (tp == null) {
            return Collections.emptyList();
        }
        TypeElement type = (TypeElement) info.getTrees().getElement(tp);
        if (type == null || !type.getKind().isClass()) {
            return Collections.emptyList();
        }
        List<QuickPickItem> fields = new ArrayList<>();
        for (Element element : type.getEnclosedElements()) {
            switch (element.getKind()) {
                case METHOD:
                    if (element.getSimpleName().contentEquals("toString") && ((ExecutableElement) element).getParameters().isEmpty()) { //NOI18N
                        return Collections.emptyList();
                    }
                    break;
                case FIELD:
                    if (!ERROR.contentEquals(element.getSimpleName()) && !element.getModifiers().contains(Modifier.STATIC)) {
                        QuickPickItem item = new QuickPickItem(createLabel(info, (VariableElement)element));
                        item.setUserData(new ElementData(element));
                        fields.add(item);
                    }
                    break;
            }
        }
        String uri = toUri(info.getFileObject());
        return Collections.singletonList(createCodeAction(Bundle.DN_GenerateToString(), CODE_GENERATOR_KIND, GENERATE_TO_STRING, uri, offset, fields));
    }

    @Override
    public Set<String> getCommands() {
        return commands;
    }

    @Override
    @NbBundle.Messages({
        "DN_SelectToString=Select fields to be included in toString()",
    })
    public CompletableFuture<Object> processCommand(NbCodeLanguageClient client, String command, List<Object> arguments) {
        if (arguments.size() > 2) {
            String uri = gson.fromJson(gson.toJson(arguments.get(0)), String.class);
            int offset = gson.fromJson(gson.toJson(arguments.get(1)), Integer.class);
            List<QuickPickItem> fields = Arrays.asList(gson.fromJson(gson.toJson(arguments.get(2)), QuickPickItem[].class));
            if (fields.isEmpty()) {
                generate(client, uri, offset, fields);
            } else {
                client.showQuickPick(new ShowQuickPickParams(Bundle.DN_SelectToString(), true, fields)).thenAccept(selected -> {
                    if (selected != null) {
                        generate(client, uri, offset, selected);
                    }
                });
            }
        }
        return CompletableFuture.completedFuture(true);
    }

    private void generate(NbCodeLanguageClient client, String uri, int offset, List<QuickPickItem> fields) {
        try {
            FileObject file = fromUri(uri);
            JavaSource js = JavaSource.forFileObject(file);
            List<TextEdit> edits = TextDocumentServiceImpl.modify2TextEdits(js, wc -> {
                wc.toPhase(JavaSource.Phase.RESOLVED);
                TreePath tp = wc.getTreeUtilities().pathFor(offset);
                tp = wc.getTreeUtilities().getPathElementOfKind(TreeUtilities.CLASS_TREE_KINDS, tp);
                if (tp != null) {
                    ClassTree cls = (ClassTree) tp.getLeaf();
                    List<VariableElement> selectedFields = fields.stream().map(item -> {
                        ElementData data = gson.fromJson(gson.toJson(item.getUserData()), ElementData.class);
                        return (VariableElement)data.resolve(wc);
                    }).collect(Collectors.toList());
                    MethodTree method = org.netbeans.modules.java.editor.codegen.ToStringGenerator.createToStringMethod(wc, selectedFields, cls.getSimpleName().toString(), true);
                    wc.rewrite(cls, GeneratorUtilities.get(wc).insertClassMember(cls, method));
                }
            });
            client.applyEdit(new ApplyWorkspaceEditParams(new WorkspaceEdit(Collections.singletonMap(uri, edits))));
        } catch (IOException | IllegalArgumentException ex) {
            client.logMessage(new MessageParams(MessageType.Error, ex.getLocalizedMessage()));
        }
    }
}
