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
import org.eclipse.persistence.internal.jpa.metadata.columns.DiscriminatorColumnMetadata;

/**
 *
 *
 * @Target({TYPE}) @Retention(RUNTIME) public @interface DiscriminatorColumn {
 * String name() default "DTYPE"; DiscriminatorType discriminatorType() default
 * STRING; String columnDefinition() default ""; int length() default 31; }
 *
 *
 *
 * <p>
 * Java class for discriminator-column complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="discriminator-column">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="discriminator-type" type="{http://java.sun.com/xml/ns/persistence/orm}discriminator-type" />
 *       &lt;attribute name="column-definition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="length" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "discriminator-column")
public class DiscriminatorColumn {

    @XmlAttribute
    protected String name;
    @XmlAttribute(name = "discriminator-type")
    protected DiscriminatorType discriminatorType;
    @XmlAttribute(name = "column-definition")
    protected String columnDefinition;
    @XmlAttribute
    protected Integer length;

    public static DiscriminatorColumn load(AnnotationExplorer annotation) {
        DiscriminatorColumn column = new DiscriminatorColumn();
        annotation.getString("name").ifPresent(column::setName);
        annotation.getString("columnDefinition").ifPresent(column::setColumnDefinition);
        annotation.getInt("length").ifPresent(column::setLength);
        annotation.getEnum("discriminatorType").map(DiscriminatorType::valueOf)
                .ifPresent(column::setDiscriminatorType);
        return column;
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
     * Gets the value of the discriminatorType property.
     *
     * @return possible object is {@link DiscriminatorType }
     *
     */
    public DiscriminatorType getDiscriminatorType() {
        return discriminatorType;
    }

    /**
     * Sets the value of the discriminatorType property.
     *
     * @param value allowed object is {@link DiscriminatorType }
     *
     */
    public void setDiscriminatorType(DiscriminatorType value) {
        this.discriminatorType = value;
    }

    /**
     * Gets the value of the columnDefinition property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getColumnDefinition() {
        return columnDefinition;
    }

    /**
     * Sets the value of the columnDefinition property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setColumnDefinition(String value) {
        this.columnDefinition = value;
    }

    /**
     * Gets the value of the length property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setLength(Integer value) {
        this.length = value;
    }

    public DiscriminatorColumnMetadata getAccessor() {
        DiscriminatorColumnMetadata accessor = new DiscriminatorColumnMetadata();
        accessor.setColumnDefinition(columnDefinition);
        accessor.setLength(length);
        accessor.setName(name);
        if (discriminatorType != null && discriminatorType != DiscriminatorType.INTEGER ) { //Issue : 51 # filter for integer not supported in dynamic eclipselink
            accessor.setDiscriminatorType(discriminatorType.value());
        }
        return accessor;
    }
}
