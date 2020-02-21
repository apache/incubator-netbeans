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
package org.netbeans.modules.subversion.remote.ui.diff;

import java.awt.EventQueue;
import java.util.logging.Level;
import org.netbeans.modules.subversion.remote.FileInformation;
import org.netbeans.modules.subversion.remote.RepositoryFile;
import org.netbeans.modules.subversion.remote.Subversion;
import org.netbeans.modules.subversion.remote.SvnModuleConfig;
import org.netbeans.modules.subversion.remote.api.ISVNStatus;
import org.netbeans.modules.subversion.remote.api.SVNClientException;
import org.netbeans.modules.subversion.remote.api.SVNRevision;
import org.netbeans.modules.subversion.remote.api.SVNUrl;
import org.netbeans.modules.subversion.remote.ui.actions.ActionUtils;
import org.netbeans.modules.subversion.remote.ui.actions.ContextAction;
import org.netbeans.modules.subversion.remote.ui.status.SyncFileNode;
import org.netbeans.modules.subversion.remote.util.ClientCheckSupport;
import org.netbeans.modules.subversion.remote.util.Context;
import org.netbeans.modules.subversion.remote.util.SvnUtils;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.netbeans.modules.versioning.util.Utils;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

/**
 * Diff action shows local changes
 *
 * 
 */
public class DiffAction extends ContextAction {
    private static final String ICON_RESOURCE = "org/netbeans/modules/subversion/remote/resources/icons/diff.png"; //NOI18N

    public DiffAction () {
        super(ICON_RESOURCE);
    }
    
    @Override
    protected String getBaseName(Node[] nodes) {
        return "CTL_MenuItem_Diff";    // NOI18N
    }

    @Override
    protected int getFileEnabledStatus() {
        return getDirectoryEnabledStatus();
    }

    @Override
    protected int getDirectoryEnabledStatus() {
        return FileInformation.STATUS_MANAGED 
             & ~FileInformation.STATUS_NOTVERSIONED_EXCLUDED; 
    }

    @Override
    protected String iconResource () {
        return ICON_RESOURCE;
    }
    
