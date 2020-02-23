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

package org.netbeans.modules.db.explorer.node;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.db.explorer.node.BaseNode;
import org.netbeans.api.db.explorer.node.ChildNodeFactory;
import org.netbeans.api.db.explorer.node.NodeProvider;
import org.netbeans.lib.ddl.CommandNotSupportedException;
import org.netbeans.lib.ddl.DDLException;
import org.netbeans.lib.ddl.impl.AbstractCommand;
import org.netbeans.lib.ddl.impl.Specification;
import org.netbeans.modules.db.DatabaseModule;
import org.netbeans.modules.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.explorer.DatabaseConnector;
import org.netbeans.modules.db.metadata.model.api.Action;
import org.netbeans.modules.db.metadata.model.api.Metadata;
import org.netbeans.modules.db.metadata.model.api.MetadataElementHandle;
import org.netbeans.modules.db.metadata.model.api.MetadataModel;
import org.netbeans.modules.db.metadata.model.api.MetadataModelException;
import org.netbeans.modules.db.metadata.model.api.Procedure;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.PropertySupport;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author Rob Englander, Jiri Rechtacek, Stjepan Brbot
 * 
 */
public class ProcedureNode extends BaseNode {
    private static final Logger LOG = Logger.getLogger(ProcedureNode.class.getName());
    
    private static final String ICON_VALID_P = "org/netbeans/modules/db/resources/procedure.png";
    private static final String ICON_VALID_F = "org/netbeans/modules/db/resources/function.png";
    private static final String ICON_VALID_T = "org/netbeans/modules/db/resources/trigger.png";
    private static final String ICON_INVALID_P = "org/netbeans/modules/db/resources/procedure-invalid.png";
    private static final String ICON_INVALID_F = "org/netbeans/modules/db/resources/function-invalid.png";
    private static final String ICON_INVALID_T = "org/netbeans/modules/db/resources/trigger-invalid.png";
    private static final String FOLDER = "Procedure"; //NOI18N
    
    private static final String DELIMITER = "@@"; // NOI18N
    private static final String SPACE = " "; // NOI18N
    private static final String NEW_LINE = "\n"; // NOI18N
    private static final String TRIGGER = "TRIGGER"; // NOI18N
    private static final String FUNCTION = "FUNCTION"; // NOI18N
    private static final String PROCEDURE = "PROCEDURE"; // NOI18N

    /**
     * Create an instance of ProcedureNode.
     * 
     * @param dataLookup the lookup to use when creating node providers
     * @param provider procedure node provider
     * @param schema database schema
     * @return the ProcedureNode instance
     */
    public static ProcedureNode create(NodeDataLookup dataLookup, ProcedureNodeProvider provider, String schema) {
        DatabaseConnection conn = dataLookup.lookup(DatabaseConnection.class);
        ProcedureNode node;
        if (conn != null && DatabaseModule.IDENTIFIER_MYSQL.equalsIgnoreCase(conn.getDriverName())) {
            node = new MySQL(dataLookup, provider, schema);
        } else if (conn != null && conn.getDriverName() != null && conn.getDriverName().startsWith(DatabaseModule.IDENTIFIER_ORACLE)) {
            node = new Oracle(dataLookup, provider, schema);
        } else {
            node = new ProcedureNode(dataLookup, provider);
        }
        node.setup();
        return node;
    }

    private String name = ""; // NOI18N
    private final MetadataElementHandle<Procedure> procedureHandle;
    private final DatabaseConnection connection;
    private Type type;
    private String schemaName;
    private String catalogName;

    @SuppressWarnings("unchecked")
    private ProcedureNode(NodeDataLookup lookup, NodeProvider provider) {
        super(new ChildNodeFactory(lookup), lookup, FOLDER, provider);
        connection = getLookup().lookup(DatabaseConnection.class);
        procedureHandle = getLookup().lookup(MetadataElementHandle.class);
    }

    @Override
    protected void initialize() {
        boolean connected = connection.isConnected();
        MetadataModel metaDataModel = connection.getMetadataModel();
        if (connected && metaDataModel != null) {
            try {
                metaDataModel.runReadAction(
                    new Action<Metadata>() {
                        @Override
                        public void run(Metadata metaData) {
                            Procedure proc = procedureHandle.resolve(metaData);
                            name = proc.getName();
                            type = proc.getReturnValue() == null ? Type.Procedure : Type.Function;

                            updateProperties(proc);
                            schemaName = proc.getParent().getName();
                            catalogName = proc.getParent().getParent().getName();
                        }
                    }
                );
            } catch (MetadataModelException e) {
                NodeRegistry.handleMetadataModelException(this.getClass(), connection, e, true);
            }
        }
    }

