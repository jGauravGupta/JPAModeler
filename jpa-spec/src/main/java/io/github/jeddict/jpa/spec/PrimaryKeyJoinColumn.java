//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import io.github.jeddict.jpa.spec.extend.IJoinColumn;
import io.github.jeddict.jpa.spec.validator.column.ForeignKeyValidator;
import io.github.jeddict.jpa.spec.validator.column.PrimaryKeyJoinColumnValidator;
import io.github.jeddict.source.AnnotatedMember;
import io.github.jeddict.source.AnnotationExplorer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.eclipse.persistence.internal.jpa.metadata.columns.PrimaryKeyJoinColumnMetadata;
/**
 *
 *
 * @Target({TYPE, METHOD, FIELD}) @Retention(RUNTIME) public @interface
 * PrimaryKeyJoinColumn { String name() default ""; String
 * referencedColumnName() default ""; String columnDefinition() default ""; }
 *
 *
 *
 * <p>
 * Java class for primary-key-join-column complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="primary-key-join-column">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="referenced-column-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="column-definition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pk-jc")
@XmlJavaTypeAdapter(value = PrimaryKeyJoinColumnValidator.class)
public class PrimaryKeyJoinColumn implements IJoinColumn {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlTransient
    private String implicitName;//automatically assigned by persistence provider
    @XmlAttribute(name = "rc")
    protected String referencedColumnName;
    @XmlAttribute(name = "cd")
    protected String columnDefinition;
    @XmlElement(name = "fk")
    private ForeignKey foreignKey;

    private static PrimaryKeyJoinColumn load(AnnotationExplorer annotation) {
        PrimaryKeyJoinColumn primaryKeyJoinColumn = new PrimaryKeyJoinColumn();
        annotation.getString("name").ifPresent(primaryKeyJoinColumn::setName);
        annotation.getString("referencedColumnName").ifPresent(primaryKeyJoinColumn::setReferencedColumnName);
        annotation.getString("columnDefinition").ifPresent(primaryKeyJoinColumn::setColumnDefinition);
        annotation.getAnnotation("foreignKey").map(ForeignKey::load).ifPresent(primaryKeyJoinColumn::setForeignKey);
        return primaryKeyJoinColumn;
    }

    public static List<PrimaryKeyJoinColumn> load(AnnotatedMember member) {
        List<PrimaryKeyJoinColumn> primaryKeyJoinColumns = new ArrayList<>();

        Optional<AnnotationExplorer> primaryKeyJoinColumnsOpt = member.getAnnotation(javax.persistence.PrimaryKeyJoinColumns.class);
        if (primaryKeyJoinColumnsOpt.isPresent()) {
            primaryKeyJoinColumns.addAll(
                    primaryKeyJoinColumnsOpt.get()
                            .getAnnotationList("value")
                            .map(PrimaryKeyJoinColumn::load)
                            .collect(toList())
            );
        } else {
            primaryKeyJoinColumns.addAll(
                    member.getRepeatableAnnotations(javax.persistence.PrimaryKeyJoinColumn.class)
                            .map(PrimaryKeyJoinColumn::load)
                            .collect(toList())
            );
        }
        return primaryKeyJoinColumns;
    }
    
    
    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    @Override
    public void setName(String value) {
        if (value != null) {
            value = value.toUpperCase();
        }
        this.name = value;
    }

    /**
     * Gets the value of the referencedColumnName property.
     *
     * @return possible object is {@link String }
     *
     */
    @Override
    public String getReferencedColumnName() {
        return referencedColumnName;
    }

    /**
     * Sets the value of the referencedColumnName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    @Override
    public void setReferencedColumnName(String value) {
        if (value != null) {
            value = value.toUpperCase();
        }
        this.referencedColumnName = value;
    }

    /**
     * Gets the value of the columnDefinition property.
     *
     * @return possible object is {@link String }
     *
     */
    @Override
    public String getColumnDefinition() {
        return columnDefinition;
    }

    /**
     * Sets the value of the columnDefinition property.
     *
     * @param value allowed object is {@link String }
     *
     */
    @Override
    public void setColumnDefinition(String value) {
        this.columnDefinition = value;
    }

    /**
     * @return the foreignKey
     */
    @Override
    public ForeignKey getForeignKey() {
        if (foreignKey == null) {
            foreignKey = new ForeignKey();
        }
        return foreignKey;
    }

    /**
     * @param foreignKey the foreignKey to set
     */
    @Override
    public void setForeignKey(ForeignKey foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * @return the implicitName
     */
    @Override
    public String getImplicitName() {
        return implicitName;
    }

    /**
     * @param implicitName the implicitName to set
     */
    @Override
    public void setImplicitName(String implicitName) {
        this.implicitName = implicitName;
    }

    public PrimaryKeyJoinColumnMetadata getAccessor() {
        PrimaryKeyJoinColumnMetadata accessor = new PrimaryKeyJoinColumnMetadata();
        accessor.setColumnDefinition(columnDefinition);
        accessor.setName(name);
        accessor.setReferencedColumnName(getReferencedColumnName());
        if (ForeignKeyValidator.isNotEmpty(foreignKey)) {
            accessor.setForeignKey(foreignKey.getAccessor());
        }
        return accessor;
    }
}
