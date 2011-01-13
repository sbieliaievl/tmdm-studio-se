// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.studio.test.workflow.item;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.talend.mdm.studio.test.TalendSWTBotForMDM;

/**
 * DOC rhou class global comment. Detailled comment
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class WorkflowGenerateProcessTest extends TalendSWTBotForMDM {

    private SWTBotTreeItem workflowParentItem;

    @Before
    public void runBeforeEveryTest() {
        workflowParentItem = serverItem.getNode("Workflow");
        workflowParentItem.expand();

    }

    @After
    public void runAfterEveryTest() {

    }

    @Test
    public void workflowGenerateProcessTest() {
        SWTBotTreeItem workflowItem;
        if (workflowParentItem.getItems().length > 0) {
            workflowItem = workflowParentItem.getNode(0);
            workflowItem.contextMenu("Generate Workflow Caller Process").click();
        }
    }

}
