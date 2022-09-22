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
package io.github.jeddict.jpa.spec.bean;

import com.github.javaparser.resolution.declarations.ResolvedTypeDeclaration;
import io.github.jeddict.source.MemberExplorer;
import java.util.Optional;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "many-to-many-assoc")
@XmlRootElement
public class ManyToManyAssociation extends MultiAssociationAttribute {

    public static ManyToManyAssociation load(ManyToManyAssociation attribute, MemberExplorer member, ResolvedTypeDeclaration type) {
        attribute.loadAttribute(member);
        attribute.setCollectionType(member.getType());

        Optional<BeanClass> beanClassOpt = member.getSource().findBeanClass(type);
        if (!beanClassOpt.isPresent()) {
            return null;
        }
        attribute.setConnectedClass(beanClassOpt.get());
        return attribute;
    }
}
