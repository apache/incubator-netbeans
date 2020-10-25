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
package org.netbeans.modules.java.source;

import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Name;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class TreeShims {

    public static final String BINDING_PATTERN = "BINDING_PATTERN"; //NOI18N
    public static final String SWITCH_EXPRESSION = "SWITCH_EXPRESSION"; //NOI18N
    public static final String YIELD = "YIELD"; //NOI18N
    public static final String BINDING_VARIABLE = "BINDING_VARIABLE"; //NOI18N
    public static final String RECORD = "RECORD"; //NOI18N

    public static List<? extends ExpressionTree> getExpressions(CaseTree node) {
        try {
            Method getExpressions = CaseTree.class.getDeclaredMethod("getExpressions");
            return (List<? extends ExpressionTree>) getExpressions.invoke(node);
        } catch (NoSuchMethodException ex) {
            return Collections.singletonList(node.getExpression());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }

    public static Tree getBody(CaseTree node) {
        try {
            Method getBody = CaseTree.class.getDeclaredMethod("getBody");
            return (Tree) getBody.invoke(node);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }

    public static boolean isRuleCase(CaseTree node) {
        try {
            Method getCaseKind = CaseTree.class.getDeclaredMethod("getCaseKind");
            return "RULE".equals(String.valueOf(getCaseKind.invoke(node)));
        } catch (NoSuchMethodException ex) {
            return false;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }

    public static Tree getPattern(InstanceOfTree node) {
        try {
            Method getPattern = InstanceOfTree.class.getDeclaredMethod("getPattern");
            return (Tree) getPattern.invoke(node);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }

    public static List<? extends ExpressionTree> getExpressions(Tree node) {
        List<? extends ExpressionTree> exprTrees = new ArrayList<>();

        switch (node.getKind().toString()) {
            case "CASE":
                exprTrees = getExpressions((CaseTree) node);
                break;
            case SWITCH_EXPRESSION: {
                try {
                    Class swExprTreeClass = Class.forName("com.sun.source.tree.SwitchExpressionTree");
                    Method getExpressions = swExprTreeClass.getDeclaredMethod("getExpression");
                    exprTrees = Collections.singletonList((ExpressionTree) getExpressions.invoke(node));
                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw TreeShims.<RuntimeException>throwAny(ex);
                }
                break;
            }
            case "SWITCH":
                exprTrees = Collections.singletonList(((SwitchTree) node).getExpression());
                break;
            default:
                break;
        }
        return exprTrees;
    }

    public static List<? extends CaseTree> getCases(Tree node) {
        List<? extends CaseTree> caseTrees = new ArrayList<>();

        switch (node.getKind().toString()) {
            case "SWITCH":
                caseTrees = ((SwitchTree) node).getCases();
                break;
            case "SWITCH_EXPRESSION": {
                try {
                    Class swExprTreeClass = Class.forName("com.sun.source.tree.SwitchExpressionTree");
                    Method getCases = swExprTreeClass.getDeclaredMethod("getCases");
                    caseTrees = (List<? extends CaseTree>) getCases.invoke(node);
                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw TreeShims.<RuntimeException>throwAny(ex);
                }
            }
        }
        return caseTrees;
    }

    public static ExpressionTree getValue(BreakTree node) {
        try {
            Method getExpression = BreakTree.class.getDeclaredMethod("getValue");
            return (ExpressionTree) getExpression.invoke(node);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }

    public static Name getBinding(Tree node) {
        try {
            Class bpt = Class.forName("com.sun.source.tree.BindingPatternTree");
            Method getBinding = bpt.getDeclaredMethod("getBinding");
            return (Name) getBinding.invoke(node);
        } catch (NoSuchMethodException | ClassNotFoundException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }
    public static List<? extends Tree> getPermits(ClassTree node) {
        List<? extends Tree> perms = null;
        try {
            Class classTree = Class.forName("com.sun.source.tree.ClassTree");
            Method getPerms = classTree.getDeclaredMethod("getPermitsClause");
            perms = (List<? extends Tree>) getPerms.invoke(node);
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            return null;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
        return perms;
    }

    public static List<? extends JCTree> getPermits(JCClassDecl newT) {
        List<JCTree.JCExpression> newPermitings = new ArrayList<>();
        try {
            Class jCClassDecl = Class.forName("com.sun.tools.javac.tree.JCTree$JCClassDecl");
            newPermitings = (com.sun.tools.javac.util.List<JCTree.JCExpression>) jCClassDecl.getDeclaredField("permitting").get(newT);
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            return null;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
        return newPermitings;
    }

    public static ExpressionTree getYieldValue(Tree node) {
        if (!node.getKind().toString().equals(YIELD)) {
            return null;
        }
        try {
            Class yieldTreeClass = Class.forName("com.sun.source.tree.YieldTree"); //NOI18N
            Method getExpression = yieldTreeClass.getDeclaredMethod("getValue");  //NOI18N
            return (ExpressionTree) getExpression.invoke(node);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }

    public static Tree SwitchExpression(TreeMaker make, ExpressionTree selector, List<? extends CaseTree> caseList) throws SecurityException {
        ListBuffer<JCTree.JCCase> cases = new ListBuffer<JCTree.JCCase>();
        for (CaseTree t : caseList) {
            cases.append((JCTree.JCCase) t);
        }
        try {
            Method getMethod = TreeMaker.class.getDeclaredMethod("SwitchExpression", JCTree.JCExpression.class, com.sun.tools.javac.util.List.class);
            return (Tree) getMethod.invoke(make, (JCTree.JCExpression) selector, cases.toList());
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }
  
    public static Tree getBindingPatternType(Tree node) {
        if (!node.getKind().toString().equals(BINDING_PATTERN)) {
            return null;
        }
        try {
            Class bindingPatternTreeClass = Class.forName("com.sun.source.tree.BindingPatternTree"); //NOI18N
            Method getType = bindingPatternTreeClass.getDeclaredMethod("getType");  //NOI18N
            return (Tree) getType.invoke(node);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }
    public static Modifier getSealed() {
        try {
            Class modifierEnum = Class.forName("javax.lang.model.element.Modifier");
            Modifier[] enumConstants = (Modifier[]) modifierEnum.getEnumConstants();
            for (int i = 0; i < enumConstants.length; i++) {
                if (enumConstants[i].name().equals("SEALED")) {
                    return enumConstants[i];
                }
            }
        } catch (ClassNotFoundException ex) {
            return null;
        }
        return null;
    }

    public static Modifier getNonSealed() {
        try {
            Class modifierEnum = Class.forName("javax.lang.model.element.Modifier");
            Modifier[] enumConstants = (Modifier[]) modifierEnum.getEnumConstants();
            for (int i = 0; i < enumConstants.length; i++) {
                if (enumConstants[i].name().equals("NON_SEALED")) {
                    return enumConstants[i];
                }
            }
        } catch (ClassNotFoundException ex) {
            return null;
        }
        return null;
    }

    public static long getFlagExtendedStandardFlags() {
        Class flagsClass = null;
        long flagExtendedStandardFlags = Flags.ExtendedStandardFlags;
        try {
            flagsClass = Class.forName("com.sun.tools.javac.code.Flags");
            flagExtendedStandardFlags = flagsClass.getDeclaredField("ExtendedStandardFlags").getLong(null);
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            return flagExtendedStandardFlags;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
        return flagExtendedStandardFlags;
    }

    public static long getSealedFlag() {
        Class flagsClass = null;
        long sealedFlag = 0;
        try {
            flagsClass = Class.forName("com.sun.tools.javac.code.Flags");
            sealedFlag = flagsClass.getDeclaredField("SEALED").getLong(null);
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            return 0;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
        return sealedFlag;
    }

    public static ClassTree getClassTree(TreeMaker make, Names names, ModifiersTree modifiers, CharSequence simpleName, ListBuffer<JCTree.JCTypeParameter> typarams, Tree extendsClause, ListBuffer<JCExpression> impls, ListBuffer<JCExpression> permits, ListBuffer<JCTree> defs) {
        try {
            Class treeMaker = Class.forName("com.sun.tools.javac.tree.TreeMaker");
            Method allMethods[] = treeMaker.getDeclaredMethods();
            Method classDefs = null;
            for (int i = 0; i < allMethods.length; i++) {
                Method oneMethod = allMethods[i];
                if (oneMethod.getName().equals("ClassDef") && oneMethod.getParameterCount() == 7) {
                    classDefs = oneMethod;
                    break;
                }
            }
            if (classDefs == null) {
                return null;
            }
            ClassTree name = (ClassTree) classDefs.invoke(make, (JCTree.JCModifiers) modifiers,
                    names.fromString(simpleName.toString()),
                    typarams.toList(),
                    (JCTree.JCExpression) extendsClause,
                    impls.toList(),
                    permits.toList(),
                    defs.toList());
            return name;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }

    public static ClassTree getClassTree(TreeMaker make, Names names, long modifiers,
            com.sun.tools.javac.util.List<JCAnnotation> annotations, CharSequence simpleName, ListBuffer<JCTree.JCTypeParameter> typarams, Tree extendsClause, ListBuffer<JCExpression> impls, ListBuffer<JCExpression> permits, ListBuffer<JCTree> defs) {
        try {
            Class treeMaker = Class.forName("com.sun.tools.javac.tree.TreeMaker");
            Method allMethods[] = treeMaker.getDeclaredMethods();
            Method classDefs = null;
            for (int i = 0; i < allMethods.length; i++) {
                Method oneMethod = allMethods[i];
                if (oneMethod.getName().equals("ClassDef") && oneMethod.getParameterCount() == 7) {
                    classDefs = oneMethod;
                    break;
                }
            }
            if (classDefs == null) {
                return null;
            }
            ClassTree name = (ClassTree) classDefs.invoke(make, make.Modifiers(modifiers, annotations),
                    names.fromString(simpleName.toString()),
                    typarams.toList(),
                    (JCTree.JCExpression) extendsClause,
                    impls.toList(),
                    permits.toList(),
                    defs.toList());
            return name;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }
    @SuppressWarnings("unchecked")
    private static <T extends Throwable> RuntimeException throwAny(Throwable t) throws T {
        throw (T) t;
    }
    public static boolean isRecord(Element el) {
        return el != null && "RECORD".equals(el.getKind().name());
    }
    
    public static<N extends Tree> boolean isRecord(final N node) {
        return node != null && TreeShims.RECORD.equals(node.getKind().name());
    }

    public static boolean isRecordComponent(Element el) {
        return el != null && "RECORD_COMPONENT".equals(el.getKind().name());
    }

    public static Element toRecordComponent(Element el) {
        if (el == null ||el.getKind() != ElementKind.FIELD) {
            return el;
        }
        TypeElement owner = (TypeElement) el.getEnclosingElement();
        if (!"RECORD".equals(owner.getKind().name())) {
            return el;
        }
        for (Element encl : owner.getEnclosedElements()) {
            if (isRecordComponent(encl.getKind()) &&
                encl.getSimpleName().equals(el.getSimpleName())) {
                return encl;
            }
        }
        return el;
    }

    public static boolean isRecordComponent(ElementKind kind) {
        return "RECORD_COMPONENT".equals(kind.name());
    }

    public static ElementKind getRecordKind() {
        try {
            return ElementKind.valueOf(RECORD); //NOI18N
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static Tree getTarget(Tree node) {
        if (!node.getKind().name().equals(YIELD)) {
            throw new IllegalStateException();
        }
        try {
            Field target = node.getClass().getField("target");
            return (Tree) target.get(node);
        } catch (NoSuchFieldException ex) {
            return null;
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            throw TreeShims.<RuntimeException>throwAny(ex);
        }
    }
}
