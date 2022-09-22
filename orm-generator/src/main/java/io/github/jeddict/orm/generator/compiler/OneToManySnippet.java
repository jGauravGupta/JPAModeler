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

import io.github.jeddict.orm.generator.util.ORMConverterUtil;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.AT;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.CLOSE_BRACES;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.CLOSE_PARANTHESES;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.COMMA;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.OPEN_BRACES;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.OPEN_PARANTHESES;
import static io.github.jeddict.orm.generator.util.ORMConverterUtil.getCommaSeparatedString;
import static io.github.jeddict.settings.generate.GenerateSettings.isGenerateDefaultValue;
import static io.github.jeddict.util.StringUtils.isNotBlank;

public class OneToManySnippet extends MultiRelationAttributeSnippet {

    private boolean orphanRemoval = false;

    @Override
    public String getType() {
        return "OneToMany";
    }

    /**
     * @return the orphanRemoval
     */
    public boolean isOrphanRemoval() {
        return orphanRemoval;
    }

    /**
     * @param orphanRemoval the orphanRemoval to set
     */
    public void setOrphanRemoval(boolean orphanRemoval) {
        this.orphanRemoval = orphanRemoval;
    }

    @Override
    public String getSnippet() throws InvalidDataException {

        if (!isGenerateDefaultValue()) {
            if (mappedBy == null
                    && orphanRemoval == false
                    && getFetchType() == null
                    && getCascadeTypes().isEmpty() 
                    && (mapKeySnippet==null || mapKeySnippet.isEmpty())) {
                return AT + getType();
            }
        }
               
        StringBuilder builder = new StringBuilder();
        if (mapKeySnippet != null && !mapKeySnippet.isEmpty()) {
            builder.append(mapKeySnippet.getSnippet())
                    .append(ORMConverterUtil.NEW_LINE)
                    .append(ORMConverterUtil.TAB);
        }

        builder.append(AT).append(getType())
                .append(OPEN_PARANTHESES)
                .append(attribute("mappedBy", mappedBy))
                .append(attributeExp("fetch", getFetchType()));;

        if (isGenerateDefaultValue() || orphanRemoval == true) {
            builder.append("orphanRemoval = ")
                    .append(orphanRemoval)
                    .append(COMMA);
        }

        if (isGenerateDefaultValue() && isNotBlank(getTargetEntity())) {
            builder.append("targetEntity = ")
                    .append(getTargetEntity())
                    .append(COMMA);
        }
        
        if (!getCascadeTypes().isEmpty()) {
            builder.append("cascade = ");
            if (getCascadeTypes().size() > 1) {
                builder.append(OPEN_BRACES)
                        .append(getCommaSeparatedString(getCascadeTypes()))
                        .append(CLOSE_BRACES);
            } else {
                builder.append(getCascadeTypes().get(0));
            }
            builder.append(COMMA);
        }

        return builder.substring(0, builder.length() - 1) + CLOSE_PARANTHESES;
    }

}
