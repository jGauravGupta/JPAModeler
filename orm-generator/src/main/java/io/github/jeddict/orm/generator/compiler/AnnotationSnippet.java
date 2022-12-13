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

import static io.github.jeddict.orm.generator.util.ORMConverterUtil.AT;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import static io.github.jeddict.util.StringUtils.isNotBlank;

public class AnnotationSnippet implements Snippet {

    private String name;
    private String _import;//TODO multi import with sub element
    

    @Override
    public String getSnippet() throws InvalidDataException {
        return name;
    }
    
    private boolean haveElements(String name){
        return name.charAt(name.length()-1) == ')';
    }

    @Override
    public Collection<String> getImportSnippets() throws InvalidDataException {
        Set<String> imports = new HashSet<>();
        if (isNotBlank(_import)) {
            imports.add(_import);
        }
        return imports;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        name = name.replaceAll("[\n\r]", "").trim();
        if(haveElements(name)){
            int startChild = name.indexOf('(');
            _import = name.substring(1, startChild);
        } else {
             _import = name.substring(1);
        }
        this.name = AT + name.substring(_import.lastIndexOf('.') + 2);
    }
}
