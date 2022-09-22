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
package io.github.jeddict.orm.generator.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import io.github.jeddict.jpa.spec.extend.ReferenceClass;
import io.github.jeddict.orm.generator.compiler.EntityListenerSnippet;

public class GeneratorUtil {

    public static List<EntityListenerSnippet> processEntityListeners(Set<ReferenceClass> parsedEntityListeners, String packageName) {
        if (parsedEntityListeners == null || parsedEntityListeners.isEmpty()) {
            return Collections.<EntityListenerSnippet>emptyList();
        }

        List<EntityListenerSnippet> entityListeners = new ArrayList<>();
        for (ReferenceClass entityListener : parsedEntityListeners) {
            EntityListenerSnippet entityListenerSnippet = new EntityListenerSnippet();
            entityListenerSnippet.setClassName(entityListener.getName());
            entityListeners.add(entityListenerSnippet);
        }
        return entityListeners;
    }

}