    private void updateProperties(Procedure proc) {
        PropertySupport.Name ps = new PropertySupport.Name(this);
        addProperty(ps);

        if (proc.getReturnValue() == null) {
            addProperty(TYPE, TYPEDESC, String.class, false, NbBundle.getMessage (ProcedureNode.class, "StoredProcedure")); // NOI18N
        } else {
            addProperty(TYPE, TYPEDESC, String.class, false, NbBundle.getMessage (ProcedureNode.class, "StoredFunction")); // NOI18N
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
    
    protected Type getType() {
        return this.type;
    }

    @Override
    public String getIconBase() {
        if (getType() == null) {
            return null;
        }
        switch (getType()) {
            case Function:
                return ICON_VALID_F;
            case Procedure:
                return ICON_VALID_P;
            case Trigger:
                return ICON_VALID_T;
            default:
                return null;
        }
    }

    @Override
    public String getShortDescription() {
        switch (getType()) {
            case Function:
                return NbBundle.getMessage (ProcedureNode.class, "ND_Function"); //NOI18N
            case Procedure:
                return NbBundle.getMessage (ProcedureNode.class, "ND_Procedure"); //NOI18N
            case Trigger:
                return NbBundle.getMessage (ProcedureNode.class, "ND_Trigger"); //NOI18N;
            default:
                return null;
        }
    }

    @Override
    public boolean canDestroy() {
        DatabaseConnector connector = connection.getConnector();
        return connector.supportsCommand(Specification.DROP_PROCEDURE);
    }

    @Override
    public void destroy() {
        DatabaseConnector connector = connection.getConnector();
        Specification spec = connector.getDatabaseSpecification();

        try {
            AbstractCommand command = null;
            switch (getType()) {
                case Function:
                    command = spec.createCommandDropFunction(getName());
                    break;
                case Procedure:
                    command = spec.createCommandDropProcedure(getName());
                    break;
                case Trigger:
                    command = spec.createCommandDropTrigger(getName());
                    break;
                default:
                    assert false : "Unknown type " + getType();
            }
            if (command == null) {
                LOG.log(Level.INFO, "No command found for droping {0}", getName());
                return ;
            }
            if (getOwner() != null) {
                command.setObjectOwner(getOwner());
            }
            command.execute();
            remove();
        } catch (DDLException e) {
            LOG.log(Level.INFO, "{0} while deleting {1} {2}", new Object[] {e, getTypeName(getType()), getName()});
            DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message(e.getMessage(), NotifyDescriptor.ERROR_MESSAGE));
        } catch (CommandNotSupportedException e) {
            LOG.log(Level.INFO, "{0} while deleting {1} {2}", new Object[] {e, getTypeName(getType()), getName()});
        }
    }
    
    private String getOwner() {
        String owner;
        if (schemaName == null) {
            owner = catalogName;
        } else {
            owner = schemaName;
        }
        return owner;
    }
    
    public boolean isViewSourceSupported() {
        return false;
    }
    
    public boolean isEditSourceSupported() {
        return false;
    }
    
    public String getParams() {
        return "";
    }
    
    public String getBody() {
        return "";
    }

    public String getSource() {
        return "";
    }
    
    public String getDDL() {
        return "";
    }
    
    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx(ProcedureNode.class);
    }

    public enum Type {
        Procedure,
        Function,
        Trigger
    }
    
    /**
     * https://dev.mysql.com/doc/refman/8.0/en/create-procedure.html
     */
    public static class MySQL extends ProcedureNode {
        private final DatabaseConnection connection;
        private final ProcedureNodeProvider provider;
        
        @SuppressWarnings("unchecked")
        private MySQL(NodeDataLookup lookup, ProcedureNodeProvider provider, String schema) {
            super(lookup, provider);
            this.connection = getLookup().lookup(DatabaseConnection.class);
            this.provider = provider;
        }

        @Override
        protected void initialize() {
            super.initialize();
            updateProcedureProperties(this, provider);
        }

