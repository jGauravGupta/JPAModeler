/**
 * Copyright 2013-2022 the original author or authors from the Jeddict project (https://jeddict.github.io/).
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
package io.github.jeddict.orm.generator.compiler;

import static io.github.jeddict.jcode.JPAConstants.MAP_KEY;
import static io.github.jeddict.jcode.JPAConstants.MAP_KEY_FQN;
import io.github.jeddict.jpa.spec.extend.Attribute;
import io.github.jeddict.orm.generator.util.ClassHelper;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.AT;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.CLOSE_PARANTHESES;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.OPEN_PARANTHESES;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.QUOTE;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MapKeySnippet implements Snippet {

    
    //Existing MapKeyType
     private Attribute mapKeyAttribute;
     
    private final ClassHelper mapKeyAttributeType = new ClassHelper();

    //New MapKeyType - Basic
    private ColumnSnippet columnSnippet;
    private TemporalSnippet temporalSnippet;
    private EnumeratedSnippet enumeratedSnippet;
    
    //New MapKeyType - Entity
    private JoinColumnsSnippet joinColumnsSnippet;
            
    //New MapKeyType - Embeddable
    private AttributeOverridesSnippet attributeOverrideSnippet;
     
    /**
     * @return the mapKeyAttribute
     */
    public Attribute getMapKeyAttribute() {
        return mapKeyAttribute;
    }

    /**
     * @param mapKeyAttribute the mapKeyAttribute to set
     */
    public void setMapKeyAttribute(Attribute mapKeyAttribute) {
        this.mapKeyAttribute = mapKeyAttribute;
    }

    /**
     * @return the temporalSnippet
     */
    public TemporalSnippet getTemporalSnippet() {
        return temporalSnippet;
    }

    /**
     * @param temporalSnippet the temporalSnippet to set
     */
    public void setTemporalSnippet(TemporalSnippet temporalSnippet) {
        this.temporalSnippet = temporalSnippet;
    }

    /**
     * @return the enumeratedSnippet
     */
    public EnumeratedSnippet getEnumeratedSnippet() {
        return enumeratedSnippet;
    }

    /**
     * @param enumeratedSnippet the enumeratedSnippet to set
     */
    public void setEnumeratedSnippet(EnumeratedSnippet enumeratedSnippet) {
        this.enumeratedSnippet = enumeratedSnippet;
    }

    /**
     * @return the joinColumnsSnippet
     */
    public JoinColumnsSnippet getJoinColumnsSnippet() {
        return joinColumnsSnippet;
    }

    /**
     * @param joinColumnsSnippet the joinColumnsSnippet to set
     */
    public void setJoinColumnsSnippet(JoinColumnsSnippet joinColumnsSnippet) {
        this.joinColumnsSnippet = joinColumnsSnippet;
    }

    public void setMapKeyAttributeType(String type) {
        this.getMapKeyAttributeType().setClassName(type);
    }

    /**
     * @return the mapKeyColumnSnippet
     */
    public ColumnSnippet getColumnSnippet() {
        return columnSnippet;
    }

    /**
     * @param mapKeyColumnSnippet the mapKeyColumnSnippet to set
     */
    public void setColumnSnippet(ColumnSnippet mapKeyColumnSnippet) {
        this.columnSnippet = mapKeyColumnSnippet;
    }

    /**
     * @return the mapKeyAttributeOverrideSnippet
     */
    public AttributeOverridesSnippet getAttributeOverrideSnippet() {
        return attributeOverrideSnippet;
    }

    /**
     * @param attributeOverridesSnippet the mapKeyAttributeOverrideSnippet to
     * set
     */
    public void setAttributeOverrideSnippet(AttributeOverridesSnippet attributeOverridesSnippet) {
        this.attributeOverrideSnippet = attributeOverridesSnippet;
    }

    /**
     * @return the mapKeyAttributeType
     */
    public ClassHelper getMapKeyAttributeType() {
        return mapKeyAttributeType;
    }

    @Override
    public String getSnippet() throws InvalidDataException {
        StringBuilder builder = new StringBuilder();
        if (mapKeyAttribute != null) {
            builder.append(AT).append(MAP_KEY);
            builder.append(OPEN_PARANTHESES);
            builder.append("name = ");
            builder.append(QUOTE).append(mapKeyAttribute.getName()).append(QUOTE);
            builder.append(CLOSE_PARANTHESES);
        } else if (joinColumnsSnippet != null) {
            builder.append(joinColumnsSnippet.getSnippet());
        } else if (attributeOverrideSnippet != null) {
            builder.append(attributeOverrideSnippet.getSnippet());
        } else {
            if (temporalSnippet != null) {
                builder.append(temporalSnippet.getSnippet());
            } else if (enumeratedSnippet != null) {
                builder.append(enumeratedSnippet.getSnippet());
            }
            if (columnSnippet != null) {
                builder.append(columnSnippet.getSnippet());
            }
        }
        return builder.toString();
    }

    @Override
    public Collection<String> getImportSnippets() throws InvalidDataException {
        Set<String> imports = new HashSet<>();
        if (mapKeyAttribute != null) {
            imports.add(MAP_KEY_FQN);
        } else if (joinColumnsSnippet != null) {
            imports.addAll(joinColumnsSnippet.getImportSnippets());
        } else if (attributeOverrideSnippet != null) {
            imports.addAll(attributeOverrideSnippet.getImportSnippets());
        } else {
            if (temporalSnippet != null) {
                imports.addAll(temporalSnippet.getImportSnippets());
            } else if (enumeratedSnippet != null) {
                imports.addAll(enumeratedSnippet.getImportSnippets());
            }
            if (columnSnippet != null) {
                imports.addAll(columnSnippet.getImportSnippets());
            }
        }
        return imports;
    }

    public boolean isEmpty(){
        return false;
    }

}
