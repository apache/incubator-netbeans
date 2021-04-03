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

package org.netbeans.modules.db.sql.editor.completion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.netbeans.api.db.explorer.JDBCDriver;
import org.netbeans.api.db.explorer.JDBCDriverManager;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.db.api.sql.execute.SQLExecution;
import org.netbeans.modules.db.sql.editor.ui.actions.SQLExecutionBaseAction;
import org.netbeans.modules.db.sql.lexer.SQLTokenId;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.awt.StatusDisplayer;
import org.openide.text.Line;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author Andrei Badea
 */
public class SQLCompletionProvider implements CompletionProvider {
    private static final String SQL_CONNECTION_HINT_ID = "sql_connection_hint";
    private static JTextComponent component = null;
    private static DatabaseConnection dbconn = null;

    @Override
    public CompletionTask createTask(int queryType, JTextComponent component) {
        if (queryType == CompletionProvider.COMPLETION_QUERY_TYPE || 
                queryType == CompletionProvider.COMPLETION_ALL_QUERY_TYPE) {
            
            /* to support DB related completion tasks (i.e. auto populating table 
            names or db columns for given schema) check for connection */
            DatabaseConnection dbconn = findDBConn(component);
            // No database connection set or active
            if (SQLCompletionProvider.dbconn == null) {
                createSuggestions(component,dbconn);
            }
            
            return new AsyncCompletionTask(new SQLCompletionQuery(dbconn), component);
        }
        
        // not a completion query type so return nothing
        return null;
    }

    /**
     * getAutoQueryTypes is invoked to check whether a popup with suggestions
     * should be shown without the user explicitly asking for it.
     * 
     * If either #getAutoQueryTypes return a non-zero value or the user
     * explicitly asks for completion, #createTask is invoked with the
     * requested type. In case of SQL see
     * org.netbeans.modules.db.sql.editor.completion.SQLCompletionQuery.
     * 
     * @param component
     * @param typedText
     * @return 
     */
    public int getAutoQueryTypes(JTextComponent component, String typedText) {
        // XXX: Check if "enable/disable" autocomplete is setting.  See NETBEANS-188

        // If "." has not been typed then acceptable to start checking for options.
        if (!".".equals(typedText)) { // NOI18N
            return 0;
        }
        // check typed text if dot is present at the selected offset
        if (!isDotAtOffset(component, component.getSelectionStart() - 1)) {
            return 0;
        }
        
        // check if there is a DB connection
        DatabaseConnection dbconn = findDBConn(component);
        if (dbconn == null) {
            String message = NbBundle.getMessage(SQLCompletionProvider.class, "MSG_NoDatabaseConnection");
            StatusDisplayer.getDefault().setStatusText(message);
            createSuggestions(component, dbconn);
            SQLExecutionBaseAction.notifyNoDatabaseConnection();
        }

        // check if DB connection is active
        if (dbconn != null && dbconn.getJDBCConnection() == null) {
            String message = NbBundle.getMessage(SQLCompletionProvider.class, "MSG_NotConnected");
            StatusDisplayer.getDefault().setStatusText(message);
            SQLExecutionBaseAction.notifyNoDatabaseConnection();
            // XXX: Maybe add content specific "fixs"
        }
        return COMPLETION_QUERY_TYPE;
    }

