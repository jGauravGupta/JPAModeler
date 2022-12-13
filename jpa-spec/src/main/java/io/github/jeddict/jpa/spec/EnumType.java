//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package io.github.jeddict.jpa.spec;

import io.github.jeddict.source.AnnotationExplorer;
import io.github.jeddict.source.MemberExplorer;
import java.util.Optional;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for enum-type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * <
 * pre>
 * &lt;simpleType name="enum-type"> &lt;restriction
 * base="{http://www.w3.org/2001/XMLSchema}token"> &lt;enumeration
 * value="ORDINAL"/> &lt;enumeration value="STRING"/> &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "enum-type")
@XmlEnum
public enum EnumType {

    DEFAULT,
    ORDINAL,
    STRING;

    public String value() {
        return name();
    }

    public static EnumType fromValue(String v) {
        return valueOf(v);
    }

    public static EnumType load(MemberExplorer member) {
        Optional<AnnotationExplorer> enumeratedOpt = member.getAnnotation(jakarta.persistence.Enumerated.class);
        if (enumeratedOpt.isPresent()) {
            AnnotationExplorer annotation = enumeratedOpt.get();
            return annotation.getEnum("value")
                    .map(EnumType::valueOf)
                    .orElse(DEFAULT);
        }
        return null;
    }

    public static EnumType loadMapKey(MemberExplorer member) {
        Optional<AnnotationExplorer> enumeratedOpt = member.getAnnotation(jakarta.persistence.MapKeyEnumerated.class);
        if (enumeratedOpt.isPresent()) {
            AnnotationExplorer annotation = enumeratedOpt.get();
            return annotation.getEnum("value")
                    .map(EnumType::valueOf)
                    .orElse(DEFAULT);
        }
        return null;
    }
}
