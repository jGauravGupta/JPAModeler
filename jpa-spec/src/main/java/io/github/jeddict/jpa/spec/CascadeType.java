//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 *
 *
 * public enum CascadeType { ALL, PERSIST, MERGE, REMOVE, REFRESH, DETACH};
 *
 *
 *
 * <p>
 * Java class for cascade-type complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="cascade-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cascade-all" type="{http://java.sun.com/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="cascade-persist" type="{http://java.sun.com/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="cascade-merge" type="{http://java.sun.com/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="cascade-remove" type="{http://java.sun.com/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="cascade-refresh" type="{http://java.sun.com/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="cascade-detach" type="{http://java.sun.com/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cascade-type", propOrder = {
    "cascadeAll",
    "cascadePersist",
    "cascadeMerge",
    "cascadeRemove",
    "cascadeRefresh",
    "cascadeDetach"
})
public class CascadeType {

    @XmlElement(name = "cascade-all")
    protected EmptyType cascadeAll;
    @XmlElement(name = "cascade-persist")
    protected EmptyType cascadePersist;
    @XmlElement(name = "cascade-merge")
    protected EmptyType cascadeMerge;
    @XmlElement(name = "cascade-remove")
    protected EmptyType cascadeRemove;
    @XmlElement(name = "cascade-refresh")
    protected EmptyType cascadeRefresh;
    @XmlElement(name = "cascade-detach")
    protected EmptyType cascadeDetach;

    /**
     * Gets the value of the cascadeAll property.
     *
     * @return possible object is {@link EmptyType }
     *
     */
    public EmptyType getCascadeAll() {
        return cascadeAll;
    }

    /**
     * Sets the value of the cascadeAll property.
     *
     * @param value allowed object is {@link EmptyType }
     *
     */
    public void setCascadeAll(EmptyType value) {
        this.cascadeAll = value;
    }

    /**
     * Gets the value of the cascadePersist property.
     *
     * @return possible object is {@link EmptyType }
     *
     */
    public EmptyType getCascadePersist() {
        return cascadePersist;
    }

    /**
     * Sets the value of the cascadePersist property.
     *
     * @param value allowed object is {@link EmptyType }
     *
     */
    public void setCascadePersist(EmptyType value) {
        this.cascadePersist = value;
    }

    /**
     * Gets the value of the cascadeMerge property.
     *
     * @return possible object is {@link EmptyType }
     *
     */
    public EmptyType getCascadeMerge() {
        return cascadeMerge;
    }

    /**
     * Sets the value of the cascadeMerge property.
     *
     * @param value allowed object is {@link EmptyType }
     *
     */
    public void setCascadeMerge(EmptyType value) {
        this.cascadeMerge = value;
    }

    /**
     * Gets the value of the cascadeRemove property.
     *
     * @return possible object is {@link EmptyType }
     *
     */
    public EmptyType getCascadeRemove() {
        return cascadeRemove;
    }

    /**
     * Sets the value of the cascadeRemove property.
     *
     * @param value allowed object is {@link EmptyType }
     *
     */
    public void setCascadeRemove(EmptyType value) {
        this.cascadeRemove = value;
    }

    /**
     * Gets the value of the cascadeRefresh property.
     *
     * @return possible object is {@link EmptyType }
     *
     */
    public EmptyType getCascadeRefresh() {
        return cascadeRefresh;
    }

    /**
     * Sets the value of the cascadeRefresh property.
     *
     * @param value allowed object is {@link EmptyType }
     *
     */
    public void setCascadeRefresh(EmptyType value) {
        this.cascadeRefresh = value;
    }

    /**
     * Gets the value of the cascadeDetach property.
     *
     * @return possible object is {@link EmptyType }
     *
     */
    public EmptyType getCascadeDetach() {
        return cascadeDetach;
    }

    /**
     * Sets the value of the cascadeDetach property.
     *
     * @param value allowed object is {@link EmptyType }
     *
     */
    public void setCascadeDetach(EmptyType value) {
        this.cascadeDetach = value;
    }

}
