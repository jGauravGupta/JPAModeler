/**
 * Copyright [2014] Gaurav Gupta
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
package org.netbeans.orm.converter.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static org.netbeans.jcode.jpa.JPAConstants.TABLE_GENERATOR;
import static org.netbeans.jcode.jpa.JPAConstants.TABLE_GENERATOR_FQN;
import org.netbeans.jpa.modeler.settings.code.CodePanel;
import org.netbeans.orm.converter.util.ORMConverterUtil;

public class TableGeneratorSnippet implements Snippet {

    private int allocationSize = 50;
    private int initialValue = 0;

    private String name;
    private String catalog = null;
    private String pkColumnName = null;
    private String pkColumnValue = null;
    private String schema = null;
    private String table = null;
    private String valueColumnName = null;

    private List<UniqueConstraintSnippet> uniqueConstraints = Collections.EMPTY_LIST;
    private List<IndexSnippet> indices = Collections.EMPTY_LIST;

    public int getAllocationSize() {
        return allocationSize;
    }

    public void setAllocationSize(int allocationSize) {
        this.allocationSize = allocationSize;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPkColumnName() {
        return pkColumnName;
    }

    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }

    public String getPkColumnValue() {
        return pkColumnValue;
    }

    public void setPkColumnValue(String pkColumnValue) {
        this.pkColumnValue = pkColumnValue;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<UniqueConstraintSnippet> getUniqueConstraints() {
        return uniqueConstraints;
    }

    public void setUniqueConstraints(List<UniqueConstraintSnippet> uniqueConstraints) {
        if (uniqueConstraints != null) {
            this.uniqueConstraints = uniqueConstraints;
        }
    }

    public String getValueColumnName() {
        return valueColumnName;
    }

    public void setValueColumnName(String valueColumnName) {
        this.valueColumnName = valueColumnName;
    }

    @Override
    public String getSnippet() throws InvalidDataException {
        if (name == null) {
            throw new InvalidDataException("Name is required");
        }

        StringBuilder builder = new StringBuilder();

        builder.append("@").append(TABLE_GENERATOR).append("(name=\"");
        builder.append(name);
        builder.append(ORMConverterUtil.QUOTE);
        builder.append(ORMConverterUtil.COMMA);

        if (pkColumnValue != null && !pkColumnValue.isEmpty()) {
            builder.append("pkColumnValue=\"");
            builder.append(pkColumnValue);
            builder.append(ORMConverterUtil.QUOTE);
            builder.append(ORMConverterUtil.COMMA);
        }

        if (schema != null && !schema.isEmpty()) {
            builder.append("schema=\"");
            builder.append(schema);
            builder.append(ORMConverterUtil.QUOTE);
            builder.append(ORMConverterUtil.COMMA);
        }

        if (table != null && !table.isEmpty()) {
            builder.append("table=\"");
            builder.append(table);
            builder.append(ORMConverterUtil.QUOTE);
            builder.append(ORMConverterUtil.COMMA);
        }

        if (valueColumnName != null && !valueColumnName.isEmpty()) {
            builder.append("valueColumnName=\"");
            builder.append(valueColumnName);
            builder.append(ORMConverterUtil.QUOTE);
            builder.append(ORMConverterUtil.COMMA);
        }

        if (catalog != null && !catalog.isEmpty()) {
            builder.append("catalog=\"");
            builder.append(catalog);
            builder.append(ORMConverterUtil.QUOTE);
            builder.append(ORMConverterUtil.COMMA);
        }

        if (pkColumnName != null && !pkColumnName.isEmpty()) {
            builder.append("pkColumnName=\"");
            builder.append(pkColumnName);
            builder.append(ORMConverterUtil.QUOTE);
            builder.append(ORMConverterUtil.COMMA);
        }

        if (CodePanel.isGenerateDefaultValue() || allocationSize != 50) {
            builder.append("allocationSize=");
            builder.append(allocationSize);
            builder.append(ORMConverterUtil.COMMA);
        }

        if (CodePanel.isGenerateDefaultValue() || initialValue != 0) {//BUG : 1 -> 0 //resolved by gaurav gupta
            builder.append("initialValue=");
            builder.append(initialValue);
            builder.append(ORMConverterUtil.COMMA);
        }

        if (!uniqueConstraints.isEmpty()) {
            builder.append("uniqueConstraints={");

            for (UniqueConstraintSnippet uniqueConstraint : uniqueConstraints) {
                builder.append(uniqueConstraint.getSnippet());
                builder.append(ORMConverterUtil.COMMA);
            }
            builder.deleteCharAt(builder.length() - 1);

            builder.append(ORMConverterUtil.CLOSE_BRACES);
            builder.append(ORMConverterUtil.COMMA);
        }
        
                if (!indices.isEmpty()) {
            builder.append("indexes={");

            for (IndexSnippet snippet : indices) {
                builder.append(snippet.getSnippet());
                builder.append(ORMConverterUtil.COMMA);
            }

            builder.deleteCharAt(builder.length() - 1);
            builder.append(ORMConverterUtil.CLOSE_BRACES);
            builder.append(ORMConverterUtil.COMMA);
        }

        return builder.substring(0, builder.length() - 1)
                + ORMConverterUtil.CLOSE_PARANTHESES;

    }

    @Override
    public Collection<String> getImportSnippets() throws InvalidDataException {

        if (uniqueConstraints.isEmpty() && indices.isEmpty()) {
            return Collections.singletonList(TABLE_GENERATOR_FQN);
        }

        List<String> importSnippets = new ArrayList<>();
        importSnippets.add(TABLE_GENERATOR_FQN);
        if (!uniqueConstraints.isEmpty()) {
            importSnippets.addAll(uniqueConstraints.get(0).getImportSnippets());
        }
        if (!indices.isEmpty()) {
            importSnippets.addAll(indices.get(0).getImportSnippets());
        }
        return importSnippets;
    }
    
        /**
     * @return the indices
     */
    public List<IndexSnippet> getIndices() {
        return indices;
    }

    /**
     * @param indices the indices to set
     */
    public void setIndices(List<IndexSnippet> indices) {
        this.indices = indices;
    }
}
