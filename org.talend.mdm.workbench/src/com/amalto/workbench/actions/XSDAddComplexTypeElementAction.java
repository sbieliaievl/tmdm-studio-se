// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.xsd.XSDAnnotation;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDCompositor;
import org.eclipse.xsd.XSDDerivationMethod;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDIdentityConstraintCategory;
import org.eclipse.xsd.XSDIdentityConstraintDefinition;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTerm;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.XSDXPathDefinition;
import org.eclipse.xsd.XSDXPathVariety;
import org.eclipse.xsd.impl.XSDModelGroupImpl;
import org.eclipse.xsd.impl.XSDParticleImpl;
import org.eclipse.xsd.util.XSDSchemaBuildingTools;
import org.w3c.dom.Element;

import com.amalto.workbench.dialogs.ComplexTypeInputDialogR;
import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.i18n.Messages;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.utils.XSDAnnotationsStructure;

public class XSDAddComplexTypeElementAction extends UndoAction {

    private static Log log = LogFactory.getLog(XSDAddComplexTypeElementAction.class);

    private XSDParticle selParticle = null;

    private ComplexTypeInputDialogR dialogR;

    private String elementName;

    private String refName;

    private int minOccurs;

    private int maxOccurs;

    private String superTypeName;

    private String typeName;

    private boolean isAbstract;

    private boolean isChoice;

    private boolean isAll;

    public XSDAddComplexTypeElementAction(DataModelMainPage page) {
        super(page);
        setText("Add Complex Type Element"); //$NON-NLS-1$
    }

    public void updateElementFields() {
        elementName = dialogR.getElementName();
        refName = dialogR.getRefName();
        minOccurs = dialogR.getMinOccurs();
        maxOccurs = dialogR.getMaxOccurs();

        superTypeName = dialogR.getSuperName();
        isAbstract = dialogR.isAbstract();
        typeName = dialogR.getTypeName();
        isChoice = dialogR.isChoice();
        isAll = dialogR.isAll();
    }

    @Override
    public IStatus doAction() {
        IStructuredSelection selection = (IStructuredSelection) page.getTreeViewer().getSelection();
        selParticle = (XSDParticle) selection.getFirstElement();

        if (!(selParticle.getContainer() instanceof XSDModelGroup))
            return Status.CANCEL_STATUS;

        int ret = openDialog();
        if (ret == Dialog.CANCEL) {
            return Status.CANCEL_STATUS;
        }

        updateElementFields();

        XSDElementDeclaration elem = (XSDElementDeclaration) selParticle.getContent();
        if (Util.changeElementTypeToSequence(elem, maxOccurs) == Status.CANCEL_STATUS) {
            return Status.CANCEL_STATUS;
        }

        try {
            XSDParticle particle = createParticle();
            XSDElementDeclaration declaration = (XSDElementDeclaration) particle.getContent();
            transformToComplexType(declaration);
            
            page.refresh();
            page.getTreeViewer().setSelection(new StructuredSelection(selParticle), true);
            page.markDirty();
            
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MessageDialog.openError(page.getSite().getShell(), Messages.getString("_Error"), //$NON-NLS-1$
                    Messages.getString("_CreateCTypeError") + e.getLocalizedMessage()); //$NON-NLS-1$
            return Status.CANCEL_STATUS;
        }

        return Status.OK_STATUS;
    }

    private int openDialog() {
        XSDSimpleTypeDefinition simpleTypeDefinition = schema.resolveSimpleTypeDefinition(schema.getSchemaForSchemaNamespace(),
                "string"); //$NON-NLS-1$
        List<XSDComplexTypeDefinition> types = Util.getComplexTypes(schema);

        dialogR = new ComplexTypeInputDialogR(page.getSite().getShell(), Messages.getString("_AddCTypeError"), selParticle, schema, types, //$NON-NLS-1$
                simpleTypeDefinition, false, false);

        dialogR.setBlockOnOpen(true);
        int ret = dialogR.open();

        return ret;
    }