    private void createSuggestions(JTextComponent component, DatabaseConnection dbconn) {
        // setup hints and fixes for possible resolution to connection issue
        List<Fix> fixes = new ArrayList<>(Collections.emptyList());
        List<ErrorDescription> warnings = new ArrayList<>(Collections.emptyList());
        
        // determine the selected line
        String selection = component.getSelectedText();
        Document doc = component.getDocument();

        // for selected lines with content
        if (selection != null && selection.length() > 0) {
            // use NbEditorUtilities.getLine() to get line from current document and offset,
            int lineOffset = component.getSelectionStart() - 1;
            int endOffset = lineOffset + selection.length();
            
            Line currentLine = NbEditorUtilities.getLine(doc, lineOffset, false);
            
            // setup sql hints/fixes
            try {
                // identify possible fixes
                
                // hint to create a new connection
                fixes.add(new SqlNewConnectionFix());  
                // hint to add a new driver for connection
                fixes.add(new SqlNewDriveFix());
                // hint to correction connection - show dialog to do so
                fixes.add(new SqlConnectFix(component));
                                
                // setup ErrorDescription and add to warnings
                warnings.add(
                        ErrorDescriptionFactory.createErrorDescription(
                                Severity.WARNING,
                                "DB Connection Issue",
                                fixes,
                                doc,
                                doc.createPosition(lineOffset),
                                doc.createPosition(endOffset)
                        )
                );
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            // for non-selected lines
                        
            // setup sql hints/fixes
            try {
                // identify possible fixes
                
                // hint to create a new connection
                fixes.add(new SqlNewConnectionFix());  
                // hint to add a new driver for connection
                fixes.add(new SqlNewDriveFix());
                // hint to correction connection - show dialog to do so
                fixes.add(new SqlConnectFix(component));

                // no line is selected so add to initial position
                // setup ErrorDescription and add to warnings
                warnings.add(
                        ErrorDescriptionFactory.createErrorDescription(
                                Severity.WARNING,
                                "DB Connection Issue",
                                fixes,
                                doc,
                                doc.createPosition(0),
                                doc.createPosition(0)
                        )
                );
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
            HintsController.setErrors(doc, SQL_CONNECTION_HINT_ID, warnings);
        }
    }

    private static DatabaseConnection findDBConn(JTextComponent component) {
        Lookup context = findContext(component);
        if (context == null) {
            return null;
        }
        SQLExecution sqlExecution = context.lookup(SQLExecution.class);
        if (sqlExecution == null) {
            return null;
        }
        return sqlExecution.getDatabaseConnection();
    }

    private static Lookup findContext(JTextComponent component) {
        for (java.awt.Component comp = component; comp != null; comp = comp.getParent()) {
            if (comp instanceof Lookup.Provider) {
                Lookup lookup = ((Lookup.Provider)comp).getLookup ();
                if (lookup != null) {
                    return lookup;
                }
            }
        }
        return null;
    }

    /**
     * For given component object's doc model, determine if given offset 
     * has applicable dot delimiter token
     * @param component
     * @param offset
     * @return 
     */
    private static boolean isDotAtOffset(JTextComponent component, final int offset) {
        final Document doc = component.getDocument();
        final boolean[] result = { false };
        // trigger internal model rendering based on SQL tokens 
        doc.render(() -> {
            TokenSequence<SQLTokenId> seq = getSQLTokenSequence(doc);
            // no SQL tokens found; done checking
            if (seq == null) {
                return;
            }
            // in the sequence move to provided offset location
            seq.move(offset);
            // no next or previous so done looking for tokens; done checking
            if (!seq.moveNext() && !seq.movePrevious()) {
                return;
            }
            // if offset not consistent with sequence offset done checking
            if (seq.offset() != offset) {
                return;
            }
            // confirm token ID is applicable SQL "dot" token
            result[0] = (seq.token().id() == SQLTokenId.DOT);
        });
        return result[0];
    }

    private static TokenSequence<SQLTokenId> getSQLTokenSequence(Document doc) {
        // Hack until the SQL editor is entirely ported to the Lexer API.
        if (doc.getProperty(Language.class) == null) {
            doc.putProperty(Language.class, SQLTokenId.language());
        }
        TokenHierarchy<?> hierarchy = TokenHierarchy.get(doc);
        return hierarchy.tokenSequence(SQLTokenId.language());
    }
        
    private static class SqlNewConnectionFix implements Fix {
        @Override
        public String getText() {
            return "Create New Database connection."; 
        }

        @Override
        public ChangeInfo implement() throws Exception {
  
            // select given connection
            ConnectionManager cm = ConnectionManager.getDefault();
            DatabaseConnection dbconn = findDBConn(component);
                
            JDBCDriver driver = null;
            if (dbconn == null) {
                // no connection, have to establish driver and related details
                cm.showAddConnectionDialog(null);
            }
            ChangeInfo results = new ChangeInfo();
            
            return results;
        }

        private void setupDriver() {
            JDBCDriverManager dm = JDBCDriverManager.getDefault();
            JDBCDriver[] drivers = dm.getDrivers();
            
            if (drivers == null || drivers.length ==0) {
                dm.showAddDriverDialog();
            }
        }

        public SqlNewConnectionFix() {
        }
        public SqlNewConnectionFix(DatabaseConnection conn) {
        }
    }
    
    /**
     * This case handles when driver and connections are available but not connected
     */
    private static class SqlConnectFix implements Fix {

        @Override
        public String getText() {
            return "Connect to database"; 
        }

        @Override
        public ChangeInfo implement() throws Exception {
            
            SQLExecutionBaseAction.notifyNoDatabaseConnection();
            ChangeInfo results = new ChangeInfo();
            return results;
        }

         public SqlConnectFix(JTextComponent applicableComponent) {
             component = applicableComponent;
        }
        public SqlConnectFix() {
        }
    }
    
    /**
     * This case handles when driver and connections are available but not connected
     */
    private static class SqlNewDriveFix implements Fix {

        @Override
        public String getText() {
            return "Add a New Database Driver"; 
        }

        @Override
        public ChangeInfo implement() throws Exception {
            ConnectionManager cm = ConnectionManager.getDefault();
            JDBCDriverManager dm = JDBCDriverManager.getDefault();
            dm.showAddDriverDialog();
                        
            ChangeInfo results = new ChangeInfo();
            return results;
        }
        
        public SqlNewDriveFix() {
        }
    }
}


