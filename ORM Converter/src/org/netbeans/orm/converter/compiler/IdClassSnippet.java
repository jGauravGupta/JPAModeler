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
import static org.netbeans.jcode.jpa.JPAConstants.ID_CLASS;
import static org.netbeans.jcode.jpa.JPAConstants.ID_CLASS_FQN;
import org.netbeans.orm.converter.util.ClassHelper;
import org.netbeans.orm.converter.util.ORMConverterUtil;

public class IdClassSnippet implements Snippet {

    private ClassHelper classHelper = new ClassHelper();

    public String getValue() {
        return classHelper.getClassNameWithClassSuffix();
    }

    public void setValue(String value) {
        classHelper.setClassName(value);
    }

    public void setPackageName(String packageName) {
        classHelper.setPackageName(packageName);
    }

    @Override
    public String getSnippet() throws InvalidDataException {
        if (classHelper.getClassName() == null) {
            throw new InvalidDataException("value is a required");
        }

        return "@"+ID_CLASS+"(" + getValue() + ORMConverterUtil.CLOSE_PARANTHESES;
    }

    @Override
    public Collection<String> getImportSnippets() throws InvalidDataException {
        List<String> importSnippets = new ArrayList<>();

        importSnippets.add(ID_CLASS_FQN);
        if (classHelper.getFQClassName() != null) {
            importSnippets.add(classHelper.getFQClassName());
        }

        return importSnippets;
    }
}