        @Override
        public Type getType() {
            return provider.getType(getName());
        }

        @Override
        public String getShortDescription() {
            switch (provider.getType(getName())) {
                case Function:
                    return provider.getStatus(getName()) ? NbBundle.getMessage (ProcedureNode.class, "ND_Function") : NbBundle.getMessage (ProcedureNode.class, "ND_Function_Invalid"); //NOI18N
                case Procedure:
                    return provider.getStatus(getName())  ? NbBundle.getMessage (ProcedureNode.class, "ND_Procedure") : NbBundle.getMessage (ProcedureNode.class, "ND_Procedure_Invalid"); //NOI18N
                case Trigger:
                    return provider.getStatus(getName())  ? NbBundle.getMessage (ProcedureNode.class, "ND_Trigger") : NbBundle.getMessage (ProcedureNode.class, "ND_Trigger_Invalid"); //NOI18N;
                default:
                    return null;
            }
        }

        @Override
        public String getIconBase() {
            Type type = getType();
            if (type == null) {
                return null;
            }
            switch (type) {
                case Function:
                    return provider.getStatus(getName()) ? ICON_VALID_F : ICON_INVALID_F;
                case Procedure:
                    return provider.getStatus(getName()) ? ICON_VALID_P : ICON_INVALID_P;
                case Trigger:
                    return provider.getStatus(getName()) ? ICON_VALID_T : ICON_INVALID_T;
                default:
                    return null;
            }
        }

        @Override
        public boolean isViewSourceSupported() {
            return true;
        }

        @Override
        public String getSource() {
            String source = "";
            try {
                String query = "";
                String escapedName = "";
                boolean function = false;
                switch (getType()) {
                    case Function:
                        function = true;
                    case Procedure:
                        escapedName = getName().replace("'", "''");
                        query = "SELECT routine_schema,routine_definition,dtd_identifier,is_deterministic,sql_data_access,routine_comment," // NOI18N
                              + "(SELECT GROUP_CONCAT(CONCAT(" + (function ? "" : "parameter_mode,' ',") + "parameter_name,' ',dtd_identifier))" // NOI18N
                              + " FROM information_schema.parameters" // NOI18N
                              + " WHERE specific_name=routine_name AND ordinal_position>0) AS routine_params" // NOI18N
                              + " FROM information_schema.routines" // NOI18N
                              + " WHERE routine_name='" + escapedName + "';"; // NOI18N
                        try (Statement stat = connection.getJDBCConnection().createStatement();
                                ResultSet rs = stat.executeQuery(query);) {

                            while (rs.next()) {
                                String parent = rs.getString("routine_schema"); // NOI18N
                                if (parent != null && parent.trim().length() > 0) {
                                    parent += '.';
                                } else {
                                    parent = "";
                                }
                                
                                //Concatenated list of routine parameters
                                String params = rs.getString("routine_params"); // NOI18N
                                
                                //Data access characteristic: CONTAINS SQL, NO SQL, READS SQL DATA, or MODIFIES SQL DATA.
                                String sql_data_access = rs.getString("sql_data_access");
                                
                                //YES or NO, depending on whether the routine is defined with the DETERMINISTIC characteristic. 
                                String is_deterministic = rs.getString("is_deterministic"); // NOI18N
                                
                                //Routine comment
                                String comment = rs.getString("routine_comment"); // NOI18N

                                source = getTypeName(getType()) + " " + parent + getName() + "(" + params + ")" + "\n"
                                       + (function ? "RETURNS " + rs.getString("dtd_identifier") + "\n" : "") // NOI18N
                                       + sql_data_access + "\n"
                                       + (is_deterministic == "YES" ? "" : "NOT ") + "DETERMINISTIC\n" // NOI18N
                                       + (comment.length() > 0 ? "COMMENT '" + comment +"'\n" : "") // NOI18N
                                       + rs.getString("routine_definition");
                            }
                        }
                        break;
                    case Trigger:
                        /*
                        CREATE
                            [DEFINER = { user | CURRENT_USER }]
                            TRIGGER trigger_name trigger_time trigger_event
                            ON tbl_name FOR EACH ROW trigger_body
                         */
                        escapedName = getName().replace("'", "''");
                        query = "SELECT trigger_schema,action_statement,action_timing,event_manipulation,event_object_schema,event_object_table" // NOI18N
                              + " FROM information_schema.triggers" // NOI18N
                              + " WHERE trigger_name='" + escapedName + "';"; // NOI18N

                        try (Statement stat = connection.getJDBCConnection().createStatement();
                                ResultSet rs = stat.executeQuery(query);) {
                            while (rs.next()) {
                                String parent = rs.getString("trigger_schema"); // NOI18N
                                if (parent != null && parent.trim().length() > 0) {
                                    parent += '.'; // NOI18N
                                } else {
                                    parent = "";
                                }
                                String trigger_body = rs.getString("action_statement"); // NOI18N
                                String trigger_time = rs.getString("action_timing"); // NOI18N
                                String trigger_event = rs.getString("event_manipulation"); // NOI18N
                                String tbl_schema = rs.getString("event_object_schema"); // NOI18N
                                String tbl_table_name = rs.getString("event_object_table"); // NOI18N
                                String tbl_name;
                                if(tbl_schema == null || tbl_schema.length() == 0 ) {
                                    tbl_name = tbl_table_name;
                                } else {
                                    tbl_name = tbl_schema + '.' + tbl_table_name; // NOI18N
                                }
                                source = TRIGGER + " " + parent + getName() + '\n' // NOI18N
                                       + trigger_time + ' ' + trigger_event + " ON " + tbl_name + '\n' // NOI18N
                                       + "FOR EACH ROW" + '\n' // NOI18N
                                       + trigger_body;
                            }
                        }
                        break;
                    default:
                        assert false : "Unknown type " + getType();
                }
            } catch (SQLException ex) {
                LOG.log(Level.INFO, "{0} while get source of {1} {2}", new Object[] {ex, getTypeName(getType()), getName()});
            }
            return source;
        }

