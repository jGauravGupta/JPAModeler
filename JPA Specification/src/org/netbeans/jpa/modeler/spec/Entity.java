//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package org.netbeans.jpa.modeler.spec;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.internal.jpa.metadata.accessors.classes.EntityAccessor;
import org.eclipse.persistence.internal.jpa.metadata.accessors.classes.XMLAttributes;
import org.netbeans.jpa.modeler.spec.extend.AccessTypeHandler;
import org.netbeans.jpa.modeler.spec.extend.AssociationOverrideHandler;
import org.netbeans.jpa.modeler.spec.extend.AttributeOverrideHandler;
import org.netbeans.jpa.modeler.spec.extend.IAttributes;
import org.netbeans.jpa.modeler.spec.extend.InheritenceHandler;
import org.netbeans.jpa.modeler.spec.extend.JavaClass;
import org.netbeans.jpa.source.JavaSourceParserUtil;
import org.netbeans.modeler.core.NBModelerUtil;

/**
 *
 *
 * Defines the settings and mappings for an entity. Is allowed to be sparsely
 * populated and used in conjunction with the annotations. Alternatively, the
 * metadata-complete attribute can be used to indicate that no annotations on
 * the entity class (and its fields or properties) are to be processed. If this
 * is the case then the defaulting rules for the entity and its subelements will
 * be recursively applied.
 *
 * @Target(TYPE) @Retention(RUNTIME) public @interface Entity { String name()
 * default ""; }
 *
 *
 *
 * <p>
 * Java class for entity complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="entity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="table" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}table" minOccurs="0"/>
 *         &lt;element name="secondary-table" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}secondary-table" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;sequence>
 *           &lt;element name="primary-key-join-column" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}primary-key-join-column" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="primary-key-foreign-key" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}foreign-key" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element name="id-class" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}id-class" minOccurs="0"/>
 *         &lt;element name="inheritance" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}inheritance" minOccurs="0"/>
 *         &lt;element name="discriminator-value" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}discriminator-value" minOccurs="0"/>
 *         &lt;element name="discriminator-column" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}discriminator-column" minOccurs="0"/>
 *         &lt;element name="sequence-generator" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}sequence-generator" minOccurs="0"/>
 *         &lt;element name="table-generator" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}table-generator" minOccurs="0"/>
 *         &lt;element name="named-query" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-query" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="named-native-query" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-native-query" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="named-stored-procedure-query" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-stored-procedure-query" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sql-result-set-mapping" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}sql-result-set-mapping" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="exclude-default-listeners" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="exclude-superclass-listeners" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="entity-listeners" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}entity-listeners" minOccurs="0"/>
 *         &lt;element name="pre-persist" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}pre-persist" minOccurs="0"/>
 *         &lt;element name="post-persist" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}post-persist" minOccurs="0"/>
 *         &lt;element name="pre-remove" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}pre-remove" minOccurs="0"/>
 *         &lt;element name="post-remove" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}post-remove" minOccurs="0"/>
 *         &lt;element name="pre-update" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}pre-update" minOccurs="0"/>
 *         &lt;element name="post-update" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}post-update" minOccurs="0"/>
 *         &lt;element name="post-load" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}post-load" minOccurs="0"/>
 *         &lt;element name="attribute-override" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}attribute-override" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="association-override" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}association-override" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="convert" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}convert" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="named-entity-graph" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-entity-graph" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}attributes" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="access" type="{http://java.sun.com/xml/ns/persistence/orm}access-type" />
 *       &lt;attribute name="cacheable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="metadata-complete" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entity", propOrder = {
    //    "description",
    "table",
    "secondaryTable",
    "primaryKeyJoinColumn",
    "primaryKeyForeignKey",
    //    "idClass",
    "inheritance",
    "discriminatorValue",
    "discriminatorColumn",
    "sequenceGenerator",
    "tableGenerator",
    "namedStoredProcedureQuery",
    //    "namedQuery",
    //    "namedNativeQuery",
    //    "sqlResultSetMapping",
    //    "excludeDefaultListeners",
    //    "excludeSuperclassListeners",
    //    "entityListeners",
    //    "prePersist",
    //    "postPersist",
    //    "preRemove",
    //    "postRemove",
    //    "preUpdate",
    //    "postUpdate",
    //    "postLoad",
    "attributeOverride",
    "associationOverride",
    "convert",
    "namedEntityGraph"
//    "attributes",
//    "interfaces"
})
public class Entity extends IdentifiableClass implements AccessTypeHandler, InheritenceHandler, AttributeOverrideHandler, AssociationOverrideHandler {

    protected Table table;
    @XmlElement(name = "secondary-table")
    protected List<SecondaryTable> secondaryTable;//REVENG PENDING
    @XmlElement(name = "primary-key-join-column")
    protected List<PrimaryKeyJoinColumn> primaryKeyJoinColumn;//REVENG PENDING
    @XmlElement(name = "primary-key-foreign-key")
    protected ForeignKey primaryKeyForeignKey;//REVENG PENDING JPA 2.1

    protected Inheritance inheritance;
    @XmlElement(name = "discriminator-value")
    protected String discriminatorValue;
    @XmlElement(name = "discriminator-column")
    protected DiscriminatorColumn discriminatorColumn;
    @XmlElement(name = "sequence-generator")
    protected SequenceGenerator sequenceGenerator;
    @XmlElement(name = "table-generator")
    protected TableGenerator tableGenerator;

    @XmlElement(name = "nspq")//(name = "named-stored-procedure-query")
    protected List<NamedStoredProcedureQuery> namedStoredProcedureQuery;
    @XmlElement(name = "attribute-override")
    protected List<AttributeOverride> attributeOverride;
    @XmlElement(name = "association-override")
    protected List<AssociationOverride> associationOverride;
    protected List<Convert> convert;//REVENG PENDING JPA 2.1
    @XmlElement(name = "neg")//(name = "named-entity-graph")
    protected List<NamedEntityGraph> namedEntityGraph;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected Boolean cacheable;//REVENG PENDING

    @Override
    public void load(EntityMappings entityMappings, TypeElement element, boolean fieldAccess) {
        super.load(entityMappings, element, fieldAccess);
        AnnotationMirror annotationMirror = JavaSourceParserUtil.getAnnotation(element, "javax.persistence.Entity");

        TypeElement superClassElement = JavaSourceParserUtil.getSuperclassTypeElement(element);
        if (!superClassElement.getQualifiedName().toString().equals("java.lang.Object")) {
            if (JavaSourceParserUtil.isEntityClass(superClassElement)) {
                org.netbeans.jpa.modeler.spec.Entity entitySuperclassSpec = entityMappings.findEntity(superClassElement.getSimpleName().toString());
                if (entitySuperclassSpec == null) {
                    entitySuperclassSpec = new org.netbeans.jpa.modeler.spec.Entity();
                    entitySuperclassSpec.load(entityMappings, superClassElement, fieldAccess);
                    entityMappings.addEntity(entitySuperclassSpec);
                }
                super.addSuperclass(entitySuperclassSpec);
            } else if (JavaSourceParserUtil.isMappedSuperClass(superClassElement)) {
                org.netbeans.jpa.modeler.spec.MappedSuperclass mappedSuperclassSpec = entityMappings.findMappedSuperclass(superClassElement.getSimpleName().toString());
                if (mappedSuperclassSpec == null) {
                    mappedSuperclassSpec = new org.netbeans.jpa.modeler.spec.MappedSuperclass();
                    mappedSuperclassSpec.load(entityMappings, superClassElement, fieldAccess);
                    entityMappings.addMappedSuperclass(mappedSuperclassSpec);
                }
                super.addSuperclass(mappedSuperclassSpec);
            } else {
                //Skip
            }
        }
        this.table = Table.load(element);
        this.inheritance = Inheritance.load(element);
        AnnotationMirror annotDiscrValue = JavaSourceParserUtil.findAnnotation(element, "javax.persistence.DiscriminatorValue");
        if (annotDiscrValue != null) {
            Object value = JavaSourceParserUtil.findAnnotationValue(annotationMirror, "value");
            if (value != null) {
                discriminatorValue = value.toString();
            }
        }
        this.discriminatorColumn = DiscriminatorColumn.load(element);
        this.tableGenerator = TableGenerator.load(element);
        this.sequenceGenerator = SequenceGenerator.load(element);

        if (annotationMirror != null) {
            this.name = (String) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "name");
        }

        this.getAttributeOverride().addAll(AttributeOverride.load(element));
        this.getAssociationOverride().addAll(AssociationOverride.load(element));
        this.getNamedEntityGraph().addAll(NamedEntityGraph.load(element));
        this.getNamedStoredProcedureQuery().addAll(NamedStoredProcedureQuery.load(element));

    }

    void beforeMarshal(Marshaller marshaller) {
//        if (NBModelerUtil.isEmptyObject(table)) {
        if(table!=null && table.isEmpty()){
            table = null;
        }
    }

    /**
     * Gets the value of the table property.
     *
     * @return possible object is {@link Table }
     *
     */
    public Table getTable() {
        if (table == null) {
            table = new Table();
        }
        return table;
    }

    /**
     * Sets the value of the table property.
     *
     * @param value allowed object is {@link Table }
     *
     */
    public void setTable(Table value) {
        this.table = value;
    }

    /**
     * Gets the value of the secondaryTable property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the secondaryTable property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecondaryTable().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SecondaryTable }
     *
     *
     */
    public List<SecondaryTable> getSecondaryTable() {
        if (secondaryTable == null) {
            secondaryTable = new ArrayList<SecondaryTable>();
        }
        return this.secondaryTable;
    }

    /**
     * Gets the value of the primaryKeyJoinColumn property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the primaryKeyJoinColumn property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrimaryKeyJoinColumn().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrimaryKeyJoinColumn }
     *
     *
     */
    public List<PrimaryKeyJoinColumn> getPrimaryKeyJoinColumn() {
        if (primaryKeyJoinColumn == null) {
            primaryKeyJoinColumn = new ArrayList<PrimaryKeyJoinColumn>();
        }
        return this.primaryKeyJoinColumn;
    }

    /**
     * Gets the value of the primaryKeyForeignKey property.
     *
     * @return possible object is {@link ForeignKey }
     *
     */
    public ForeignKey getPrimaryKeyForeignKey() {
        return primaryKeyForeignKey;
    }

    /**
     * Sets the value of the primaryKeyForeignKey property.
     *
     * @param value allowed object is {@link ForeignKey }
     *
     */
    public void setPrimaryKeyForeignKey(ForeignKey value) {
        this.primaryKeyForeignKey = value;
    }

    /**
     * Gets the value of the namedStoredProcedureQuery property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the namedStoredProcedureQuery property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedStoredProcedureQuery().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedStoredProcedureQuery }
     *
     *
     */
    public List<NamedStoredProcedureQuery> getNamedStoredProcedureQuery() {
        if (namedStoredProcedureQuery == null) {
            namedStoredProcedureQuery = new ArrayList<NamedStoredProcedureQuery>();
        }
        return this.namedStoredProcedureQuery;
    }

    /**
     * Gets the value of the inheritance property.
     *
     * @return possible object is {@link Inheritance }
     *
     */
    @Override
    public Inheritance getInheritance() {
        return inheritance;
    }

    /**
     * Sets the value of the inheritance property.
     *
     * @param value allowed object is {@link Inheritance }
     *
     */
    @Override
    public void setInheritance(Inheritance value) {
        this.inheritance = value;
    }

    /**
     * Gets the value of the discriminatorValue property.
     *
     * @return possible object is {@link String }
     *
     */
    @Override
    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

    /**
     * Sets the value of the discriminatorValue property.
     *
     * @param value allowed object is {@link String }
     *
     */
    @Override
    public void setDiscriminatorValue(String value) {
        this.discriminatorValue = value;
    }

    /**
     * Gets the value of the discriminatorColumn property.
     *
     * @return possible object is {@link DiscriminatorColumn }
     *
     */
    @Override
    public DiscriminatorColumn getDiscriminatorColumn() {
        return discriminatorColumn;
    }

    /**
     * Sets the value of the discriminatorColumn property.
     *
     * @param value allowed object is {@link DiscriminatorColumn }
     *
     */
    @Override
    public void setDiscriminatorColumn(DiscriminatorColumn value) {
        this.discriminatorColumn = value;
    }

    /**
     * Gets the value of the sequenceGenerator property.
     *
     * @return possible object is {@link SequenceGenerator }
     *
     */
    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    /**
     * Sets the value of the sequenceGenerator property.
     *
     * @param value allowed object is {@link SequenceGenerator }
     *
     */
    public void setSequenceGenerator(SequenceGenerator value) {
        this.sequenceGenerator = value;
    }

    /**
     * Gets the value of the tableGenerator property.
     *
     * @return possible object is {@link TableGenerator }
     *
     */
    public TableGenerator getTableGenerator() {
        return tableGenerator;
    }

    /**
     * Sets the value of the tableGenerator property.
     *
     * @param value allowed object is {@link TableGenerator }
     *
     */
    public void setTableGenerator(TableGenerator value) {
        this.tableGenerator = value;
    }

    /**
     * Gets the value of the attributeOverride property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the attributeOverride property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeOverride().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeOverride }
     *
     *
     */
    @Override
    public List<AttributeOverride> getAttributeOverride() {
        if (attributeOverride == null) {
            attributeOverride = new ArrayList<AttributeOverride>();
        }
        return this.attributeOverride;
    }

    /**
     * Gets the value of the associationOverride property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the associationOverride property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssociationOverride().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssociationOverride }
     *
     *
     */
    @Override
    public List<AssociationOverride> getAssociationOverride() {
        if (associationOverride == null) {
            associationOverride = new ArrayList<AssociationOverride>();
        }
        return this.associationOverride;
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
        this.name = value;
    }

    /**
     * Gets the value of the cacheable property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getCacheable() {//isCacheable()
        return cacheable;
    }

    /**
     * Sets the value of the cacheable property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setCacheable(Boolean value) {
        this.cacheable = value;
    }

    @Override
    public void setAttributes(IAttributes attributes) {
        this.attributes = (Attributes) attributes;
    }

    @Override
    public String toString() {
        return "Entity{" + "description=" + description + ", table=" + table + ", secondaryTable=" + secondaryTable + ", primaryKeyJoinColumn=" + primaryKeyJoinColumn + ", idClass=" + idClass + ", inheritance=" + inheritance + ", discriminatorValue=" + discriminatorValue + ", discriminatorColumn=" + discriminatorColumn + ", sequenceGenerator=" + sequenceGenerator + ", tableGenerator=" + tableGenerator + ", namedQuery=" + namedQuery + ", namedNativeQuery=" + namedNativeQuery + ", sqlResultSetMapping=" + sqlResultSetMapping + ", excludeDefaultListeners=" + excludeDefaultListeners + ", excludeSuperclassListeners=" + excludeSuperclassListeners + ", entityListeners=" + entityListeners + ", prePersist=" + prePersist + ", postPersist=" + postPersist + ", preRemove=" + preRemove + ", postRemove=" + postRemove + ", preUpdate=" + preUpdate + ", postUpdate=" + postUpdate + ", postLoad=" + postLoad + ", attributeOverride=" + attributeOverride + ", associationOverride=" + associationOverride + ", attributes=" + attributes + ", name=" + name + ", clazz=" + clazz + ", access=" + access + ", cacheable=" + cacheable + ", metadataComplete=" + metadataComplete + '}';
    }

    @Override
    public AttributeOverride getAttributeOverride(String attributePath) {
        List<AttributeOverride> attributeOverrides = getAttributeOverride();
        for (AttributeOverride attributeOverride_TMP : attributeOverrides) {
            if (attributeOverride_TMP.getName().equals(attributePath)) {
                return attributeOverride_TMP;
            }
        }
        AttributeOverride attributeOverride_TMP = new AttributeOverride();
        attributeOverride_TMP.setName(attributePath);
        attributeOverrides.add(attributeOverride_TMP);
        return attributeOverride_TMP;
    }

    @Override
    public AssociationOverride getAssociationOverride(String attributePath) {
        List<AssociationOverride> associationOverrides = getAssociationOverride();
        for (AssociationOverride associationOverride_TMP : associationOverrides) {
            if (associationOverride_TMP.getName().equals(attributePath)) {
                return associationOverride_TMP;
            }
        }
        AssociationOverride attributeOverride_TMP = new AssociationOverride();
        attributeOverride_TMP.setName(attributePath);
        associationOverrides.add(attributeOverride_TMP);
        return attributeOverride_TMP;
    }

    /**
     * Sets the value of the clazz property.
     *
     * @param value allowed object is {@link String }
     *
     */
    @Override
    public void setClazz(String value) {
//        this.clazz = value;
        notifyListeners("clazz", this.clazz, this.clazz = value);
    }

    /**
     * Gets the value of the convert property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the convert property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConvert().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Convert }
     *
     *
     */
    public List<Convert> getConvert() {
        if (convert == null) {
            convert = new ArrayList<Convert>();
        }
        return this.convert;
    }

    /**
     * Gets the value of the namedEntityGraph property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the namedEntityGraph property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedEntityGraph().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedEntityGraph }
     *
     *
     * @return 
     */
    public List<NamedEntityGraph> getNamedEntityGraph() {
        if (namedEntityGraph == null) {
            namedEntityGraph = new ArrayList<>();
        }
        return this.namedEntityGraph;
    }


    public String getTableName() {
        if (table != null && StringUtils.isNotBlank(table.getName())) {
            return table.getName();
        } else {
            return clazz;
        }
    }


}
