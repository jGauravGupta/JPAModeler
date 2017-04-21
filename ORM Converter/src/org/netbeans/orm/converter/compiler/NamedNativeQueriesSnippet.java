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
import static org.netbeans.jcode.jpa.JPAConstants.NAMED_NATIVE_QUERIES;
import static org.netbeans.jcode.jpa.JPAConstants.NAMED_NATIVE_QUERIES_FQN;
import org.netbeans.orm.converter.util.ORMConverterUtil;

public class NamedNativeQueriesSnippet implements Snippet {

    private List<NamedNativeQuerySnippet> namedNativeQueries = Collections.EMPTY_LIST;

    public void addNamedQuery(NamedNativeQuerySnippet namedQueryDef) {

        if (namedNativeQueries.isEmpty()) {
            namedNativeQueries = new ArrayList<NamedNativeQuerySnippet>();
        }

        namedNativeQueries.add(namedQueryDef);
    }

    public List<NamedNativeQuerySnippet> getNamedQueries() {
        return namedNativeQueries;
    }

    public void setNamedQueries(List<NamedNativeQuerySnippet> namedQueries) {
        if (namedNativeQueries != null) {
            this.namedNativeQueries = namedQueries;
        }
    }

    @Override
    public String getSnippet() throws InvalidDataException {

        if (namedNativeQueries.isEmpty()) {
            throw new InvalidDataException("Missing " + NAMED_NATIVE_QUERIES);
        }

        if (namedNativeQueries.size() == 1) {
            return namedNativeQueries.get(0).getSnippet();
        }

        StringBuilder builder = new StringBuilder();

        builder.append("@").append(NAMED_NATIVE_QUERIES).append("({");

        for (NamedNativeQuerySnippet namedNativeQuery : namedNativeQueries) {
            builder.append(namedNativeQuery.getSnippet());
            builder.append(ORMConverterUtil.COMMA);
        }

        return builder.substring(0, builder.length() - 1)
                + ORMConverterUtil.CLOSE_BRACES
                + ORMConverterUtil.CLOSE_PARANTHESES;
    }

    @Override
    public Collection<String> getImportSnippets() throws InvalidDataException {

        if (namedNativeQueries.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        if (namedNativeQueries.size() == 1) {
            return namedNativeQueries.get(0).getImportSnippets();
        }

        ArrayList<String> importSnippets = new ArrayList<>();

        importSnippets.add(NAMED_NATIVE_QUERIES_FQN);
        for(NamedNativeQuerySnippet namedNativeQuery : namedNativeQueries){
            importSnippets.addAll(namedNativeQuery.getImportSnippets());
        }
        return importSnippets;
    }
}
