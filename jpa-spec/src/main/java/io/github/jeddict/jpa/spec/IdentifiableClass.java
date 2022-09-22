/**
 * Copyright 2013-2019 the original author or authors from the Jeddict project (https://jeddict.github.io/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.jeddict.jpa.spec;

import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import static io.github.jeddict.jcode.JPAConstants.ENTITY_FQN;
import static io.github.jeddict.jcode.JPAConstants.MAPPED_SUPERCLASS_FQN;
import static io.github.jeddict.jpa.spec.NamedQuery.FIND_BY;
import io.github.jeddict.jpa.spec.extend.Attribute;
import io.github.jeddict.jpa.spec.extend.CompositePrimaryKeyType;
import static io.github.jeddict.jpa.spec.extend.CompositePrimaryKeyType.EMBEDDEDID;
import static io.github.jeddict.jpa.spec.extend.CompositePrimaryKeyType.IDCLASS;
import io.github.jeddict.jpa.spec.extend.IPrimaryKeyAttributes;
import io.github.jeddict.jpa.spec.extend.PrimaryKeyContainer;
import io.github.jeddict.jpa.spec.extend.ReferenceClass;
import io.github.jeddict.jpa.spec.extend.SingleRelationAttribute;
import io.github.jeddict.jpa.spec.validation.adapter.CompositePrimaryKeyAdapter;
import io.github.jeddict.settings.diagram.ClassDiagramSettings;
import io.github.jeddict.source.AnnotationExplorer;
import io.github.jeddict.source.ClassExplorer;
import io.github.jeddict.source.MemberExplorer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public abstract class IdentifiableClass extends ManagedClass<IPrimaryKeyAttributes> implements PrimaryKeyContainer {

    @XmlTransient
    protected IdClass idClass;
    protected PrimaryKeyAttributes attributes;

    @XmlElement(name = "nq")//(name = "named-query")
    protected List<NamedQuery> namedQuery;
    @XmlElement(name = "nnq")//(name = "named-native-query")
    protected List<NamedNativeQuery> namedNativeQuery;
    @XmlElement(name = "srsm")//(name = "sql-result-set-mapping")
    protected Set<SqlResultSetMapping> sqlResultSetMapping;
    @XmlElement(name = "nspq")
    protected List<NamedStoredProcedureQuery> namedStoredProcedureQuery;

    @XmlElement(name = "edl")//(name = "exclude-default-listeners")
    protected EmptyType excludeDefaultListeners;
    @XmlElement(name = "esl")//(name = "exclude-superclass-listeners")
    protected EmptyType excludeSuperclassListeners;
    @XmlElement(name = "el")//(name = "entity-listeners")
    protected EntityListeners entityListeners;
    @XmlElement(name = "sp")//(name = "pre-persist")
    protected PrePersist prePersist;//REVENG PENDING
    @XmlElement(name = "ep")//(name = "post-persist")
    protected PostPersist postPersist;//REVENG PENDING
    @XmlElement(name = "sr")//(name = "pre-remove")
    protected PreRemove preRemove;//REVENG PENDING
    @XmlElement(name = "er")//(name = "post-remove")
    protected PostRemove postRemove;//REVENG PENDING
    @XmlElement(name = "su")//(name = "pre-update")
    protected PreUpdate preUpdate;//REVENG PENDING
    @XmlElement(name = "eu")//(name = "post-update")
    protected PostUpdate postUpdate;//REVENG PENDING
    @XmlElement(name = "el")//(name = "post-load")
    protected PostLoad postLoad;//REVENG PENDING

    @XmlAttribute
    @XmlJavaTypeAdapter(CompositePrimaryKeyAdapter.class)
    private CompositePrimaryKeyType compositePrimaryKeyType;//custom added
    @XmlAttribute
    private String compositePrimaryKeyClass;//custom added

    @Override
    public void load(ClassExplorer clazz) {
        super.load(clazz);
        this.idClass = IdClass.load(clazz);

        if (this.getAttributes().getEmbeddedId() != null) {
            String embeddedIdName = this.getAttributes().getEmbeddedId().getName();
            Optional<MemberExplorer> memeber = clazz.getMembers()
                    .stream()
                    .filter(member -> member.getFieldName().equals(embeddedIdName))
                    .findAny();
            if (memeber.isPresent()) {
                this.setCompositePrimaryKeyClass(memeber.get().getSimpleType());
                this.setCompositePrimaryKeyType(EMBEDDEDID);
            }
        } else if (idClass != null) {
            this.setCompositePrimaryKeyClass(this.getIdClass().getClazz());
            this.setCompositePrimaryKeyType(IDCLASS);
        } else {
            this.setCompositePrimaryKeyClass(null);
            this.setCompositePrimaryKeyType(null);
        }

        Optional<AnnotationExplorer> entityListenersOpt = clazz.getAnnotation(jakarta.persistence.EntityListeners.class);
        if (entityListenersOpt.isPresent()) {
            this.entityListeners = EntityListeners.load(entityListenersOpt.get());
        }

        Optional<AnnotationExplorer> excludeDefaultListenersOpt = clazz.getAnnotation(jakarta.persistence.ExcludeDefaultListeners.class);
        if (excludeDefaultListenersOpt.isPresent()) {
            this.excludeDefaultListeners = new EmptyType();
        }

        Optional<AnnotationExplorer> excludeSuperclassListenersOpt = clazz.getAnnotation(jakarta.persistence.ExcludeSuperclassListeners.class);
        if (excludeSuperclassListenersOpt.isPresent()) {
            this.excludeSuperclassListeners = new EmptyType();
        }

        this.namedQuery = NamedQuery.load(clazz);
        this.namedNativeQuery = NamedNativeQuery.load(clazz);
        this.sqlResultSetMapping = SqlResultSetMapping.load(clazz);
        this.namedStoredProcedureQuery = NamedStoredProcedureQuery.load(clazz);

        Optional<ResolvedReferenceTypeDeclaration> superClassTypeOpt = clazz.getSuperClass();
        if (superClassTypeOpt.isPresent()) {
            ResolvedReferenceTypeDeclaration superClassType = superClassTypeOpt.get();

            if (superClassType.hasDirectlyAnnotation(ENTITY_FQN)) {
                Optional<Entity> superClassOpt = clazz.getSource().findEntity(superClassType);
                if (superClassOpt.isPresent()) {
                    super.addSuperclass(superClassOpt.get());
                }
            } else if (superClassType.hasDirectlyAnnotation(MAPPED_SUPERCLASS_FQN)) {
                Optional<MappedSuperclass> superClassOpt = clazz.getSource().findMappedSuperclass(superClassType);
                if (superClassOpt.isPresent()) {
                    super.addSuperclass(superClassOpt.get());
                }
            } else {
                this.setSuperclassRef(new ReferenceClass(superClassType.getQualifiedName()));
            }
        }
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    @Override
    public String getName() {
        return getClazz();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    @Override
    public void setName(String name) {
        setClazz(name);
    }

    /**
     * Gets the value of the idClass property.
     *
     * @return possible object is {@link IdClass }
     *
     */
    @Override
    public IdClass getIdClass() {
        manageCompositePrimaryKey();
        return idClass;
    }

    /**
     * Sets the value of the idClass property.
     *
     * @param value allowed object is {@link IdClass }
     *
     */
    @Override
    public void setIdClass(IdClass value) {
        this.idClass = value;
    }

    /**
     * Gets the value of the attributes property.
     *
     * @return possible object is {@link PrimaryKeyAttributes }
     *
     */
    @Override
    public IPrimaryKeyAttributes getAttributes() {
        if (attributes == null) {
            attributes = new PrimaryKeyAttributes();
            attributes.setJavaClass(this);
        }
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     *
     * @param attributes allowed object is {@link PrimaryKeyAttributes }
     *
     */
    @Override
    public void setAttributes(IPrimaryKeyAttributes attributes) {
        if (attributes instanceof PrimaryKeyAttributes) {
            this.attributes = (PrimaryKeyAttributes) attributes;
        }
    }

    /**
     * Gets the value of the excludeDefaultListeners property.
     *
     * @return possible object is {@link EmptyType }
     *
     */
    public EmptyType getExcludeDefaultListeners() {
        return excludeDefaultListeners;
    }

    /**
     * Sets the value of the excludeDefaultListeners property.
     *
     * @param value allowed object is {@link EmptyType }
     *
     */
    public void setExcludeDefaultListeners(EmptyType value) {
        this.excludeDefaultListeners = value;
    }

    /**
     * Gets the value of the excludeSuperclassListeners property.
     *
     * @return possible object is {@link EmptyType }
     *
     */
    public EmptyType getExcludeSuperclassListeners() {
        return excludeSuperclassListeners;
    }

    /**
     * Sets the value of the excludeSuperclassListeners property.
     *
     * @param value allowed object is {@link EmptyType }
     *
     */
    public void setExcludeSuperclassListeners(EmptyType value) {
        this.excludeSuperclassListeners = value;
    }

    /**
     * Gets the value of the entityListeners property.
     *
     * @return possible object is {@link EntityListeners }
     *
     */
    public EntityListeners getEntityListeners() {
        if (entityListeners == null) {
            entityListeners = new EntityListeners();
        }
        return entityListeners;
    }

    /**
     * Sets the value of the entityListeners property.
     *
     * @param value allowed object is {@link EntityListeners }
     *
     */
    public void setEntityListeners(EntityListeners value) {
        this.entityListeners = value;
    }

    /**
     * Gets the value of the prePersist property.
     *
     * @return possible object is {@link PrePersist }
     *
     */
    public PrePersist getPrePersist() {
        return prePersist;
    }

    /**
     * Sets the value of the prePersist property.
     *
     * @param value allowed object is {@link PrePersist }
     *
     */
    public void setPrePersist(PrePersist value) {
        this.prePersist = value;
    }

    /**
     * Gets the value of the postPersist property.
     *
     * @return possible object is {@link PostPersist }
     *
     */
    public PostPersist getPostPersist() {
        return postPersist;
    }

    /**
     * Sets the value of the postPersist property.
     *
     * @param value allowed object is {@link PostPersist }
     *
     */
    public void setPostPersist(PostPersist value) {
        this.postPersist = value;
    }

    /**
     * Gets the value of the preRemove property.
     *
     * @return possible object is {@link PreRemove }
     *
     */
    public PreRemove getPreRemove() {
        return preRemove;
    }

    /**
     * Sets the value of the preRemove property.
     *
     * @param value allowed object is {@link PreRemove }
     *
     */
    public void setPreRemove(PreRemove value) {
        this.preRemove = value;
    }

    /**
     * Gets the value of the postRemove property.
     *
     * @return possible object is {@link PostRemove }
     *
     */
    public PostRemove getPostRemove() {
        return postRemove;
    }

    /**
     * Sets the value of the postRemove property.
     *
     * @param value allowed object is {@link PostRemove }
     *
     */
    public void setPostRemove(PostRemove value) {
        this.postRemove = value;
    }

    /**
     * Gets the value of the preUpdate property.
     *
     * @return possible object is {@link PreUpdate }
     *
     */
    public PreUpdate getPreUpdate() {
        return preUpdate;
    }

    /**
     * Sets the value of the preUpdate property.
     *
     * @param value allowed object is {@link PreUpdate }
     *
     */
    public void setPreUpdate(PreUpdate value) {
        this.preUpdate = value;
    }

    /**
     * Gets the value of the postUpdate property.
     *
     * @return possible object is {@link PostUpdate }
     *
     */
    public PostUpdate getPostUpdate() {
        return postUpdate;
    }

    /**
     * Sets the value of the postUpdate property.
     *
     * @param value allowed object is {@link PostUpdate }
     *
     */
    public void setPostUpdate(PostUpdate value) {
        this.postUpdate = value;
    }

    /**
     * Gets the value of the postLoad property.
     *
     * @return possible object is {@link PostLoad }
     *
     */
    public PostLoad getPostLoad() {
        return postLoad;
    }

    /**
     * Sets the value of the postLoad property.
     *
     * @param value allowed object is {@link PostLoad }
     *
     */
    public void setPostLoad(PostLoad value) {
        this.postLoad = value;
    }

    /**
     * Gets the value of the namedQuery property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the namedQuery property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedQuery().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedQuery }
     *
     *
     */
    public List<NamedQuery> getNamedQuery() {
        if (namedQuery == null) {
            namedQuery = new ArrayList<>();
        }
        return this.namedQuery;
    }

    public void addNamedQuery(NamedQuery namedQuery) {
        if (namedQuery != null) {
            this.getNamedQuery().add(namedQuery);
        }
    }

    public void removeNamedQuery(NamedQuery namedQuery) {
        if (namedQuery != null) {
            this.getNamedQuery().remove(namedQuery);
        }
    }

    public Optional<NamedQuery> findNamedQuery(Attribute attribute) {
        return this.getNamedQuery().stream().filter(q -> q.getName().equalsIgnoreCase(this.getClazz() + '.' + FIND_BY + attribute.getName())).findAny();
    }

    /**
     * Gets the value of the namedNativeQuery property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the namedNativeQuery property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedNativeQuery().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedNativeQuery }
     *
     *
     */
    public List<NamedNativeQuery> getNamedNativeQuery() {
        if (namedNativeQuery == null) {
            namedNativeQuery = new ArrayList<>();
        }
        return this.namedNativeQuery;
    }

    /**
     * Gets the value of the sqlResultSetMapping property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the sqlResultSetMapping property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSqlResultSetMapping().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SqlResultSetMapping }
     *
     *
     */
    public Set<SqlResultSetMapping> getSqlResultSetMapping() {
        if (sqlResultSetMapping == null) {
            sqlResultSetMapping = new HashSet<>();
        }
        return this.sqlResultSetMapping;
    }

    /**
     * @return the compositePrimaryKeyType
     */
    @Override
    public CompositePrimaryKeyType getCompositePrimaryKeyType() {
        return compositePrimaryKeyType;
    }

    @Override
    public boolean isIdClassType() {
        return compositePrimaryKeyType == CompositePrimaryKeyType.IDCLASS
                || (compositePrimaryKeyType == CompositePrimaryKeyType.DEFAULT && !ClassDiagramSettings.isEmbeddedIdDefaultType());
    }

    @Override
    public boolean isEmbeddedIdType() {
        return compositePrimaryKeyType == CompositePrimaryKeyType.EMBEDDEDID
                || (compositePrimaryKeyType == CompositePrimaryKeyType.DEFAULT && ClassDiagramSettings.isEmbeddedIdDefaultType());
    }

    /**
     * @param compositePrimaryKeyType the compositePrimaryKeyType to set
     */
    @Override
    public void setCompositePrimaryKeyType(CompositePrimaryKeyType compositePrimaryKeyType) {
        this.compositePrimaryKeyType = compositePrimaryKeyType;
    }

    /**
     * @return the compositePrimaryKeyClass
     */
    @Override
    public String getCompositePrimaryKeyClass() {
        return compositePrimaryKeyClass;
    }

    /**
     * @param compositePrimaryKeyClass the compositePrimaryKeyClass to set
     */
    @Override
    public void setCompositePrimaryKeyClass(String compositePrimaryKeyClass) {
        this.compositePrimaryKeyClass = compositePrimaryKeyClass;
    }

    private void manageCompositePrimaryKeyClass() {
        if (compositePrimaryKeyClass == null || compositePrimaryKeyClass.trim().isEmpty()) {
            compositePrimaryKeyClass = this.getClazz() + "PK";
        }
    }

    private void manageCompositePrimaryKeyType() {
        if (null != compositePrimaryKeyType) {
            CompositePrimaryKeyType type = compositePrimaryKeyType == CompositePrimaryKeyType.DEFAULT ? 
                    (ClassDiagramSettings.isEmbeddedIdDefaultType() ? CompositePrimaryKeyType.EMBEDDEDID : CompositePrimaryKeyType.IDCLASS) : 
                    compositePrimaryKeyType;
            switch (type) {
                case EMBEDDEDID:
                    this.idClass = null;
                    break;
                case IDCLASS:
                    if (this.idClass != null) {
                        this.idClass.setClazz(compositePrimaryKeyClass);
                    } else {
                        this.idClass = new IdClass(compositePrimaryKeyClass);
                    }
                    break;
                default:
                    this.idClass = null;
                    compositePrimaryKeyClass = null;
            }
        }
    }

    @Override
    public void manageCompositePrimaryKey() {
        manageCompositePrimaryKeyClass();
        manageCompositePrimaryKeyType();
    }

    @Override
    public void clearCompositePrimaryKey() {
        this.idClass = null;
        this.compositePrimaryKeyClass = null;
        this.compositePrimaryKeyType = null;
    }

    public DefaultClass getDefaultClass() {
        EntityMappings entityMappings = this.getRootElement();
        DefaultClass _class = entityMappings.addDefaultClass(this.getPackage(), this.getCompositePrimaryKeyClass());
        List<Id> idAttributes = null;
        if (this.isEmbeddedIdType()) {
            idAttributes = this.getAttributes().getId();
            _class.setEmbeddable(true);
        } else if (this.isIdClassType()) {
            idAttributes = this.getAttributes().getSuperId();
        }

        for (Id idSpec : idAttributes) {
            DefaultAttribute attribute = new DefaultAttribute(idSpec);
            attribute.setAttributeType(idSpec.getAttributeType());
            attribute.setName(idSpec.getName());
            _class.getAttributes().addDefaultAttribute(attribute);
        }

        for (SingleRelationAttribute relationAttributeSpec : this.getAttributes().getDerivedRelationAttributes()) {
            Entity targetEntitySpec = relationAttributeSpec.getConnectedEntity();
            List<Attribute> targetPrimaryAttributes = targetEntitySpec.getAttributes().getPrimaryKeyAttributes();
            DefaultAttribute attribute = new DefaultAttribute(relationAttributeSpec);
            if (targetPrimaryAttributes.size() == 1) {
                if (targetPrimaryAttributes.get(0) instanceof Id) { //if only @Id exist
                    Id idSpec = (Id) targetPrimaryAttributes.get(0);
                    attribute.setAttributeType(idSpec.getAttributeType());
                    attribute.setName(relationAttributeSpec.getName());// matches name of @Id Relation attribute
                } else {// if only @Id @Relation exist
                    //never execute , handled by above AUTO_CLASS condition
                    throw new IllegalStateException("Handled by Auto Class case");
                }
            } else {// if @Id and @Id @Relation exist
                attribute.setAttributeType(targetEntitySpec.getRootIdentifiableClass().getCompositePrimaryKeyClass());
                attribute.setName(relationAttributeSpec.getName());// matches name of @Id Relation attribute//PK
                attribute.setDerived(true);
            }
            _class.getAttributes().addDefaultAttribute(attribute);
            //Start : if dependent class is Embedded that add @MapsId to Derived PK
            if (this.isIdClassType()) {
                if (relationAttributeSpec instanceof OneToOne) {
                    ((OneToOne) relationAttributeSpec).setMapsId(null);
                } else if (relationAttributeSpec instanceof ManyToOne) {
                    ((ManyToOne) relationAttributeSpec).setMapsId(null);
                }
            } else if (this.isEmbeddedIdType()) {
                if (relationAttributeSpec instanceof OneToOne) {
                    ((OneToOne) relationAttributeSpec).setMapsId(attribute.getName());
                } else if (relationAttributeSpec instanceof ManyToOne) {
                    ((ManyToOne) relationAttributeSpec).setMapsId(attribute.getName());
                }
            }
            //End : if dependent class is Embedded that add @MapsId to Derived PK

        }
        return _class;
    }

    public IdentifiableClass getRootIdentifiableClass() {
        if (this.getSuperclass() != null && this.getSuperclass() instanceof IdentifiableClass) {
            return (IdentifiableClass) this.getSuperclass();
        } else {
            return this;
        }
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
     * @return 
     */
    public List<NamedStoredProcedureQuery> getNamedStoredProcedureQuery() {
        if (namedStoredProcedureQuery == null) {
            namedStoredProcedureQuery = new ArrayList<>();
        }
        return this.namedStoredProcedureQuery;
    }

}
