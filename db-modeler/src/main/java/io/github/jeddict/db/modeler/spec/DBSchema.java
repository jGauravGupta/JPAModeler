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
package io.github.jeddict.db.modeler.spec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.github.jeddict.util.StringUtils;
import io.github.jeddict.jpa.spec.extend.BaseElement;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.specification.model.document.IDefinitionElement;
import org.netbeans.modeler.specification.model.document.IRootElement;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modules.db.metadata.model.api.Schema;

/**
 *
 * @author Gaurav Gupta
 */
public class DBSchema extends BaseElement implements IDefinitionElement, IRootElement {

    private final Schema schema;
    private final Map<String, DBTable> tables = new HashMap<>();
    
    private final Map<String, String> queries = new HashMap<>();
    private final Map<String, String> insertQueries = new HashMap<>();
    private final Map<String, String> creationQueries = new HashMap<>();
    private final Map<String, List<String>> alterationQueries = new HashMap<>();

    public DBSchema(Schema schema) {
        this.schema = schema;
    }
    
    /**
     * @return the tables
     */
    public Collection<DBTable> getTables() {
        return tables.values();
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(List<DBTable> tables) {
        tables.forEach(t -> addTable(t));
    }

    public DBTable getTable(String name) {
        return this.tables.get(name.toUpperCase());
    }

    public void addTable(DBTable table) {
        this.tables.put(table.getName().toUpperCase(), table);
    }

    public void removeTable(DBTable table) {
        removeTable(table.getName());
    }
    
    public void removeTable(String table) {
        this.tables.remove(table.toUpperCase());
    }

    public List<DBTable> findAllTable(String tableName) {
        List<DBTable> tablesResult = new ArrayList<>();
        for (DBTable table : tables.values()) {
            if (tableName.equals(table.getName())) {
                tablesResult.add(table);
            }
        }
        return tablesResult;
    }

    @Override
    public void removeBaseElement(IBaseElement baseElement_In) {
        if (baseElement_In instanceof DBTable) {
            removeTable((DBTable) baseElement_In);
        } else {
            throw new InvalidElmentException("Invalid JPA Element");
        }
    }

    @Override
    public void addBaseElement(IBaseElement baseElement_In) {
        if (baseElement_In instanceof DBTable) {
            addTable((DBTable) baseElement_In);
        } else {
            throw new InvalidElmentException("Invalid JPA Element");
        }

    }

    public String getQuery(String table) {
        return queries.get(table);
    }
 
    public String getSchemaName() {
        return schema.getName();
    }

    public String getCatalogName() {
        return schema.getParent().getName();
    }
    
    public void putQuery(String table, String query) {
        if(StringUtils.isNotBlank(query)){
            if (query.startsWith("INSERT")) {
                insertQueries.put(table, query);
            } else {
                queries.put(table, query);
            }
        }
    }
    
    public String getCreateQuery(String table) {
        return creationQueries.get(table);
    }

    public void putCreateQuery(String table, String query) {
        if(StringUtils.isNotBlank(query)){
            creationQueries.put(table, query);
        }
    }

    public List<String> getAlterQuery(String table) {
        return alterationQueries.get(table);
    }

    public void putAlterQuery(String table, String query) {
        if (StringUtils.isNotBlank(query)) {
            if (getAlterQuery(table) == null) {
                alterationQueries.put(table, new ArrayList<>());
            }
            getAlterQuery(table).add(query);
        }
    }

    public String getSQL() {
        StringBuilder queryList = new StringBuilder();
        queries.values().forEach((query) -> {
            queryList.append(query).append(";\n");
        });
        insertQueries.values().forEach((query) -> {
            queryList.append(query).append(";\n");
        });
        creationQueries.values().forEach((query) -> {
            queryList.append(query).append(";\n");
        });

        alterationQueries.values().stream()
                .flatMap(queries -> queries.stream())
                .forEach((query) -> {
            queryList.append(query).append(";\n");
        });

        return queryList.toString();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
