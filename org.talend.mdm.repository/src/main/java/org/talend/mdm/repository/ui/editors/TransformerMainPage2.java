// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.repository.ui.editors;

import java.rmi.RemoteException;
import java.util.LinkedList;

import org.eclipse.ui.forms.editor.FormEditor;
import org.talend.core.model.properties.Item;
import org.talend.mdm.repository.core.service.RepositoryWebServiceAdapter;
import org.talend.mdm.repository.core.service.wsimpl.transformplugin.AbstractPluginDetail;
import org.talend.mdm.repository.model.mdmmetadata.MDMServerDef;
import org.talend.mdm.repository.model.mdmproperties.MDMServerObjectItem;
import org.talend.mdm.repository.model.mdmserverobject.MDMServerObject;
import org.talend.mdm.repository.ui.widgets.xmleditor.infoholder.RepositoryExternalInfoHolder;

import com.amalto.workbench.editors.TransformerMainPage;
import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.utils.XtentisException;
import com.amalto.workbench.webservices.WSTransformerPluginV2Details;
import com.amalto.workbench.webservices.WSTransformerPluginV2VariableDescriptor;
import com.amalto.workbench.webservices.WSTransformerV2;
import com.amalto.workbench.webservices.XtentisPort;
import com.amalto.workbench.widgets.xmleditor.infoholder.ExternalInfoHolder;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public class TransformerMainPage2 extends TransformerMainPage {

    XObjectEditor2 editor2;

    MDMServerDef lastServerDef;
    /**
     * DOC hbhong TransformerMainPage2 constructor comment.
     * 
     * @param editor
     */
    public TransformerMainPage2(FormEditor editor) {
        super(editor);
        this.editor2 = (XObjectEditor2) editor;
        setLastServerDef();
    }

    @Override
    protected void initExternalInfoHolder() {

        ExternalInfoHolder<?> allJobInfosHolder = RepositoryExternalInfoHolder.getAllJobInfosHolder(null);
        ExternalInfoHolder<?> mdmServerInfoHolder = RepositoryExternalInfoHolder.getAllMDMServerInfoHolder2(null);
        ExternalInfoHolder<?> allVarCandidatesHolder = RepositoryExternalInfoHolder
                .getProcessAllCallJobVarsCandidatesHolder((WSTransformerV2) getXObject().getWsObject());
        ExternalInfoHolder<?> workflowInfoHolder = RepositoryExternalInfoHolder.getAllWorkflowInfoHolder(null);
        ExternalInfoHolder<?> allDataModelHolderProxy = RepositoryExternalInfoHolder.getAllDataModelInfoHolderProxy(getXObject());

        initExternalInfoHolderForEachType("callJob", new ExternalInfoHolder<?>[] { allJobInfosHolder, mdmServerInfoHolder, //$NON-NLS-1$
                allVarCandidatesHolder });
        initExternalInfoHolderForEachType("workflowtrigger", new ExternalInfoHolder<?>[] { workflowInfoHolder, //$NON-NLS-1$
                allDataModelHolderProxy });

    }

    protected void initTransformer() throws XtentisException {
        TreeObject xObject = getXObject();
        transformer = (WSTransformerV2) xObject.getWsObject();
    }

    @Override
    protected void initPlugin() throws RemoteException {

        for (AbstractPluginDetail detail : RepositoryWebServiceAdapter.findAllTransformerPluginV2Details()) {

            String jndi = detail.getJNDIName();
            pluginsCombo.add(jndi);
            pluginDescriptions.put(jndi, detail.getDescription());
            // add input variables and output variables
            java.util.List<String> input = new LinkedList<String>();
            for (WSTransformerPluginV2VariableDescriptor v : detail.getInputVariableDescriptors()) {
                input.add(v.getVariableName());
            }
            inputVariablesMap.put(jndi, input);
            //
            java.util.List<String> output = new LinkedList<String>();
            for (WSTransformerPluginV2VariableDescriptor v : detail.getOutputVariableDescriptors()) {
                output.add(v.getVariableName());
            }
            outputVariablesMap.put(jndi, output);
        }

    }

    @Override
    protected XtentisPort getPort() {
        return RepositoryWebServiceAdapter.getXtentisPort(getSite().getShell(), lastServerDef);
    }

    @Override
    protected WSTransformerPluginV2Details getWsTransformerPluginV2Details(String jndi) throws RemoteException {
        return RepositoryWebServiceAdapter.findTransformerPluginV2Detail(jndi);
    }

    protected void openTransformerDialog() {
        setLastServerDef();
        super.openTransformerDialog();
    }

    private void setLastServerDef() {
        XObjectEditorInput2 input = (XObjectEditorInput2) editor2.getEditorInput();
        Item item = input.getInputItem();
        MDMServerObject serverObject = ((MDMServerObjectItem) item).getMDMServerObject();
        lastServerDef = serverObject.getLastServerDef();
    }

}