        @Override
        public String getParams() {
            String params = "";
            String escapedName = "";
            String query = "";
            try {
                boolean function = false;
                switch (getType()) {
                    case Function:
                        function = true;
                    case Procedure:
                        escapedName = getName().replace("'", "''");
                        query = "SELECT GROUP_CONCAT(CONCAT(" + (function ? "" : "parameter_mode,' ',") + "parameter_name,' ',dtd_identifier)) AS routine_params" // NOI18N
                              + " FROM information_schema.parameters" // NOI18N
                              + " WHERE ordinal_position>0 AND specific_name='" + escapedName + "';"; // NOI18N
                        try (Statement stat = connection.getJDBCConnection().createStatement();
                                ResultSet rs = stat.executeQuery(query);) {
                            while (rs.next()) {
                                params = rs.getString("routine_params"); // NOI18N
                            }
                        }
                        break;
                    case Trigger:
                        escapedName = getName().replace("'", "''");
                        query = "SELECT action_timing,event_manipulation,event_object_schema,event_object_table" // NOI18N
                              + " FROM information_schema.triggers" // NOI18N
                              + " WHERE trigger_name='" + escapedName + "';"; // NOI18N
                        try (Statement stat = connection.getJDBCConnection().createStatement();
                                ResultSet rs = stat.executeQuery(query);) {
                            while (rs.next()) {
                                String trigger_time = rs.getString("action_timing"); // NOI18N
                                String trigger_event = rs.getString("event_manipulation"); // NOI18N
                                String tbl_schema = rs.getString("event_object_schema"); // NOI18N
                                String tbl_table_name = rs.getString("event_object_table"); // NOI18N
                                String tbl_name;
                                if (tbl_schema == null || tbl_schema.length() == 0) {
                                    tbl_name = tbl_table_name;
                                } else {
                                    tbl_name = tbl_schema + '.' + tbl_table_name; // NOI18N
                                }
                                params = trigger_time + ' ' + trigger_event
                                       + " ON " + tbl_name + '\n'
                                       + "FOR EACH ROW" + '\n'; // NOI18N
                            }
                        }
                        break;
                    default:
                        assert false : "Unknown type " + getType();
                }
            } catch (SQLException ex) {
                LOG.log(Level.INFO, "{0} while get params of {1} {2}", new Object[] {ex, getTypeName(getType()), getName()});
            }
            return params;
        }

