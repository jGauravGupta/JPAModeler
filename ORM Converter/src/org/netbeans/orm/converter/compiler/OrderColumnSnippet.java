/**
 * Copyright [2017] Gaurav Gupta
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

import java.util.Collections;
import java.util.List;
import static org.netbeans.jcode.jpa.JPAConstants.ORDER_COLUMN;
import static org.netbeans.jcode.jpa.JPAConstants.ORDER_COLUMN_FQN;
import org.netbeans.jpa.modeler.spec.OrderColumn;
import org.netbeans.jpa.modeler.settings.code.CodePanel;
import static org.netbeans.orm.converter.util.ORMConverterUtil.CLOSE_PARANTHESES;
import static org.netbeans.orm.converter.util.ORMConverterUtil.COMMA;
import static org.netbeans.orm.converter.util.ORMConverterUtil.OPEN_PARANTHESES;
import static org.netbeans.orm.converter.util.ORMConverterUtil.QUOTE;

public class OrderColumnSnippet implements Snippet {
    
    private boolean insertable = true;
    private boolean nullable = true;
    private boolean updatable = true;
    private String columnDefinition = null;
    private String name = null;
    
    public OrderColumnSnippet(OrderColumn orderColumn) {
        this.name = orderColumn.getName();
        this.columnDefinition = orderColumn.getColumnDefinition();
        this.insertable = orderColumn.isInsertable();
        this.nullable = orderColumn.isNullable();
        this.updatable = orderColumn.isUpdatable();
    }


    public boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSnippet() throws InvalidDataException {
        StringBuilder builder = new StringBuilder("@");
        builder.append(ORDER_COLUMN);
        
        if (!CodePanel.isGenerateDefaultValue()) {
            if (insertable == true
                    && nullable == true
                    && updatable == true
                    && columnDefinition == null
                    && name == null) {
                return builder.toString();
            }
        }

        builder.append(OPEN_PARANTHESES);

        if (name != null) {
            builder.append("name=\"");
            builder.append(name);
            builder.append(QUOTE);
            builder.append(COMMA);
        }

        if (CodePanel.isGenerateDefaultValue()) {
            builder.append(" insertable=");
            builder.append(insertable);
            builder.append(COMMA);
        } else if (insertable == false) {
            builder.append(" insertable=");
            builder.append(insertable);
            builder.append(COMMA);
        }

        if (CodePanel.isGenerateDefaultValue()) {
            builder.append(" nullable=");
            builder.append(nullable);
            builder.append(COMMA);
        } else if (nullable == false) {
            builder.append(" nullable=");
            builder.append(nullable);
            builder.append(COMMA);
        }

        if (CodePanel.isGenerateDefaultValue()) {
            builder.append(" updatable=");
            builder.append(updatable);
            builder.append(COMMA);
        } else if (updatable == false) {
            builder.append(" updatable=");
            builder.append(updatable);
            builder.append(COMMA);
        }
        
        if (columnDefinition != null && !columnDefinition.trim().isEmpty()) {
            builder.append(" columnDefinition=\"");
            builder.append(columnDefinition);
            builder.append(QUOTE);
            builder.append(COMMA);
        }
        
        return builder.substring(0, builder.length() - 1) + CLOSE_PARANTHESES;
    }

    @Override
    public List<String> getImportSnippets() throws InvalidDataException {
        return Collections.singletonList(ORDER_COLUMN_FQN);
    }

}
