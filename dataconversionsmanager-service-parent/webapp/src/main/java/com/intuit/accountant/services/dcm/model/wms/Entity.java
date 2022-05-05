//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.09 at 10:43:29 AM IST 
//


package com.intuit.accountant.services.dcm.model.wms;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 *                 The Entity models the base type for domain objects.  It possesses an identity, a version, and audit information in the form of ModificationEvents
 *             
 * 
 * <p>Java class for entity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.intuit.com/accountant/v1}extensibleType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://services.intuit.com/accountant/v1}identity"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="created" type="{http://services.intuit.com/accountant/v1}modificationEvent"/>
 *         &lt;element name="lastUpdated" type="{http://services.intuit.com/accountant/v1}modificationEvent"/>
 *         &lt;element name="Aliases" type="{http://services.intuit.com/accountant/v1}identity" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entity", namespace = "http://services.intuit.com/accountant/v1", propOrder = {
    "id",
    "version",
    "created",
    "lastUpdated",
    "aliases"
})
@XmlSeeAlso({
    Job.class
})
public abstract class Entity
    extends ExtensibleType
{

    @XmlElement(namespace = "http://services.intuit.com/accountant/v1", required = true)
    protected Identity id;
    @XmlElement(namespace = "http://services.intuit.com/accountant/v1", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger version;
    @XmlElement(namespace = "http://services.intuit.com/accountant/v1", required = true)
    protected ModificationEvent created;
    @XmlElement(namespace = "http://services.intuit.com/accountant/v1", required = true)
    protected ModificationEvent lastUpdated;
    @XmlElement(name = "Aliases", namespace = "http://services.intuit.com/accountant/v1")
    protected List<Identity> aliases;

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link Identity }
     *
     */
    public Identity getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link Identity }
     *
     */
    public void setId(Identity value) {
        this.id = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setVersion(BigInteger value) {
        this.version = value;
    }

    /**
     * Gets the value of the created property.
     *
     * @return
     *     possible object is
     *     {@link ModificationEvent }
     *
     */
    public ModificationEvent getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     *
     * @param value
     *     allowed object is
     *     {@link ModificationEvent }
     *
     */
    public void setCreated(ModificationEvent value) {
        this.created = value;
    }

    /**
     * Gets the value of the lastUpdated property.
     *
     * @return
     *     possible object is
     *     {@link ModificationEvent }
     *
     */
    public ModificationEvent getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the value of the lastUpdated property.
     *
     * @param value
     *     allowed object is
     *     {@link ModificationEvent }
     *
     */
    public void setLastUpdated(ModificationEvent value) {
        this.lastUpdated = value;
    }

    /**
     * Gets the value of the aliases property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aliases property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAliases().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Identity }
     *
     *
     */
    public List<Identity> getAliases() {
        if (aliases == null) {
            aliases = new ArrayList<Identity>();
        }
        return this.aliases;
    }

}
