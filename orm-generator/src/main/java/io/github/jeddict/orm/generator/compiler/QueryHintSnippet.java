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

import static io.github.jeddict.jcode.JPAConstants.QUERY_HINT;
import static io.github.jeddict.jcode.JPAConstants.QUERY_HINT_FQN;
import java.util.Collection;
import static java.util.Collections.singleton;

public class QueryHintSnippet implements Snippet {

    private String name = null;
    private String value = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getSnippet() throws InvalidDataException {
        if (name == null || value == null) {
            throw new InvalidDataException("name or value cannot be null");
        }

        return annotate(
                QUERY_HINT,
                attribute("name", name),
                attribute("value", value)
        );

    }

    @Override
    public Collection<String> getImportSnippets() throws InvalidDataException {
        return singleton(QUERY_HINT_FQN);
    }
}
