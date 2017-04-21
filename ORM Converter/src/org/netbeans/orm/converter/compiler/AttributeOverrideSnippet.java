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
import java.util.List;
import static org.netbeans.jcode.jpa.JPAConstants.ATTRIBUTE_OVERRIDE;
import static org.netbeans.jcode.jpa.JPAConstants.ATTRIBUTE_OVERRIDE_FQN;
import static org.netbeans.jcode.jpa.JPAConstants.COLUMN_FQN;
import org.netbeans.orm.converter.util.ORMConverterUtil;

public class AttributeOverrideSnippet implements Snippet {

    private String name = null;

    private ColumnDefSnippet columnDef = null;

    public ColumnDefSnippet getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(ColumnDefSnippet columnDef) {
        this.columnDef = columnDef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSnippet() throws InvalidDataException {

        if (name == null || columnDef == null) {
            throw new InvalidDataException("Name and ColumnDef required");
        }

        StringBuilder builder = new StringBuilder();

        builder.append("@").append(ATTRIBUTE_OVERRIDE).append("(");

        builder.append("name=\"");
        builder.append(name);
        builder.append(ORMConverterUtil.QUOTE);
        builder.append(ORMConverterUtil.COMMA);

        builder.append("column=");
        builder.append(columnDef.getSnippet());
        builder.append(ORMConverterUtil.CLOSE_PARANTHESES);

        return builder.toString();
    }

    @Override
    public Collection<String> getImportSnippets() throws InvalidDataException {
        List<String> importSnippets = new ArrayList<>();

        importSnippets.add(ATTRIBUTE_OVERRIDE_FQN);
        importSnippets.add(COLUMN_FQN);

        return importSnippets;
    }
}