    public static void diff (final Context ctx, final int type, final String contextName,
            final boolean initialStatusRefreshDisabled) {
        Utils.post(new Runnable() {
            @Override
            public void run () {
                SVNUrl repositoryUrl = null, fileUrl = null;
                RepositoryFile left = null, right = null;
                VCSFileProxy[] roots = SvnUtils.getActionRoots(ctx, false);
                if (roots != null) {
                    try {
                        VCSFileProxy interestingFile;
                        if(roots.length == 1) {
                            interestingFile = roots[0];
                        } else {
                            interestingFile = SvnUtils.getPrimaryFile(roots[0]);
                        }
                        repositoryUrl = SvnUtils.getRepositoryRootUrl(interestingFile);
                        fileUrl = SvnUtils.getRepositoryUrl(interestingFile);
                    } catch (SVNClientException ex) {
                        Subversion.LOG.log(Level.INFO, null, ex);
                    }
                }
                if (repositoryUrl != null && fileUrl != null) {
                    if (type == Setup.DIFFTYPE_LOCAL) {
                        left = new RepositoryFile(ctx.getFileSystem(), repositoryUrl, fileUrl, SVNRevision.BASE);
                        right = new RepositoryFile(ctx.getFileSystem(), repositoryUrl, fileUrl, SVNRevision.WORKING);
                    } else if (type == Setup.DIFFTYPE_REMOTE) {
                        left = new RepositoryFile(ctx.getFileSystem(), repositoryUrl, fileUrl, SVNRevision.HEAD);
                        right = new RepositoryFile(ctx.getFileSystem(), repositoryUrl, fileUrl, SVNRevision.BASE);
                    } else {
                        left = new RepositoryFile(ctx.getFileSystem(), repositoryUrl, fileUrl, SVNRevision.HEAD);
                        right = new RepositoryFile(ctx.getFileSystem(), repositoryUrl, fileUrl, SVNRevision.WORKING);
                    }
                }
                final SVNUrl fRepositoryUrl = repositoryUrl;
                final SVNUrl fFileUrl = fileUrl;
                final RepositoryFile fLeft = left;
                final RepositoryFile fRight = right;
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run () {
                        MultiDiffPanel panel = new MultiDiffPanel(ctx, type, contextName, initialStatusRefreshDisabled,
                                fRepositoryUrl, fFileUrl, fLeft, fRight);
                        DiffTopComponent tc = new DiffTopComponent(panel);
                        tc.setName(NbBundle.getMessage(DiffAction.class, "CTL_DiffPanel_Title", contextName)); // NOI18N
                        tc.open();
                        tc.requestActive();        
                    }
                });
            }
        });
    }

    public static void diff(VCSFileProxy file, String rev1, String rev2) {
        MultiDiffPanel panel = new MultiDiffPanel(file, rev1, rev2, false); // spawns bacground DiffPrepareTask
        DiffTopComponent tc = new DiffTopComponent(panel);
        tc.setName(NbBundle.getMessage(DiffAction.class, "CTL_DiffPanel_Title", file.getName())); // NOI18N
        tc.open();
        tc.requestActive();
    }

    public static void diff(VCSFileProxy file, ISVNStatus status) {
        MultiDiffPanel panel = new MultiDiffPanel(file, status);
        DiffTopComponent tc = new DiffTopComponent(panel);
        tc.setName(NbBundle.getMessage(DiffAction.class, "CTL_DiffPanel_Title", file.getName())); // NOI18N
        tc.open();
        tc.requestActive();
    }
    
    @Override
    protected void performContextAction(final Node[] nodes) {
        ClientCheckSupport.getInstance().runInAWTIfAvailable(nodes, ActionUtils.cutAmpersand(getRunningName(nodes)), new Runnable() {
            @Override
            public void run() {
                Context ctx = getContext(nodes);
                String contextName = getContextDisplayName(nodes);
                diff(ctx, SvnModuleConfig.getDefault(ctx.getFileSystem()).getLastUsedModificationContext(), contextName, isSvnNodes(nodes));
            }
        });
    }
    
    /**
     * Returns true if the given nodes are from the versioning view.
     * In such case the deep scan is not required because the files and their statuses should already be known
     * @param nodes
     * @return
     */
    private static boolean isSvnNodes (Node[] nodes) {
        boolean fromSubversionView = true;
        for (Node node : nodes) {
            if (!(node instanceof SyncFileNode)) {
                fromSubversionView = false;
                break;
            }
        }
        return fromSubversionView;
    }   
    
    public static class DiffToBaseAction extends ContextAction {
        @Override
        protected String getBaseName(Node[] nodes) {
            return "CTL_MenuItem_DiffToBase"; //NOI18N
        }

        @Override
        protected int getFileEnabledStatus() {
            return getDirectoryEnabledStatus();
        }

        @Override
        protected int getDirectoryEnabledStatus() {
            return FileInformation.STATUS_MANAGED 
                 & ~FileInformation.STATUS_NOTVERSIONED_EXCLUDED; 
        }

        @Override
        protected void performContextAction (final Node[] nodes) {
            ClientCheckSupport.getInstance().runInAWTIfAvailable(nodes, ActionUtils.cutAmpersand(getRunningName(nodes)), new Runnable() {
                @Override
                public void run() {
                    Context ctx = getContext(nodes);
                    String contextName = getContextDisplayName(nodes);
                    diff(ctx, Setup.DIFFTYPE_LOCAL, contextName, isSvnNodes(nodes));
                }
            });
        }
    }
    
    public static class DiffToRepositoryAction extends ContextAction {
        @Override
        protected String getBaseName(Node[] nodes) {
            return "CTL_MenuItem_DiffToRepository"; //NOI18N
        }

        @Override
        protected int getFileEnabledStatus() {
            return getDirectoryEnabledStatus();
        }

        @Override
        protected int getDirectoryEnabledStatus() {
            return FileInformation.STATUS_MANAGED 
                 & ~FileInformation.STATUS_NOTVERSIONED_EXCLUDED; 
        }

        @Override
        protected void performContextAction (final Node[] nodes) {
            ClientCheckSupport.getInstance().runInAWTIfAvailable(nodes, ActionUtils.cutAmpersand(getRunningName(nodes)), new Runnable() {
                @Override
                public void run() {
                    Context ctx = getContext(nodes);
                    String contextName = getContextDisplayName(nodes);
                    diff(ctx, Setup.DIFFTYPE_ALL, contextName, isSvnNodes(nodes));
                }
            });
        }
    }
}
