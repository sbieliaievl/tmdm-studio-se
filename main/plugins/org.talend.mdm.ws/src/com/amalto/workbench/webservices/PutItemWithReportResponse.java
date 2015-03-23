
package com.amalto.workbench.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for putItemWithReportResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="putItemWithReportResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.talend.com/mdm}wsItemPK" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putItemWithReportResponse", propOrder = {
    "_return"
})
public class PutItemWithReportResponse {

    @XmlElement(name = "return")
    protected WsItemPK _return;

    /**
     * Default no-arg constructor
     * 
     */
    public PutItemWithReportResponse() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public PutItemWithReportResponse(final WsItemPK _return) {
        this._return = _return;
    }

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link WsItemPK }
     *     
     */
    public WsItemPK getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsItemPK }
     *     
     */
    public void setReturn(WsItemPK value) {
        this._return = value;
    }

}