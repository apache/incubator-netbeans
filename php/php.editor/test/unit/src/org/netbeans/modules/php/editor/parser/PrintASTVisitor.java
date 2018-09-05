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
package org.netbeans.modules.php.editor.parser;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.php.editor.lexer.PHPLexerUtils;
import org.netbeans.modules.php.editor.parser.astnodes.*;

/**
 *
 * @author Petr Pisl
 */
public class PrintASTVisitor implements Visitor {

    private StringBuffer buffer;
    private final static String NEW_LINE = "\n";
    private final static String TAB = "    ";
    private int indent;

    private class XMLPrintNode {

        private class GroupItem {
            private final String groupName;
            private final List<ASTNode> group;

            public GroupItem(String groupName, List<ASTNode> group) {
                this.groupName = groupName;
                this.group = group;
            }

            public List<ASTNode> getGroup() {
                return group;
            }

            public String getGroupName() {
                return groupName;
            }
        }

        private ASTNode node;
        private String name;
        private String[] attributes;
        // <name of children group, childrens>
        private List<GroupItem> childrenGroups;

        public XMLPrintNode(ASTNode node, String name){
            this(node, name, new String[]{});
        }

        public XMLPrintNode(ASTNode node, String name, String[] attributes){
            this.node = node;
            this.name = name;
            this.attributes = attributes;
            this.childrenGroups = new ArrayList <GroupItem> ();
        }

        public void addChildrenGroup(String groupName, ASTNode[] groupChildren) {
            ArrayList<ASTNode> nodes = new ArrayList<ASTNode>();
            for (int i = 0; i < groupChildren.length; i++) {
               nodes.add(groupChildren[i]);
            }
            addChildrenGroup(groupName, nodes);
        }

        public void addChildrenGroup(String groupName, List nodes) {
            if (nodes != null) {
                if (this.childrenGroups == null) {
                    this.childrenGroups = new ArrayList<GroupItem>();
                }
                this.childrenGroups.add(new GroupItem(groupName, nodes));
            }
        }

        public void addChildren(List nodes) {
            if (nodes != null)
                addChildrenGroup("", nodes);
        }

        public void addChild(ASTNode node) {
            ArrayList<ASTNode> nodes = new ArrayList<ASTNode>();
            nodes.add(node);
            addChildrenGroup("", nodes);
        }

        public void addChild(String name, ASTNode node) {
            ArrayList<ASTNode> nodes = new ArrayList<ASTNode>();
            nodes.add(node);
            addChildrenGroup(name, nodes);
        }

        public void print(Visitor visitor) {
            addIndentation();
            buffer.append("<").append(name);
            addOffsets(node);
            for (int i = 0; i < attributes.length; i++) {
                String attrName = attributes[i];
                String attrValue = attributes[++i];
                if (attrValue == null) {
                    attrValue = "null";
                }
                buffer.append(" ").append(attrName).append("='").append(attrValue).append("'");
            }
            if (childrenGroups.size() > 0) {
                buffer.append(">").append(NEW_LINE);
                indent++;
                for (GroupItem groupItem : childrenGroups) {
                    if (groupItem.getGroupName().length() > 0) {
                        addIndentation();
                        buffer.append("<").append(groupItem.getGroupName()).append(">").append(NEW_LINE);
                        indent++;
                    }
                    if (groupItem.getGroup() != null) {
                        for (ASTNode aSTNode : groupItem.getGroup()) {
                            if (aSTNode != null) {
                                aSTNode.accept(visitor);
                            }
                        }
                    }
                    if (groupItem.getGroupName().length() > 0) {
                        indent--;
                        addIndentation();
                        buffer.append("</").append(groupItem.getGroupName()).append(">").append(NEW_LINE);
                    }
                }
                indent--;
                addIndentation();
                buffer.append("</").append(name).append(">").append(NEW_LINE);
            }
            else {
                buffer.append("/>").append(NEW_LINE);
            }
        }
    }

    public String printTree(ASTNode node) {
        return printTree(node, 0);
    }

