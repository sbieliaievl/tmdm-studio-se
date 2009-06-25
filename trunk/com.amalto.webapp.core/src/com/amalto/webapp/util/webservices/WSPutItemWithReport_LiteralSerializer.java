// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;

public class WSPutItemWithReport_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_wsPutItem_QNAME = new QName("", "wsPutItem");
    private static final QName ns2_WSPutItem_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSPutItem");
    private CombinedSerializer ns2_myWSPutItem_LiteralSerializer;
    private static final QName ns1_source_QNAME = new QName("", "source");
    private static final QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final QName ns1_operationType_QNAME = new QName("", "operationType");
    private static final QName ns1_wsUpdateReportItemArray_QNAME = new QName("", "wsUpdateReportItemArray");
    private static final QName ns2_WSUpdateReportItemArray_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSUpdateReportItemArray");
    private CombinedSerializer ns2_myWSUpdateReportItemArray_LiteralSerializer;
    
    public WSPutItemWithReport_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSPutItemWithReport_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myWSPutItem_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.webapp.util.webservices.WSPutItem.class, ns2_WSPutItem_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myWSUpdateReportItemArray_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.webapp.util.webservices.WSUpdateReportItemArray.class, ns2_WSUpdateReportItemArray_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSPutItemWithReport instance = new com.amalto.webapp.util.webservices.WSPutItemWithReport();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsPutItem_QNAME)) {
                member = ns2_myWSPutItem_LiteralSerializer.deserialize(ns1_wsPutItem_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setWsPutItem((com.amalto.webapp.util.webservices.WSPutItem)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_wsPutItem_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_source_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_source_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setSource((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_source_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_operationType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_operationType_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setOperationType((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_operationType_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsUpdateReportItemArray_QNAME)) {
                member = ns2_myWSUpdateReportItemArray_LiteralSerializer.deserialize(ns1_wsUpdateReportItemArray_QNAME, reader, context);
                instance.setWsUpdateReportItemArray((com.amalto.webapp.util.webservices.WSUpdateReportItemArray)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_wsUpdateReportItemArray_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSPutItemWithReport instance = (com.amalto.webapp.util.webservices.WSPutItemWithReport)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSPutItemWithReport instance = (com.amalto.webapp.util.webservices.WSPutItemWithReport)obj;
        
        if (instance.getWsPutItem() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2_myWSPutItem_LiteralSerializer.serialize(instance.getWsPutItem(), ns1_wsPutItem_QNAME, null, writer, context);
        if (instance.getSource() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSource(), ns1_source_QNAME, null, writer, context);
        if (instance.getOperationType() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getOperationType(), ns1_operationType_QNAME, null, writer, context);
        ns2_myWSUpdateReportItemArray_LiteralSerializer.serialize(instance.getWsUpdateReportItemArray(), ns1_wsUpdateReportItemArray_QNAME, null, writer, context);
    }
}