        @Override
        public String getBody() {
            String body = "";
            String escapedName = "";
            String query = "";
            try {
                switch (getType()) {
                    case Function:
                    case Procedure:
                        escapedName = getName().replace("'", "''");
                        query = "SELECT routine_definition" // NOI18N
                              + " FROM information_schema.routines" // NOI18N
                              + " WHERE routine_name='" + escapedName + "';"; // NOI18N
                        try (Statement stat = connection.getJDBCConnection().createStatement();
                                ResultSet rs = stat.executeQuery(query);) {
                            while (rs.next()) {
                                body = rs.getString("routine_definition"); // NOI18N
                            }
                        }
                        break;
                    case Trigger:
                        escapedName = getName().replace("'", "''");
                        query = "SELECT action_statement" // NOI18N
                              + " FROM information_schema.triggers" // NOI18N
                              + " WHERE trigger_name='" + escapedName + "';"; // NOI18N
                        try (Statement stat = connection.getJDBCConnection().createStatement();
                                ResultSet rs = stat.executeQuery(query)) {
                            while (rs.next()) {
                                body = rs.getString("action_statement"); // NOI18N
                            }
                        }
                        break;
                    default:
                        assert false : "Unknown type " + getType();
                }
            } catch (SQLException ex) {
                LOG.log(Level.INFO, "{0} while get body of {1} {2}", new Object[] {ex, getTypeName(getType()), getName()});
            }
            return body;
        }
        
        

        @Override
        public boolean isEditSourceSupported() {
            return true;
        }

        @Override
        public String getDDL() {
            StringBuilder expression = new StringBuilder();
            String escapedName = getName().replace("'", "''");
            expression.append("DELIMITER ").append(DELIMITER).append(NEW_LINE); // NOI18N
            expression.append("DROP ").append(getTypeName(getType())).append(" ").append(escapedName).append(SPACE).append(DELIMITER).append(NEW_LINE); // NOI18N
            expression.append("CREATE ").append(getSource()); // NOI18N
            expression.append(SPACE).append(DELIMITER).append(SPACE).append(NEW_LINE); // NOI18N
            expression.append("DELIMITER ; ").append(NEW_LINE); // NOI18N
            return expression.toString();
        }

    }
    
    public static class Oracle extends ProcedureNode {
        private final DatabaseConnection connection;
        private final ProcedureNodeProvider provider;
        private final String schema;

        @SuppressWarnings("unchecked")
        private Oracle(NodeDataLookup lookup, ProcedureNodeProvider provider, String schema) {
            super(lookup, provider);
            connection = getLookup().lookup(DatabaseConnection.class);
            this.provider = provider;
            this.schema = schema;
        }

        @Override
        protected void initialize() {
            super.initialize();
            updateProcedureProperties(this, provider);
        }

        @Override
        public Type getType() {
            return provider.getType(getName());
        }

        @Override
        public String getShortDescription() {
            switch (provider.getType(getName())) {
                case Function:
                    return provider.getStatus(getName()) ? NbBundle.getMessage (ProcedureNode.class, "ND_Function") : NbBundle.getMessage (ProcedureNode.class, "ND_Function_Invalid"); //NOI18N
                case Procedure:
                    return provider.getStatus(getName())  ? NbBundle.getMessage (ProcedureNode.class, "ND_Procedure") : NbBundle.getMessage (ProcedureNode.class, "ND_Procedure_Invalid"); //NOI18N
                case Trigger:
                    return provider.getStatus(getName())  ? NbBundle.getMessage (ProcedureNode.class, "ND_Trigger") : NbBundle.getMessage (ProcedureNode.class, "ND_Trigger_Invalid"); //NOI18N;
                default:
                    return null;
            }
        }

        @Override
        public String getIconBase() {
            Type type = getType();
            if (type == null) {
                return null;
            }
            switch (type) {
                case Function:
                    return provider.getStatus(getName()) ? ICON_VALID_F : ICON_INVALID_F;
                case Procedure:
                    return provider.getStatus(getName()) ? ICON_VALID_P : ICON_INVALID_P;
                case Trigger:
                    return provider.getStatus(getName()) ? ICON_VALID_T : ICON_INVALID_T;
                default:
                    return null;
            }
        }

        @Override
        public boolean isViewSourceSupported() {
            return true;
        }

