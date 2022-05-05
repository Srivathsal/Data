//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.09 at 10:43:29 AM IST 
//


package com.intuit.accountant.services.dcm.model.wms;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * 
 *                 The ModificationEvent models audit information (who was responsible for a change, when the change occurred, and what system was used to execute the change).
 *             
 * 
 * <p>Java class for modificationEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modificationEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="on" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="by" type="{http://services.intuit.com/accountant/v1}identity"/>
 *         &lt;element name="using" type="{http://services.intuit.com/accountant/v1}identity"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modificationEvent", namespace = "http://services.intuit.com/accountant/v1", propOrder = {
    "on",
    "by",
    "using"
})
public class ModificationEvent {

    @XmlElement(namespace = "http://services.intuit.com/accountant/v1", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar on;
    @XmlElement(namespace = "http://services.intuit.com/accountant/v1", required = true)
    protected Identity by;
    @XmlElement(namespace = "http://services.intuit.com/accountant/v1", required = true)
    protected Identity using;

    /**
     * Gets the value of the on property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getOn() {
        return on;
    }

    /**
     * Sets the value of the on property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setOn(XMLGregorianCalendar value) {
        this.on = value;
    }

    /**
     * Gets the value of the by property.
     *
     * @return
     *     possible object is
     *     {@link Identity }
     *
     */
    public Identity getBy() {
        return by;
    }

    /**
     * Sets the value of the by property.
     *
     * @param value
     *     allowed object is
     *     {@link Identity }
     *
     */
    public void setBy(Identity value) {
        this.by = value;
    }

    /**
     * Gets the value of the using property.
     *
     * @return
     *     possible object is
     *     {@link Identity }
     *
     */
    public Identity getUsing() {
        return using;
    }

    /**
     * Sets the value of the using property.
     *
     * @param value
     *     allowed object is
     *     {@link Identity }
     *
     */
    public void setUsing(Identity value) {
        this.using = value;
    }

}