    public String printTree(ASTNode node, int startindent) {
        buffer = new StringBuffer();
        indent = startindent;
        node.accept(this);
        return buffer.toString();
    }

    private void addOffsets(ASTNode node) {
        buffer.append(" start='").append(node.getStartOffset()).append("' end='").append(node.getEndOffset()).append("'");
    }

    protected void addIndentation() {
        for (int i = 0; i < indent; i++) {
            buffer.append(TAB);
        }
    }

    private void addNodeDescription(String name, ASTNode node, boolean newline) {
        addIndentation();
        buffer.append(name);
        addOffsets(node);
        if (newline) {
            buffer.append(NEW_LINE);
        }
    }

    @Override
    public void visit(ArrayAccess node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ArrayAccess",
                new String[]{ "type", node.getDimension().getType().name(),
                    "isDollared", (node.isDollared()?"true":"false")});
        printNode.addChild(node.getDimension());
        printNode.addChildrenGroup("Name", new ASTNode[]{node.getName()});
        printNode.print(this);
    }

    @Override
    public void visit(ArrayCreation node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ArrayCreation", new String[]{"type", node.getType().name()});
        printNode.addChildren(node.getElements());
        printNode.print(this);
    }

    @Override
    public void visit(ArrayElement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ArrayElement");
        printNode.addChild("Key", node.getKey());
        printNode.addChild("Value", node.getValue());
        printNode.print(this);
    }

    @Override
    public void visit(Assignment assignment) {
        XMLPrintNode printNode = new XMLPrintNode(assignment, "Assignment",
                new String[]{"operator", assignment.getOperator().name()});
        printNode.addChild(assignment.getLeftHandSide());
        printNode.addChild(assignment.getRightHandSide());
        printNode.print(this);
    }

    @Override
    public void visit(ASTError astError) {
        (new XMLPrintNode(astError, "ASTError")).print(this);
    }

    @Override
    public void visit(BackTickExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "BackTickExpression");
        printNode.addChildren(node.getExpressions());
        printNode.print(this);
    }

    @Override
    public void visit(Block block) {
        XMLPrintNode printNode = new XMLPrintNode(block, "Block",
                new String[]{"isCurly", (block.isCurly()?"true":"flase")});
        printNode.addChildren(block.getStatements());
        printNode.print(this);
    }

    @Override
    public void visit(BreakStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "BreakStatement");
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(CastExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "CastExpression",
                new String[]{"castingType", node.getCastingType().name()});
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(CatchClause node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "CatchClause");
        printNode.addChildrenGroup("ClassNames", node.getClassNames());
        printNode.addChild(node.getVariable());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(ConstantDeclaration node) {
        XMLPrintNode printNode;
        if (node.isGlobal()) {
            printNode = new XMLPrintNode(node, "GlobalConstantDeclaration");
        } else {
            printNode = new XMLPrintNode(node, "ClassConstantDeclaration", new String[]{"modifier", node.getModifierString() });
        }
        printNode.addChildrenGroup("Names", node.getNames());
        printNode.addChildrenGroup("Initializers", node.getInitializers());
        printNode.print(this);
    }

    @Override
    public void visit(ClassDeclaration classDeclaration) {
        XMLPrintNode printNode = new XMLPrintNode(classDeclaration, "ClassDeclaration",
                new String[]{"modifier", classDeclaration.getModifier().name()});
        printNode.addChildrenGroup("ClassName", new ASTNode[]{classDeclaration.getName()});
        printNode.addChildrenGroup("SuperClassName", new ASTNode[]{classDeclaration.getSuperClass()});
        printNode.addChildrenGroup("Interfaces", classDeclaration.getInterfaes());
        printNode.addChild(classDeclaration.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(ClassInstanceCreation node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ClassInstanceCreation", new String[] {"anonymous", String.valueOf(node.isAnonymous())});
        if (!node.isAnonymous()) {
            printNode.addChild(node.getClassName());
            printNode.addChildrenGroup("Parameters", node.ctorParams());
        } else {
            printNode.addChildrenGroup("Parameters", node.ctorParams());
            printNode.addChild("Superclass", node.getSuperClass());
            printNode.addChildrenGroup("Interfaces", node.getInterfaces());
            printNode.addChild(node.getBody());
        }
        printNode.print(this);
    }

    @Override
    public void visit(ClassName className) {
        XMLPrintNode printNode = new XMLPrintNode(className, "ClassName");
        printNode.addChild(className.getName());
        printNode.print(this);
    }

    @Override
    public void visit(CloneExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "CloneExpression");
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(Comment comment) {
        addNodeDescription("<Comment", comment, false);
	buffer.append(" commentType='").append(comment.getCommentType()).append("'/>").append(NEW_LINE);
    }

    @Override
    public void visit(ConditionalExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ConditionalExpression");
        printNode.addChild("Condition", node.getCondition());
        printNode.addChild("Then", node.getIfTrue());
        printNode.addChild("Else", node.getIfFalse());
        printNode.print(this);
    }

    @Override
    public void visit(ContinueStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ContinueStatement");
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(DeclareStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "DeclareStatement");
        printNode.addChildrenGroup("DirectiveNames", node.getDirectiveNames());
        printNode.addChildrenGroup("DirectiveValues", node.getDirectiveValues());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(DereferencableVariable node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "DereferencableVariable");
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(DoStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "DoStatement");
        printNode.addChild("Condition", node.getCondition());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(EchoStatement echoStatement) {
        XMLPrintNode printNode = new XMLPrintNode(echoStatement, "EchoStatement");
        printNode.addChildren(echoStatement.getExpressions());
        printNode.print(this);
    }

    @Override
    public void visit(EmptyStatement emptyStatement) {
        (new XMLPrintNode(emptyStatement, "EmptyStatement")).print(this);
    }

    @Override
    public void visit(ExpressionArrayAccess node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ExpressionArrayAccess");
        printNode.addChild(node.getExpression());
        printNode.addChild(node.getDimension());
        printNode.print(this);
    }

    @Override
    public void visit(ExpressionStatement expressionStatement) {
        XMLPrintNode printNode = new XMLPrintNode(expressionStatement, "ExpressionStatement");
        printNode.addChild(expressionStatement.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(FieldAccess fieldAccess) {
        XMLPrintNode printNode = new XMLPrintNode(fieldAccess, "FieldAccess");
        printNode.addChild(fieldAccess.getDispatcher());
        printNode.addChild("Field", fieldAccess.getField());
        printNode.print(this);
    }

    @Override
    public void visit(FieldsDeclaration node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "FieldsDeclaration",
                new String[]{"modifier", node.getModifierString() });
        printNode.addChildrenGroup("VariableNames", node.getVariableNames());
        printNode.addChildrenGroup("InitialValues", node.getInitialValues());
        printNode.print(this);
    }

    @Override
    public void visit(FinallyClause node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "FinallyClause");
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(ForEachStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ForEachStatement");
        printNode.addChild("Key", node.getKey());
        printNode.addChild("Expression", node.getExpression());
        printNode.addChild("Statement",node.getStatement());
        printNode.addChild("Value", node.getValue());
        printNode.print(this);
    }

    @Override
    public void visit(FormalParameter node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "FormalParameter",
                new String[]{"isMandatory", (node.isMandatory()?"true":"false"), "isVariadic", (node.isVariadic() ? "true" : "false")});
        printNode.addChild("ParametrType", node.getParameterType());
        printNode.addChild("ParametrName", node.getParameterName());
        printNode.addChild("DefaultValue", node.getDefaultValue());
        printNode.print(this);
    }

    @Override
    public void visit(ForStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ForStatement");
        printNode.addChildrenGroup("Initializers", node.getInitializers());
        printNode.addChildrenGroup("Conditions", node.getConditions());
        printNode.addChildrenGroup("Updaters", node.getUpdaters());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(FunctionDeclaration node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "FunctionDeclaration",
                new String[]{"isReference", (node.isReference()?"true":"false")});
        printNode.addChild(node.getFunctionName());
        printNode.addChildrenGroup("FormalParameters", node.getFormalParameters());
        printNode.addChild(node.getReturnType());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(FunctionInvocation functionInvocation) {
        XMLPrintNode printNode = new XMLPrintNode(functionInvocation, "FunctionInvocation");
        printNode.addChild(functionInvocation.getFunctionName());
        printNode.addChildrenGroup("Parameters", functionInvocation.getParameters());
        printNode.print(this);
    }

    @Override
    public void visit(FunctionName functionName) {
       XMLPrintNode printNode = new XMLPrintNode(functionName, "FucntionName");
       printNode.addChild(functionName.getName());
       printNode.print(this);
    }

    @Override
    public void visit(GlobalStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "GlobalStatement");
        printNode.addChildren(node.getVariables());
        printNode.print(this);
    }

    @Override
    public void visit(NullableType nullableType) {
        XMLPrintNode printNode = new XMLPrintNode(nullableType, "NullableType");
        printNode.addChild(nullableType.getType());
        printNode.print(this);
    }

    @Override
    public void visit(Identifier identifier) {
        (new XMLPrintNode(identifier, "Identifier", new String[]{"name", identifier.getName()})).print(this);
    }

    @Override
    public void visit(NamespaceName namespaceName) {
        XMLPrintNode printNode = new XMLPrintNode(namespaceName, "NamespaceName",
                new String[] {"isCurrent", namespaceName.isCurrent() ? "true" : "false",
        "isGlobal", namespaceName.isGlobal() ? "true" : "false"});
        printNode.addChildren(namespaceName.getSegments());
        printNode.print(this);
    }

    @Override
    public void visit(NamespaceDeclaration declaration) {
        XMLPrintNode printNode = new XMLPrintNode(declaration, "NamespaceDeclaration",
                new String[] {"isBracketed", declaration.isBracketed() ? "true" : "false"});
        printNode.addChild(declaration.getName());
        printNode.addChild(declaration.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(GotoLabel label) {
        XMLPrintNode printNode = new XMLPrintNode(label, "GotoLabel");
        printNode.addChild(label.getName());
        printNode.print(this);

    }

    @Override
    public void visit(GotoStatement statement) {
        XMLPrintNode printNode = new XMLPrintNode(statement, "GotoStatement");
        printNode.addChild(statement.getLabel());
        printNode.print(this);

    }

    @Override
    public void visit(LambdaFunctionDeclaration declaration) {
        XMLPrintNode printNode = new XMLPrintNode(declaration, "LambdaFunctionDeclaration",
                new String[] {"isReference", declaration.isReference() ? "true" : "false"});
        printNode.addChildren(declaration.getFormalParameters());
        printNode.addChildren(declaration.getLexicalVariables());
        printNode.addChild(declaration.getReturnType());
        printNode.addChild(declaration.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(UseStatement statement) {
        XMLPrintNode printNode = new XMLPrintNode(statement, "UseStatement", new String[] {"type", statement.getType().toString()});
        printNode.addChildren(statement.getParts());
        printNode.print(this);
    }

    @Override
    public void visit(SingleUseStatementPart statementPart) {
        final String[] attrs;
        UseStatement.Type type = statementPart.getType();
        if (type != null) {
            attrs = new String[] {"type", type.toString()};
        } else {
            attrs = new String[0];
        }
        XMLPrintNode printNode = new XMLPrintNode(statementPart, "SingleUseStatementPart", attrs);
        printNode.addChild("Name", statementPart.getName());
        printNode.addChild("Alias", statementPart.getAlias());
        printNode.print(this);
    }

    @Override
    public void visit(GroupUseStatementPart statementPart) {
        XMLPrintNode printNode = new XMLPrintNode(statementPart, "GroupUseStatementPart");
        printNode.addChild("BaseNameSpace", statementPart.getBaseNamespaceName());
        printNode.addChildren(statementPart.getItems());
        printNode.print(this);
    }

    @Override
    public void visit(IfStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "IfStatement");
        printNode.addChild("Condition", node.getCondition());
        printNode.addChild("Then", node.getTrueStatement());
        printNode.addChild("Else", node.getFalseStatement());
        printNode.print(this);
    }

    @Override
    public void visit(IgnoreError node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "IgnoreError");
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(Include include) {
        XMLPrintNode printNode = new XMLPrintNode(include, "Include",
                new String [] {"type", include.getIncludeType().name() });
        printNode.addChild(include.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(InfixExpression infixExpression) {
        XMLPrintNode printNode = new XMLPrintNode(infixExpression, "InfixExpression",
                new String[]{"operator", infixExpression.getOperator().name()});
        printNode.addChild(infixExpression.getLeft());
        printNode.addChild(infixExpression.getRight());
        printNode.print(this);
    }

    @Override
    public void visit(InLineHtml inLineHtml) {
        (new XMLPrintNode(inLineHtml, "InLineHtml")).print(this);
    }

    @Override
    public void visit(InstanceOfExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "InstanceOfExpression");
        printNode.addChild(node.getExpression());
        printNode.addChild(node.getClassName());
        printNode.print(this);
    }

    @Override
    public void visit(InterfaceDeclaration node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "InterfaceDeclaration");
        printNode.addChild("Name", node.getName());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(ListVariable node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ListVariable", new String[]{"type", node.getSyntaxType().name()});
        printNode.addChildren(node.getElements());
        printNode.print(this);
    }

    @Override
    public void visit(MethodDeclaration node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "MethodDeclaration",
                new String[]{"modifiers", node.getModifierString()});
        printNode.addChild(node.getFunction());
        printNode.print(this);
    }

    @Override
    public void visit(MethodInvocation node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "MethodInvocation");
        printNode.addChild(node.getDispatcher());
        printNode.addChild("Method", node.getMethod());
        printNode.print(this);
    }

    @Override
    public void visit(ParenthesisExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ParenthesisExpression");
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(PostfixExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "PostfixExpression",
                new String[]{"operator", node.getOperator().name()});
        printNode.addChild(node.getVariable());
        printNode.print(this);
    }

    @Override
    public void visit(PrefixExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "PrefixExpression",
                new String[]{"operator", node.getOperator().name()});
        printNode.addChild(node.getVariable());
        printNode.print(this);
    }

    @Override
    public void visit(Program program) {
        XMLPrintNode printNode = new XMLPrintNode(program, "Program");
        printNode.addChildrenGroup("Comments", program.getComments());
        printNode.addChildrenGroup("Statements", program.getStatements());
        printNode.print(this);
    }

    @Override
    public void visit(Quote quote) {
        XMLPrintNode printNode = new XMLPrintNode(quote, "Quote", new String[]{"type", quote.getQuoteType().name()});
        printNode.addChildrenGroup("Expressions", quote.getExpressions());
        printNode.print(this);
    }

    @Override
    public void visit(Reference reference) {
        XMLPrintNode printNode = new XMLPrintNode(reference, "Reference");
        printNode.addChild(reference.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(ReflectionVariable node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ReflectionVariable");
        printNode.addChild(node.getName());
        printNode.print(this);
    }

    @Override
    public void visit(ReturnStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ReturnStatement");
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(Scalar scalar) {
        (new XMLPrintNode(scalar, "Scalar",
                new String[]{"type", scalar.getScalarType().name(),
                "value", PHPLexerUtils.getXmlStringValue(scalar.getStringValue())})).print(this);
    }

    @Override
    public void visit(SingleFieldDeclaration node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "SingleFieldDeclaration");
        printNode.addChild("Name",node.getName());
        printNode.addChild("Value", node.getValue());
        printNode.print(this);
    }

    @Override
    public void visit(StaticConstantAccess node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "StaticConstantAccess");
        printNode.addChild(node.getDispatcher());
        printNode.addChild("Constant", node.getConstant());
        printNode.addChild("Member", node.getMember());
        printNode.print(this);
    }

    @Override
    public void visit(StaticFieldAccess node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "StaticFieldAccess");
        printNode.addChild(node.getDispatcher());
        printNode.addChild("Field", node.getField());
        printNode.addChild("Member", node.getMember());
        printNode.print(this);
    }

    @Override
    public void visit(StaticMethodInvocation node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "StaticMethodInvocation");
        printNode.addChild(node.getDispatcher());
        printNode.addChild("Member", node.getMember());
        printNode.addChild(node.getMethod());
        printNode.print(this);
    }

    @Override
    public void visit(StaticStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "StaticStatement");
        printNode.addChildrenGroup("Variables", node.getVariables());
        printNode.addChildrenGroup("Expressions", node.getExpressions());
        printNode.print(this);
    }

    @Override
    public void visit(SwitchCase node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "SwitchCase",
                new String[]{"default", (node.isDefault()?"true":"false")});
        printNode.addChild(node.getValue());
        printNode.addChildren(node.getActions());
        printNode.print(this);
    }

    @Override
    public void visit(SwitchStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "SwitchStatement");
        printNode.addChild(node.getExpression());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(ThrowStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ThrowStatement");
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(TryStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "TryStatement");
        printNode.addChildrenGroup("CatchClauses", node.getCatchClauses());
        printNode.addChild(node.getFinallyClause());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(UnaryOperation node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "UnaryOperation",
                new String[]{"operator", node.getOperator().name()});
        printNode.addChild(node.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(Variable variable) {
        XMLPrintNode printNode = new XMLPrintNode(variable, "Variable",
                new String[]{"isDollared", (variable.isDollared()?"true":"false")});

        printNode.addChild(variable.getName());
        printNode.print(this);
    }

    @Override
    public void visit(Variadic variadic) {
        XMLPrintNode printNode = new XMLPrintNode(variadic, "Variadic");
        printNode.addChild(variadic.getExpression());
        printNode.print(this);
    }

    @Override
    public void visit(WhileStatement node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "WhileStatement");
        printNode.addChild("Condition", node.getCondition());
        printNode.addChild(node.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(ASTNode node) {
        // this node shouldn't appear in the result.
        (new XMLPrintNode(node, "ASTNode")).print(this);
    }

    @Override
    public void visit(PHPDocBlock phpDocBlock) {
        XMLPrintNode printNode = new XMLPrintNode(phpDocBlock, "PHPDocBlock");
        printNode.addChildrenGroup("Tags", phpDocBlock.getTags());
        printNode.print(this);
    }

    @Override
    public void visit(PHPDocTag phpDocTag) {
        XMLPrintNode printNode = new XMLPrintNode(phpDocTag, "PHPDocTag",
                new String[] {"kind", phpDocTag.getKind().getName()});
        printNode.print(this);
    }

    @Override
    public void visit(PHPDocTypeTag node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "PHPDocTypeTag",
                new String[] {"kind", node.getKind().getName()});
        printNode.addChildrenGroup("Types", node.getTypes());
        printNode.print(this);
    }

    @Override
    public void visit(PHPDocVarTypeTag node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "PHPDocVarTypeTag",
                new String[] {"kind", node.getKind().getName()});
        printNode.addChild("Variable", node.getVariable());
        printNode.addChildrenGroup("Types", node.getTypes());
        printNode.print(this);
    }


    @Override
    public void visit(PHPDocMethodTag node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "PHPDocMethodTag",
                new String[] {"kind", node.getKind().getName()});
        printNode.addChild("Name", node.getMethodName());
        printNode.addChildrenGroup("Return Types", node.getTypes());
        printNode.addChildrenGroup("Parameters", node.getParameters());
        printNode.print(this);
    }

    @Override
    public void visit(PHPDocStaticAccessType node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "PHPDocStaticAccessType",
                new String[] {"value", node.getValue()});
        printNode.addChild(node.getClassName());
        printNode.addChild(node.getConstant());
        printNode.print(this);
    }

    @Override
    public void visit(PHPDocNode phpDocNode) {
        XMLPrintNode printNode = new XMLPrintNode(phpDocNode, "PHPDocNode",
                new String[] {"value", phpDocNode.getValue()});
        printNode.print(this);
    }

    @Override
    public void visit(PHPDocTypeNode phpDocTypeNode) {
        XMLPrintNode printNode = new XMLPrintNode(phpDocTypeNode, "PHPDocTypeNode",
                new String[] {"value", phpDocTypeNode.getValue(), "isArray", phpDocTypeNode.isArray() ? "true" : "false"});
        printNode.print(this);
    }

    @Override
    public void visit(PHPVarComment node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "PHPVarComment");
        printNode.addChild("Variable", node.getVariable());
        printNode.print(this);
    }

    @Override
    public void visit(TraitDeclaration traitDeclaration) {
        XMLPrintNode printNode = new XMLPrintNode(traitDeclaration, "TraitDeclaration");
        printNode.addChildrenGroup("TraitName", new ASTNode[]{traitDeclaration.getName()});
        printNode.addChild(traitDeclaration.getBody());
        printNode.print(this);
    }

    @Override
    public void visit(TraitMethodAliasDeclaration traitsAliasStatement) {
        XMLPrintNode printNode = new XMLPrintNode(traitsAliasStatement, "TraitMethodAliasDeclaration");
        printNode.addChild("NewMethodName", traitsAliasStatement.getNewMethodName());
        printNode.addChild("OldMethodName", traitsAliasStatement.getOldMethodName());
        printNode.addChild("TraitName", traitsAliasStatement.getTraitName());
        printNode.print(this);
    }

    @Override
    public void visit(TraitConflictResolutionDeclaration traitsInsteadofStatement) {
        XMLPrintNode printNode = new XMLPrintNode(traitsInsteadofStatement, "TraitConflictResolutionDeclaration");
        printNode.addChild("MethodName", traitsInsteadofStatement.getMethodName());
        printNode.addChild("PreferredTraitName", traitsInsteadofStatement.getPreferredTraitName());
        printNode.addChildrenGroup("SuppressedTraitNames", traitsInsteadofStatement.getSuppressedTraitNames());
        printNode.print(this);
    }

    @Override
    public void visit(UseTraitStatement useTraitsStatement) {
        XMLPrintNode printNode = new XMLPrintNode(useTraitsStatement, "UseTraitStatement");
        printNode.addChildrenGroup("Parts", useTraitsStatement.getParts());
        printNode.addChildrenGroup("Body", new ASTNode[]{useTraitsStatement.getBody()});
        printNode.print(this);
    }

    @Override
    public void visit(UseTraitStatementPart useTraitStatementPart) {
        XMLPrintNode printNode = new XMLPrintNode(useTraitStatementPart, "UseTraitStatementPart");
        printNode.addChild("Name", useTraitStatementPart.getName());
        printNode.print(this);
    }

    @Override
    public void visit(YieldExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "YieldExpression");
        printNode.addChild(node.getKey());
        printNode.addChild(node.getValue());
        printNode.print(this);
    }

    @Override
    public void visit(YieldFromExpression node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "YieldFromExpression");
        printNode.addChild(node.getExpr());
        printNode.print(this);
    }

    @Override
    public void visit(AnonymousObjectVariable node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "AnonymousObjectVariable");
        printNode.addChild(node.getName());
        printNode.print(this);
    }

    @Override
    public void visit(DereferencedArrayAccess node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "DereferencedArrayAccess");
        printNode.addChild(node.getDimension());
        printNode.addChild(node.getDispatcher());
        printNode.print(this);
    }

    @Override
    public void visit(ArrayDimension node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "ArrayDimension");
        printNode.addChild("Index", node.getIndex());
        printNode.print(this);
    }

    @Override
    public void visit(HaltCompiler node) {
        XMLPrintNode printNode = new XMLPrintNode(node, "HaltCompiler");
        printNode.print(this);
    }
}
