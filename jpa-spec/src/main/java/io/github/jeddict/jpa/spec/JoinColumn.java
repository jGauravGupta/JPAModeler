//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import io.github.jeddict.jpa.spec.extend.IJoinColumn;
import io.github.jeddict.jpa.spec.validator.column.ForeignKeyValidator;
import io.github.jeddict.jpa.spec.validator.column.JoinColumnValidator;
import io.github.jeddict.source.AnnotatedMember;
import io.github.jeddict.source.AnnotationExplorer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.eclipse.persistence.internal.jpa.metadata.columns.JoinColumnMetadata;

/**
 *
 *
 * @Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface JoinColumn {
 * String name() default ""; String referencedColumnName() default ""; boolean
 * unique() default false; boolean nullable() default true; boolean insertable()
 * default true; boolean updatable() default true; String columnDefinition()
 * default ""; String table() default ""; ForeignKey foreignKey() default
 * @ForeignKey(); }
 *
 *
 *
 * <p>
 * Java class for join-column complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="join-column">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="referenced-column-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="unique" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="nullable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="insertable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="updatable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="column-definition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="table" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "join-column")
@XmlJavaTypeAdapter(value = JoinColumnValidator.class)
public class JoinColumn implements IJoinColumn {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlTransient
    private String implicitName;//automatically assigned by persistence provider
    @XmlAttribute(name = "rc")
    protected String referencedColumnName;
    @XmlAttribute
    protected Boolean unique = false;
    @XmlAttribute
    protected Boolean nullable = true;
    @XmlAttribute
    protected Boolean insertable = true;
    @XmlAttribute
    protected Boolean updatable = true;
    @XmlAttribute(name = "column-definition")
    protected String columnDefinition;
    @XmlAttribute(name = "table")
    protected String table;
    @XmlElement(name = "fk")
    private ForeignKey foreignKey;

    public static JoinColumn load(AnnotationExplorer annotation) {
        JoinColumn joinColumn = new JoinColumn();
        annotation.getString("name").ifPresent(joinColumn::setName);
        annotation.getString("referencedColumnName").ifPresent(joinColumn::setReferencedColumnName);
        annotation.getBoolean("unique").ifPresent(joinColumn::setUnique);
        annotation.getBoolean("nullable").ifPresent(joinColumn::setNullable);
        annotation.getBoolean("insertable").ifPresent(joinColumn::setInsertable);
        annotation.getBoolean("updatable").ifPresent(joinColumn::setUpdatable);
        annotation.getString("columnDefinition").ifPresent(joinColumn::setColumnDefinition);
        annotation.getString("table").ifPresent(joinColumn::setTable);
        annotation.getAnnotation("foreignKey").map(ForeignKey::load).ifPresent(joinColumn::setForeignKey);
        return joinColumn;
    }

    public static List<JoinColumn> load(AnnotatedMember member) {
        List<JoinColumn> joinColumns = new ArrayList<>();
        Optional<AnnotationExplorer> joinColumnsOpt = member.getAnnotation(jakarta.persistence.JoinColumns.class);
        if (joinColumnsOpt.isPresent()) {
            joinColumns.addAll(
                    joinColumnsOpt.get()
                            .getAnnotationList("value")
                            .map(JoinColumn::load)
                            .collect(toList())
            );
        }

        joinColumns.addAll(
                member.getRepeatableAnnotations(jakarta.persistence.JoinColumn.class)
                        .map(JoinColumn::load)
                        .collect(toList())
        );

        return joinColumns;
    }

    public static List<JoinColumn> loadMapKey(AnnotatedMember member) {
        List<JoinColumn> joinColumns = new ArrayList<>();
        Optional<AnnotationExplorer> joinColumnsOpt = member.getAnnotation(jakarta.persistence.MapKeyJoinColumns.class);
        if (joinColumnsOpt.isPresent()) {
            joinColumns.addAll(
                    joinColumnsOpt.get()
                            .getAnnotationList("value")
                            .map(JoinColumn::load)
                            .collect(toList())
            );
        }

        joinColumns.addAll(
                member.getRepeatableAnnotations(jakarta.persistence.MapKeyJoinColumn.class)
                        .map(JoinColumn::load)
                        .collect(toList())
        );

        return joinColumns;
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
     * Gets the value of the unique property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getUnique() {
        return unique;
    }

    /**
     * Sets the value of the unique property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setUnique(Boolean value) {
        this.unique = value;
    }

    /**
     * Gets the value of the nullable property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getNullable() {
        return nullable;
    }

    /**
     * Sets the value of the nullable property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNullable(Boolean value) {
        this.nullable = value;
    }

    /**
     * Gets the value of the insertable property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getInsertable() {
        return insertable;
    }

    /**
     * Sets the value of the insertable property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setInsertable(Boolean value) {
        this.insertable = value;
    }

    /**
     * Gets the value of the updatable property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getUpdatable() {
        return updatable;
    }

    /**
     * Sets the value of the updatable property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setUpdatable(Boolean value) {
        this.updatable = value;
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
     * Gets the value of the table property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the value of the table property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTable(String value) {
        this.table = value;
    }

    public JoinColumnMetadata getAccessor() {
        JoinColumnMetadata accessor = new JoinColumnMetadata();
        accessor.setColumnDefinition(columnDefinition);
        accessor.setInsertable(insertable);
        accessor.setName(name);
        accessor.setNullable(nullable);
        accessor.setReferencedColumnName(getReferencedColumnName());
        accessor.setTable(table);
        accessor.setUnique(unique);
        accessor.setUpdatable(updatable);
        if (ForeignKeyValidator.isNotEmpty(foreignKey)) {
            accessor.setForeignKey(foreignKey.getAccessor());
        }
        return accessor;
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

}
