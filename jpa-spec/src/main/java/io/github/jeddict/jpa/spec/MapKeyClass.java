//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedTypeDeclaration;
import io.github.jeddict.source.AnnotationExplorer;
import io.github.jeddict.source.MemberExplorer;
import java.util.Optional;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 *
 *
 * @Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface MapKeyClass {
 * Class value(); }
 *
 *
 *
 * <p>
 * Java class for map-key-class complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="map-key-class">
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
@XmlType(name = "map-key-class")
public class MapKeyClass {

    @XmlAttribute(name = "class", required = true)
    protected String clazz;
 
    public static ResolvedTypeDeclaration getDeclaredType(MemberExplorer member) {
        Optional<ResolvedTypeDeclaration> keyTypeOpt;
        ResolvedTypeDeclaration keyType;
        Optional<AnnotationExplorer> mapKeyClassOpt = member.getAnnotation(jakarta.persistence.MapKeyClass.class);
        if (mapKeyClassOpt.isPresent()) {
            Optional<ResolvedReferenceTypeDeclaration> mapKeyClassValueOpt = mapKeyClassOpt.get().getResolvedClass("value");
            if (mapKeyClassValueOpt.isPresent()) {
                keyType = mapKeyClassValueOpt.get();
            } else {
                throw new UnsolvedSymbolException("@MapKeyClass value not defind '" + member.getFieldName() + "'");
            }
        } else {
            keyTypeOpt = member.getTypeArgumentDeclaration(0);
            if (keyTypeOpt.isPresent()) {
                keyType = keyTypeOpt.get();
            } else {
                throw new UnsolvedSymbolException("@MapKeyClass or generic type not defined in ElementCollection attribute '" + member.getFieldName() + "'");
            }
        }
        return keyType;
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
