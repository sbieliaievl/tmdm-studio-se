// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.detailtabs.sections.util.simpletype;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.xsd.XSDSimpleTypeDefinition;

import com.amalto.workbench.detailtabs.sections.model.simpletype.propsource.SimpleTypeFacetDatePropertySource;
import com.amalto.workbench.detailtabs.sections.model.simpletype.propsource.SimpleTypeFacetDoublePropertySource;
import com.amalto.workbench.detailtabs.sections.model.simpletype.propsource.SimpleTypeFacetIntegerPropertySource;
import com.amalto.workbench.utils.IConstants;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.utils.XSDUtil;
import com.amalto.workbench.widgets.composites.property.IPropertySource;

public class SimpleTypeMaxExclusiveFacetPropSourceBuilder extends SimpleTypeFacetPropSourceBuilder {

    @Override
    protected IPropertySource<?> doCreatePropSource(XSDSimpleTypeDefinition simpleType, Composite cellEditorParent,
            Object sourceFacetValue) {

        if (Util.isDouble(simpleType) || Util.isDecimal(simpleType) || Util.isFloat(simpleType)) {
            return new SimpleTypeFacetDoublePropertySource(cellEditorParent, IConstants.SIMPLETYPE_FACETNAME_MAXEXCLUSIVE,
                    toDoubleQuietly(sourceFacetValue));
        }
        if (Util.isDate(simpleType)) {
            return new SimpleTypeFacetDatePropertySource(cellEditorParent, IConstants.SIMPLETYPE_FACETNAME_MAXEXCLUSIVE,
                    (String) sourceFacetValue, XSDUtil.VALIDATE_DATE);
        }
        if (Util.isDateTime(simpleType)) {
            return new SimpleTypeFacetDatePropertySource(cellEditorParent, IConstants.SIMPLETYPE_FACETNAME_MAXEXCLUSIVE,
                    (String) sourceFacetValue, XSDUtil.VALIDATE_DATE_TIME);
        }
        if (Util.isTime(simpleType)) {
            return new SimpleTypeFacetDatePropertySource(cellEditorParent, IConstants.SIMPLETYPE_FACETNAME_MAXEXCLUSIVE,
                    (String) sourceFacetValue, XSDUtil.VALIDATE_TIME);
        }
        return new SimpleTypeFacetIntegerPropertySource(cellEditorParent, IConstants.SIMPLETYPE_FACETNAME_MAXEXCLUSIVE,
                toIntegerQuielty(sourceFacetValue));
    }

    @Override
    protected Object getSourceFacetValue(XSDSimpleTypeDefinition simpleType) {

        if (Util.isDouble(simpleType) || Util.isDecimal(simpleType) || Util.isFloat(simpleType)) {
            return SimpleTypeFacetValueExtractor.getDoubleFacetValue(simpleType.getMaxExclusiveFacet());
        }
        if (Util.isDate(simpleType) || Util.isDateTime(simpleType) || Util.isTime(simpleType)) {
            return SimpleTypeFacetValueExtractor.getStringFacetValue(simpleType.getMaxExclusiveFacet());
        }
        return SimpleTypeFacetValueExtractor.getIntFacetValue(simpleType.getMaxExclusiveFacet());
    }

}