    private XSDParticle createParticle() {

        XSDFactory factory = XSDSchemaBuildingTools.getXSDFactory();

        XSDElementDeclaration resultElementDeclaration = factory.createXSDElementDeclaration();
        resultElementDeclaration.setName(elementName);
        if (!refName.equals("")) { //$NON-NLS-1$
            XSDElementDeclaration ref = Util.findReference(refName, schema);
            if (ref != null) {
                resultElementDeclaration.setResolvedElementDeclaration(ref);
            }
        } else {
            resultElementDeclaration.setTypeDefinition(schema.resolveSimpleTypeDefinition(schema.getSchemaForSchemaNamespace(),
                    "string")); //$NON-NLS-1$
        }

        XSDParticle resultParticle = factory.createXSDParticle();
        resultParticle.setContent(resultElementDeclaration);
        resultParticle.setMinOccurs(this.minOccurs);
        XSDModelGroup group = (XSDModelGroup) selParticle.getContainer();
        if (maxOccurs > -1) {
            resultParticle.setMaxOccurs(this.maxOccurs);
        } else {
            resultParticle.setMaxOccurs(this.maxOccurs);
            group.getContents().add(group.getContents().size(), resultParticle);
            group.updateElement();
            if (resultParticle.getElement().getAttributeNode("maxOccurs") != null)//$NON-NLS-1$
                resultParticle.getElement().getAttributeNode("maxOccurs").setNodeValue("unbounded");//$NON-NLS-1$//$NON-NLS-2$
            else {
                resultParticle.getElement().setAttribute("maxOccurs", "unbounded");//$NON-NLS-1$//$NON-NLS-2$
            }
        }
        if (maxOccurs > -1) {
            group.getContents().add(group.getContents().size(), resultParticle);
            group.updateElement();
        }

        if (dialogR.isInherit()) {
            XSDTerm totm = resultParticle.getTerm();
            XSDElementDeclaration concept = null;
            if (Util.getParent(selParticle) instanceof XSDElementDeclaration)
                concept = (XSDElementDeclaration) Util.getParent(selParticle);
            else if (Util.getParent(selParticle) instanceof XSDComplexTypeDefinition) {
                if (selParticle instanceof XSDParticle)
                    concept = (XSDElementDeclaration) ((XSDParticle) selParticle).getContent();
                else if (selParticle instanceof XSDElementDeclaration)
                    concept = (XSDElementDeclaration) selParticle;
            }
            XSDAnnotation fromannotation = null;
            if (concept != null)
                fromannotation = concept.getAnnotation();
            if (fromannotation != null) {
                XSDAnnotationsStructure struc = new XSDAnnotationsStructure(totm);
                if (((XSDElementDeclaration) totm).getType() != null)
                    addAnnotion(struc, fromannotation);
            }

        }

        return resultParticle;
    }

