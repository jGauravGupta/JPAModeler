//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import io.github.jeddict.source.ClassExplorer;
import java.util.Optional;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 *
 *
 * @Target({TYPE}) @Retention(RUNTIME) public @interface IdClass { Class
 * value(); }
 *
 *
 *
 * <p>
 * Java class for id-class complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="id-class">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "id-class")
public class IdClass {

    @XmlAttribute(name = "class", required = true)
    protected String clazz;

    public static IdClass load(ClassExplorer clazz) {
        IdClass idClass = null;
        Optional<ResolvedReferenceTypeDeclaration> idClazzNameOpt = clazz
                .getResolvedClassAttribute(jakarta.persistence.IdClass.class, "value");
        if (idClazzNameOpt.isPresent()) {
            idClass = new IdClass();
            idClass.clazz = idClazzNameOpt.get().getClassName();
        }
        return idClass;
    }

    public IdClass() {
    }

    public IdClass(String clazz) {
        this.clazz = clazz;
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
    
}
