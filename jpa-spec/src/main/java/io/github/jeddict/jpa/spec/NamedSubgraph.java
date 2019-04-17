//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.18 at 01:46:14 PM IST 
//
package io.github.jeddict.jpa.spec;

import io.github.jeddict.source.AnnotationExplorer;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 *
 * @Target({}) @Retention(RUNTIME) public @interface NamedSubgraph { String
 * name(); Class type() default void.class; NamedAttributeNode[]
 * attributeNodes(); }
 *
 *
 *
 * <p>
 * Java class for named-subgraph complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="named-subgraph">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="named-attribute-node" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-attribute-node" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "named-subgraph", propOrder = {
    "namedAttributeNode"
})
public class NamedSubgraph {

    @XmlElement(name = "named-attribute-node")
    protected List<NamedAttributeNode> namedAttributeNode;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "class")
    protected String clazz;

    public NamedSubgraph() {
    }

    public NamedSubgraph(String name) {
        this.name = name;
    }

    public static NamedSubgraph load(AnnotationExplorer annotation) {
        NamedSubgraph namedSubgraph = new NamedSubgraph();
        annotation.getString("name").ifPresent(namedSubgraph::setName);
        annotation.getClassName("type").ifPresent(namedSubgraph::setClazz);
        namedSubgraph.namedAttributeNode
                = annotation.getAnnotationList("attributeNodes")
                        .map(NamedAttributeNode::load)
                        .collect(toList());
        return namedSubgraph;
    }

    /**
     * Gets the value of the namedAttributeNode property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the namedAttributeNode property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedAttributeNode().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedAttributeNode }
     *
     *
     */
    public List<NamedAttributeNode> getNamedAttributeNode() {
        if (namedAttributeNode == null) {
            namedAttributeNode = new ArrayList<>();
        }
        return this.namedAttributeNode;
    }

    public void addNamedAttributeNode(NamedAttributeNode node) {
        if (namedAttributeNode == null) {
            namedAttributeNode = new ArrayList<>();
        }
        this.namedAttributeNode.add(node);
    }

    public void removeNamedAttributeNode(NamedAttributeNode node) {
        if (namedAttributeNode == null) {
            namedAttributeNode = new ArrayList<>();
        }
        this.namedAttributeNode.remove(node);
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
     * Gets the value of the clazz property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

    public NamedAttributeNode findNamedAttributeNode(String name) {
        if (namedAttributeNode == null) {
            namedAttributeNode = new ArrayList<>();
        }

        for (NamedAttributeNode namedAttributeNodeInst : namedAttributeNode) {
            if (namedAttributeNodeInst.getName().equals(name)) {
                return namedAttributeNodeInst;
            }
        }

        return null;
    }

}