    public void addAnnotion(XSDAnnotationsStructure struc, XSDAnnotation xsdannotationparent) {
        Map<String, List<String>> infor = new HashMap<String, List<String>>();
        infor = cloneXSDAnnotation(xsdannotationparent);
        Set<String> keys = infor.keySet();
        for (int i = 0; i < infor.size(); i++) {
            List<String> lists = (List<String>) infor.get(keys.toArray()[i]);
            try {
                struc.setAccessRole(lists, false, (IStructuredContentProvider) page.getTreeViewer().getContentProvider(),
                        (String) keys.toArray()[i]);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public Map<String, List<String>> cloneXSDAnnotation(XSDAnnotation oldAnn) {
        Map<String, List<String>> infor = new HashMap<String, List<String>>();
        try {
            if (oldAnn != null) {
                for (int i = 0; i < oldAnn.getApplicationInformation().size(); i++) {
                    Element oldElem = oldAnn.getApplicationInformation().get(i);
                    String type = oldElem.getAttributes().getNamedItem("source").getNodeValue(); //$NON-NLS-1$
                    // X_Write,X_Hide,X_Workflow
                    if (type.equals("X_Write") || type.equals("X_Hide") || type.equals("X_Workflow")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        if (!infor.containsKey(type)) {
                            List<String> typeList = new ArrayList<String>();
                            typeList.add(oldElem.getFirstChild().getNodeValue());
                            infor.put(type, typeList);
                        } else {
                            (infor.get(type)).add(oldElem.getFirstChild().getNodeValue());
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MessageDialog.openError(this.page.getSite().getShell(), Messages.getString("_Error"), //$NON-NLS-1$
                    Messages.getString("_PasteError") + e.getLocalizedMessage()); //$NON-NLS-1$
        }
        return infor;
    }

    private void transformToComplexType(XSDElementDeclaration decl) {
        List<XSDComplexTypeDefinition> types = Util.getComplexTypes(schema);

        XSDFactory factory = XSDSchemaBuildingTools.getXSDFactory();
        boolean anonymous = (typeName == null) || ("".equals(typeName));//$NON-NLS-1$
        boolean alreadyExists = false;

        XSDComplexTypeDefinition complexType = null;
        // the sub element created if needed
        XSDParticle subParticle = null;
        XSDParticle groupParticle = null;
        XSDElementDeclaration subElement = null;

        // check if already exist
        // add by ymli; fix the bug:0012278;
        XSDElementDeclaration parent = null;
        Object pObject = Util.getParent(decl);
        if (pObject instanceof XSDElementDeclaration)
            parent = (XSDElementDeclaration) pObject;
        else if (pObject instanceof XSDComplexTypeDefinition)
            complexType = (XSDComplexTypeDefinition) pObject;

        if (!anonymous) {
            EList<XSDTypeDefinition> list = schema.getTypeDefinitions();
            String ns = "";//$NON-NLS-1$
            if (typeName.lastIndexOf(" : ") != -1) {//$NON-NLS-1$
                ns = typeName.substring(typeName.lastIndexOf(" : ") + 3);//$NON-NLS-1$
                typeName = typeName.substring(0, typeName.lastIndexOf(" : "));//$NON-NLS-1$
            }
            for (Iterator<XSDTypeDefinition> iter = list.iterator(); iter.hasNext();) {
                XSDTypeDefinition td = (XSDTypeDefinition) iter.next();
                if ((td.getName().equals(typeName) && (td instanceof XSDComplexTypeDefinition))) {
                    alreadyExists = true;
                    complexType = (XSDComplexTypeDefinition) td;
                    break;
                }
            }

        } else {
            if (parent != null && decl.getTypeDefinition() instanceof XSDComplexTypeDefinition)
                // complexType = (XSDComplexTypeDefinition) parent.getTypeDefinition();
                complexType = (XSDComplexTypeDefinition) decl.getTypeDefinition();
            if (complexType != null && complexType.getSchema() != null && complexType.getName() == null) {
                alreadyExists = true;
            }
            if (decl.getTypeDefinition() instanceof XSDSimpleTypeDefinition)
                alreadyExists = false;
        }

        if (parent != null && complexType != null && complexType.getSchema() != null) {

            XSDParticleImpl partCnt = (XSDParticleImpl) complexType.getContentType();
            XSDModelGroupImpl mdlGrp = (XSDModelGroupImpl) partCnt.getTerm();
            if (mdlGrp.getSchema() != null) {
                if (isChoice)
                    mdlGrp.setCompositor(XSDCompositor.CHOICE_LITERAL);
                else if (isAll) {
                    mdlGrp.setCompositor(XSDCompositor.ALL_LITERAL);

                } else {
                    mdlGrp.setCompositor(XSDCompositor.SEQUENCE_LITERAL);
                }
            }
            partCnt.unsetMaxOccurs();
            partCnt.unsetMinOccurs();
            XSDTypeDefinition superType = null;
            for (XSDTypeDefinition type : types) {
                if (type.getName().equals(superTypeName)) {
                    superType = type;
                    break;
                }
            }

            if (superType != null) {
                complexType.setDerivationMethod(XSDDerivationMethod.EXTENSION_LITERAL);
                complexType.setBaseTypeDefinition(superType);
            }
            if (isAbstract)
                complexType.setAbstract(isAbstract);
            else
                complexType.unsetAbstract();

            if (parent != null)
                parent.updateElement();
            if (complexType != null)
                complexType.updateElement();
        }

        // Create if does not exist
        if (!alreadyExists) {

            // add an element declaration
            subElement = factory.createXSDElementDeclaration();
            subElement.setName("subelement");//$NON-NLS-1$
            subElement.setTypeDefinition(schema.resolveSimpleTypeDefinition(schema.getSchemaForSchemaNamespace(), "string"));//$NON-NLS-1$

            subParticle = factory.createXSDParticle();
            subParticle.unsetMaxOccurs();
            subParticle.unsetMinOccurs();
            subParticle.setContent(subElement);
            subParticle.updateElement();

            // create group
            XSDModelGroup group = factory.createXSDModelGroup();
            if (isChoice)
                group.setCompositor(XSDCompositor.CHOICE_LITERAL);
            else if (isAll)
                group.setCompositor(XSDCompositor.ALL_LITERAL);
            else
                group.setCompositor(XSDCompositor.SEQUENCE_LITERAL);
            group.getContents().add(0, subParticle);
            group.updateElement();

            // create the complex type
            complexType = factory.createXSDComplexTypeDefinition();
            // complexType.setDerivationMethod(XSDDerivationMethod.EXTENSION_LITERAL);
            if (!anonymous) {
                // if (true) {
                XSDTypeDefinition superType = null;
                for (XSDTypeDefinition type : types) {
                    if (type.getName().equals(superTypeName)) {
                        superType = type;
                        break;
                    }
                }
                complexType.setName(typeName);
                if (superType != null) {
                    complexType.setDerivationMethod(XSDDerivationMethod.EXTENSION_LITERAL);
                    complexType.setBaseTypeDefinition(superType);
                }
                if (isAbstract)
                    complexType.setAbstract(isAbstract);
                else
                    complexType.unsetAbstract();
                schema.getContents().add(complexType);
            }
            complexType.updateElement();

            // add the group
            groupParticle = factory.createXSDParticle();
            groupParticle.unsetMaxOccurs();
            groupParticle.unsetMinOccurs();
            groupParticle.setContent(group);
            groupParticle.updateElement();

            complexType.setContent(groupParticle);
            complexType.updateElement();
        }// end if NOT already exusts

        // set complex type to concept
        if (anonymous)
            decl.setAnonymousTypeDefinition(complexType);
        else {
            decl.setTypeDefinition(complexType);
        }

        boolean isConcept = false;
        if (isConcept) {

            // remove exisiting unique key(s)
            List<XSDIdentityConstraintDefinition> keys = new ArrayList<XSDIdentityConstraintDefinition>();
            EList<XSDIdentityConstraintDefinition> list = decl.getIdentityConstraintDefinitions();
            for (Iterator<XSDIdentityConstraintDefinition> iter = list.iterator(); iter.hasNext();) {
                XSDIdentityConstraintDefinition icd = (XSDIdentityConstraintDefinition) iter.next();
                if (icd.getIdentityConstraintCategory().equals(XSDIdentityConstraintCategory.UNIQUE_LITERAL))
                    keys.add(icd);
            }
            decl.getIdentityConstraintDefinitions().removeAll(keys);

            // add new unique key with first element declaration name
            XSDElementDeclaration firstDecl = null;
            if (complexType.getContent() instanceof XSDParticle) {
                if (((XSDParticle) complexType.getContent()).getTerm() instanceof XSDModelGroup) {
                    XSDModelGroup group = (XSDModelGroup) ((XSDParticle) complexType.getContent()).getTerm();
                    EList<XSDParticle> gpl = group.getContents();
                    for (Iterator<XSDParticle> iter = gpl.iterator(); iter.hasNext();) {
                        XSDParticle part = (XSDParticle) iter.next();
                        if (part.getTerm() instanceof XSDElementDeclaration) {
                            firstDecl = (XSDElementDeclaration) part.getTerm();
                            break;
                        }
                    }
                }
            }
            if (firstDecl != null) {
                XSDIdentityConstraintDefinition uniqueKey = factory.createXSDIdentityConstraintDefinition();
                uniqueKey.setIdentityConstraintCategory(XSDIdentityConstraintCategory.UNIQUE_LITERAL);
                uniqueKey.setName(decl.getName());
                XSDXPathDefinition selector = factory.createXSDXPathDefinition();
                selector.setVariety(XSDXPathVariety.SELECTOR_LITERAL);
                selector.setValue(".");//$NON-NLS-1$
                uniqueKey.setSelector(selector);
                XSDXPathDefinition field = factory.createXSDXPathDefinition();
                field.setVariety(XSDXPathVariety.FIELD_LITERAL);
                field.setValue(firstDecl.getAliasName());
                uniqueKey.getFields().add(field);
                decl.getIdentityConstraintDefinitions().add(uniqueKey);
            }

        }// if isConcept

        decl.updateElement();
        schema.update();
    }

}
