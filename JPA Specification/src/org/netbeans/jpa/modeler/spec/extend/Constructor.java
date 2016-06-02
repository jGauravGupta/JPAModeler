/**
 * Copyright [2016] Gaurav Gupta
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
package org.netbeans.jpa.modeler.spec.extend;

import java.util.Objects;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author Gaurav Gupta
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Constructor extends ClassMembers {

    private AccessModifierType accessModifier;

    /**
     * @return the accessModifier
     */
    public AccessModifierType getAccessModifier() {
        if(accessModifier==null){
            return AccessModifierType.PUBLIC;
        }
        return accessModifier;
    }

    /**
     * @param accessModifier the accessModifier to set
     */
    public void setAccessModifier(AccessModifierType accessModifier) {
        this.accessModifier = accessModifier;
    }

    @Override
    public int hashCode() {
        return getAttributes().size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Constructor other = (Constructor) obj;
        if (this.getAttributes().size() != other.getAttributes().size()) {
            return false;
        }
        for(int i=0; i< this.getAttributes().size();i++){
            if(!Objects.equals(this.getAttributes().get(i).getDataTypeLabel(), other.getAttributes().get(i).getDataTypeLabel())){
                return false;
            }
        }
        return true;
    }
    
    public String getSignature() {
        return getAttributes().stream().map((Attribute a) -> a.getDataTypeLabel()).collect(Collectors.joining(", "));
    }

}