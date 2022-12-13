//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import io.github.jeddict.source.AnnotationExplorer;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 *
 *
 * @Target({}) @Retention(RUNTIME) public @interface FieldResult { String
 * name(); String column(); }
 *
 *
 *
 * <p>
 * Java class for field-result complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="field-result">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="column" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "field-result")
public class FieldResult {

    @XmlAttribute(name = "n", required = true)//(required = true)
    protected String name;
    @XmlAttribute(name = "cl", required = true)//(required = true)
    protected String column;

    public static FieldResult load(AnnotationExplorer annotation) {
        FieldResult fieldResult = new FieldResult();
        annotation.getString("name").ifPresent(fieldResult::setName);
        annotation.getString("column").ifPresent(fieldResult::setColumn);
        return fieldResult;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the column property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets the value of the column property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setColumn(String value) {
        this.column = value;
    }

}
