//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import io.github.jeddict.jpa.spec.extend.SingleRelationAttribute;
import io.github.jeddict.source.AnnotationExplorer;
import io.github.jeddict.source.MemberExplorer;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import io.github.jeddict.util.StringUtils;

/**
 *
 *
 * @Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface OneToOne {
 * Class targetEntity() default void.class; CascadeType[] cascade() default {};
 * FetchType fetch() default EAGER; boolean optional() default true; String
 * mappedBy() default ""; boolean orphanRemoval() default false; }
 *
 *
 *
 * <p>
 * Java class for one-to-one complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="one-to-one">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element name="primary-key-join-column" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}primary-key-join-column" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;element name="primary-key-foreign-key" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}foreign-key" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="join-column" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}join-column" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;element name="foreign-key" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}foreign-key" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;element name="join-table" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}join-table" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="cascade" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}cascade-type" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="target-entity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fetch" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}fetch-type" />
 *       &lt;attribute name="optional" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="access" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}access-type" />
 *       &lt;attribute name="mapped-by" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orphan-removal" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="maps-id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "one-to-one")
@XmlRootElement
public class OneToOne extends SingleRelationAttribute {

    @XmlAttribute(name = "own")
    private Boolean owner;//default true/null
    @XmlTransient//(name = "mapped-by")
    protected String mappedBy;
    @XmlAttribute(name = "orp")
    protected Boolean orphanRemoval;

    public void load(MemberExplorer member) {
        AnnotationExplorer annotation = member.getAnnotation(jakarta.persistence.OneToOne.class).get();
        super.loadAttribute(member, annotation);
        annotation.getString("mappedBy").ifPresent(this::setMappedBy);
        annotation.getBoolean("orphanRemoval").ifPresent(this::setOrphanRemoval);
    }

    /**
     * Gets the value of the mappedBy property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMappedBy() {
        if (Boolean.FALSE.equals(isOwner())) {
            if (mappedBy != null) {
                return mappedBy;
            }
            if (getConnectedAttribute() != null) {
                return getConnectedAttribute().getName();
            }
        }
        return null;
    }

    /**
     * Sets the value of the mappedBy property.
     *
     * @param value allowed object is {@link String }
     *
     */
      public void setMappedBy(String value) {
        this.mappedBy = value;
        this.owner =  StringUtils.isBlank(mappedBy);
    }

    /**
     * Gets the value of the orphanRemoval property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getOrphanRemoval() {
        if(orphanRemoval == null){
            orphanRemoval = false;
        }
        return orphanRemoval;
    }

    /**
     * Sets the value of the orphanRemoval property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setOrphanRemoval(Boolean value) {
        this.orphanRemoval = value;
    }

    /**
     * @return the owner
     */
    @Override
    public boolean isOwner() {
        if (owner == null) {
            return Boolean.FALSE;
        }
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    @Override
    public void setOwner(boolean owner) {
        this.owner = owner;
        if(owner){
            mappedBy = null;
        } 
        
    }

}