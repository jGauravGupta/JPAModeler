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
package org.netbeans.orm.converter.compiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.netbeans.jpa.modeler.spec.extend.AccessModifierType;
import org.netbeans.jpa.modeler.spec.extend.Constructor;
import static org.netbeans.orm.converter.util.ORMConverterUtil.CLOSE_BRACES;
import static org.netbeans.orm.converter.util.ORMConverterUtil.CLOSE_PARANTHESES;
import static org.netbeans.orm.converter.util.ORMConverterUtil.COMMA;
import static org.netbeans.orm.converter.util.ORMConverterUtil.NEW_LINE;
import static org.netbeans.orm.converter.util.ORMConverterUtil.OPEN_BRACES;
import static org.netbeans.orm.converter.util.ORMConverterUtil.OPEN_PARANTHESES;
import static org.netbeans.orm.converter.util.ORMConverterUtil.SPACE;

public class ConstructorSnippet implements Snippet {

    private final String className;
    private final Constructor constructor;
    private final List<VariableDefSnippet> parentVariableSnippets;
    private final List<VariableDefSnippet> localVariableSnippets;
    private final List<VariableDefSnippet> allVariableSnippets;

    public ConstructorSnippet(String className, Constructor constructor,
            List<VariableDefSnippet> parentVariableSnippets, List<VariableDefSnippet> localVariableSnippets) {
        this.className = className;
        this.constructor = constructor;
        this.parentVariableSnippets = parentVariableSnippets;
        this.localVariableSnippets = localVariableSnippets;
        this.allVariableSnippets = new ArrayList<>(parentVariableSnippets);
        this.allVariableSnippets.addAll(localVariableSnippets);
    }

    @Override
    public String getSnippet() throws InvalidDataException {
        StringBuilder builder = new StringBuilder();
        if (constructor.getAccessModifier() != AccessModifierType.DEFAULT) {
            builder.append(constructor.getAccessModifier().getValue()).append(SPACE);
        }

        builder.append(className).append(OPEN_PARANTHESES);

        if (!allVariableSnippets.isEmpty()) {
            for (VariableDefSnippet variableSnippet : allVariableSnippets) {
                builder.append(variableSnippet.getType()).append(SPACE).append(variableSnippet.getName()).append(COMMA);
            }
            builder.setLength(builder.length() - 1);
        }
        builder.append(CLOSE_PARANTHESES);

        StringBuilder varAssign = new StringBuilder();
        if (!parentVariableSnippets.isEmpty()) {
            varAssign.append("        ")
                   .append("super(");
            for (VariableDefSnippet parentVariableSnippet : parentVariableSnippets) {
                varAssign.append(parentVariableSnippet.getName()).append(", ");
            }
            varAssign.setLength(varAssign.length() - 2);
            varAssign.append(");").append(NEW_LINE);
        }

        if (!localVariableSnippets.isEmpty()) {
            for (VariableDefSnippet localVariableSnippet : localVariableSnippets) {
                varAssign.append("        ")
                   .append(String.format("this.%s=%s;", localVariableSnippet.getName(), localVariableSnippet.getName())).append(NEW_LINE);
            }
        }

        builder.append(OPEN_BRACES).append(NEW_LINE);
        if (StringUtils.isNotBlank(constructor.getPreCode())) {
            builder.append(constructor.getPreCode()).append(NEW_LINE);
        }
        builder.append(varAssign);
        if (StringUtils.isNotBlank(constructor.getPostCode())) {
            builder.append(constructor.getPostCode()).append(NEW_LINE);
        }
        builder.append("    ").append(CLOSE_BRACES);

        return builder.toString();
    }

    @Override
    public List<String> getImportSnippets() throws InvalidDataException {
        return Collections.EMPTY_LIST;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

}