        @Override
        public String getBody() {
            String source = getSource();
            String body = "";
            int beginIdx = source.indexOf("BEGIN"); // NOI18N
            if (beginIdx != -1) {
                body = source.substring(beginIdx);
            }
            return body;
        }

        @Override
        public String getParams() {
            String source = getSource();
            String params = "";
            int beginIdx = source.indexOf("BEGIN"); // NOI18N
            int lIdx = source.indexOf('('); // NOI18N
            int rIdx = source.indexOf(')'); // NOI18N
            if (lIdx != -1 && rIdx != -1 && lIdx < beginIdx) {
                params = source.substring(lIdx, rIdx + 1);
            }
            return params;
        }
        
        @Override
        public String getSource() {
            StringBuilder sb = new StringBuilder();
            String owner = "";
            try {
                String escapedName = getName().replace("'", "''");
                String escapedSchemaName = schema.toUpperCase().replace("'", "''");
                // select text from sys.dba_source where name = ??? and owner = upper('???') order by dba_source.line;
                try (Statement stat = connection.getJDBCConnection().createStatement()) {
                    // select text from sys.dba_source where name = ??? and owner = upper('???') order by dba_source.line;
                    String q = "SELECT text,owner" // NOI18N
                             + " FROM sys.all_source" // NOI18N
                             + " WHERE name='" + escapedName + "' AND owner='" + escapedSchemaName + "'" // NOI18N
                             + " ORDER BY line"; // NOI18N
                    try (ResultSet rs = stat.executeQuery(q)) {
                        while (rs.next()) {
                            sb.append(rs.getString("text")); // NOI18N
                            owner = rs.getString("owner"); // NOI18N
                        }
                    }
                }
            } catch (SQLException ex) {
                LOG.log(Level.INFO, "{0} while get source of {1} {2}", new Object[] {ex, getTypeName(getType()), getName()});
            }
            return fqn(sb.toString(), owner);
        }

        @Override
        public boolean isEditSourceSupported() {
            return true;
        }

        @Override
        public String getDDL() {
            StringBuilder expression = new StringBuilder();
            expression.append("DELIMITER ").append(DELIMITER).append(NEW_LINE); // NOI18N
            expression.append("CREATE OR REPLACE ").append(getSource()); // NOI18N
            expression.append(SPACE).append(DELIMITER).append(NEW_LINE); // NOI18N
            expression.append("DELIMITER ; ").append(NEW_LINE); // NOI18N
            return expression.toString();
        }

        private String fqn(String source, String owner) {
            String upperSource = source.toUpperCase();
            String toFind = getTypeName(getType()) + " ";
            String res = source;
            int nameIdx = upperSource.indexOf(toFind);
            if (nameIdx != -1) {
                // don't duplicate owner
                if (upperSource.substring(nameIdx + toFind.length()).trim().startsWith(owner.toUpperCase() + '.')) {
                    return source;
                }
                res = source.substring(0, nameIdx + toFind.length()) +
                        owner +
                        '.' +
                        source.substring(nameIdx + toFind.length()).trim();
            }
            return res;
        }

    }
    
    private static String getTypeName(Type t) {
        String name = "";
        switch (t) {
            case Function:
                name = FUNCTION;
                break;
            case Procedure:
                name = PROCEDURE;
                break;
            case Trigger:
                name = TRIGGER;
                break;
            default:
                assert false : "Unknown type " + t;
        }
        return name;
    }

    private static void updateProcedureProperties(ProcedureNode node,
            ProcedureNodeProvider provider) {
        PropertySupport.Name ps = new PropertySupport.Name(node);
        node.addProperty(ps);
        Type type = provider.getType(node.getName());
        if (type == null) {
            LOG.log(Level.INFO, "Unknown type of object {0}", node.getName());
            return;
        }
        switch (type) {
            case Function:
                setTypeProperty(node, "StoredFunction"); // NOI18N
                break;
            case Procedure:
                setTypeProperty(node, "StoredProcedure"); // NOI18N
                break;
            case Trigger:
                setTypeProperty(node, "StoredTrigger"); // NOI18N
                break;
            default:
                assert false : "Unknown type " + provider.getType(node.getName());
        }
    }

    private static void setTypeProperty(ProcedureNode node, String bundleKey) {
        node.addProperty(TYPE, TYPEDESC, String.class, false,
                NbBundle.getMessage(ProcedureNode.class, bundleKey));
    }
}
