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
package org.netbeans.db.modeler.spec;

import java.util.List;
import org.netbeans.jpa.modeler.spec.Entity;
import org.netbeans.jpa.modeler.spec.Id;
import org.netbeans.jpa.modeler.spec.JoinColumn;
import org.netbeans.jpa.modeler.spec.PrimaryKeyJoinColumn;

public class DBPrimaryKeyJoinColumn extends DBColumn<Id> implements DBForeignKey<PrimaryKeyJoinColumn> {

    private final PrimaryKeyJoinColumn joinColumn;
    private final List<PrimaryKeyJoinColumn> joinColumns;

    public DBPrimaryKeyJoinColumn(String name, Entity entity, Id attribute) {
        super(name, attribute);
        joinColumns = JoinColumnFinder.findPrimaryKeyJoinColumns(entity);
        joinColumn = JoinColumnFinder.findPrimaryKeyJoinColumn(name, joinColumns);
    }

    /**
     * @return the inverseJoinColumn
     */
    public PrimaryKeyJoinColumn getJoinColumn() {
        return joinColumn;
    }

    public List<PrimaryKeyJoinColumn> getJoinColumns() {
        return joinColumns;
    }
}
