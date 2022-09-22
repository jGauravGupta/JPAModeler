//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import io.github.jeddict.source.AnnotationExplorer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.persistence.internal.jpa.metadata.tables.UniqueConstraintMetadata;

/**
 *
 *
 * @Target({}) @Retention(RUNTIME) public @interface UniqueConstraint { String
 * name() default ""; String[] columnNames(); }
 *
 *
 *
 * <p>
 * Java class for unique-constraint complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="unique-constraint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="column-name" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unique-constraint", propOrder = {
    "columnName"
})
public class UniqueConstraint {

    @XmlElement(name = "column-name", required = true)
    private List<String> columnName;
    @XmlAttribute
    protected String name;

    public UniqueConstraint() {
    }

    public UniqueConstraint(String name) {
        this.name = name;
    }

    public static UniqueConstraint load(AnnotationExplorer annotation) {
        UniqueConstraint uniqueConstraint = new UniqueConstraint();
        annotation.getString("name").ifPresent(uniqueConstraint::setName);
        uniqueConstraint.getColumnName().addAll(annotation.getStringList("columnNames"));
        return uniqueConstraint;
    }

    /**
     * Gets the value of the columnName property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the columnName property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColumnName().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     */
    public List<String> getColumnName() {
        if (columnName == null) {
            setColumnName(new ArrayList<>());
        }
        return this.columnName;
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
     * @param columnName the columnName to set
     */
    public void setColumnName(List<String> columnName) {
        this.columnName = columnName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UniqueConstraint other = (UniqueConstraint) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getColumnName().stream().collect(Collectors.joining(", "));
    }

    public UniqueConstraintMetadata getAccessor() {
        UniqueConstraintMetadata accessor = new UniqueConstraintMetadata();
        accessor.setName(name);
        accessor.setColumnNames(columnName);
        return accessor;//java.lang.IllegalAccessError: tried to access method org.eclipse.persistence.tools.schemaframework.TableDefinition.buildUniqueKeyConstraint
    }
}
