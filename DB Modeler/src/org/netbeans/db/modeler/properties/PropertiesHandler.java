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
package org.netbeans.db.modeler.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.netbeans.db.modeler.core.widget.table.TableWidget;
import org.netbeans.db.modeler.properties.index.IndexPanel;
import org.netbeans.db.modeler.properties.uniqueconstraint.UniqueConstraintPanel;
import org.netbeans.db.modeler.spec.DBTable;
import org.netbeans.jpa.modeler.spec.Index;
import org.netbeans.jpa.modeler.spec.UniqueConstraint;
import org.netbeans.modeler.properties.nentity.Column;
import org.netbeans.modeler.properties.nentity.NAttributeEntity;
import org.netbeans.modeler.properties.nentity.NEntityPropertySupport;
import org.openide.nodes.PropertySupport;
import org.netbeans.modeler.properties.nentity.INEntityDataListener;
import org.netbeans.modeler.properties.nentity.NEntityDataListener;

public class PropertiesHandler {

    public static PropertySupport getIndexProperties(TableWidget<? extends DBTable> tableWidget) {
        final NAttributeEntity attributeEntity = new NAttributeEntity("Index", "Index", "Index of Database Table");
        attributeEntity.setCountDisplay(new String[]{"No Index exist", "One Index exist", "Indexes exist"});
        List<Index> indices = tableWidget.getBaseElementSpec().getIndexes();
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Columns", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new IndexPanel(tableWidget));
        attributeEntity.setTableDataListener(new NEntityDataListener<>(indices,
                t -> Arrays.asList(t.getName(), t.toString())));
        return new NEntityPropertySupport(tableWidget.getModelerScene().getModelerFile(), attributeEntity);
    }
    
    public static PropertySupport getUniqueConstraintProperties(TableWidget<? extends DBTable> tableWidget) {
        final NAttributeEntity attributeEntity = new NAttributeEntity("UniqueConstraint", "Unique Constraint", "Unique Constraints of Database Table");
        attributeEntity.setCountDisplay(new String[]{"No UniqueConstraints exist", "One UniqueConstraint exist", "UniqueConstraints exist"});
        Set<UniqueConstraint> uniqueConstraints = tableWidget.getBaseElementSpec().getUniqueConstraints();
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Columns", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new UniqueConstraintPanel(tableWidget));
        attributeEntity.setTableDataListener(new NEntityDataListener<>(uniqueConstraints,
                t -> Arrays.asList(t.getName(), t.toString())));
        return new NEntityPropertySupport(tableWidget.getModelerScene().getModelerFile(), attributeEntity);
    }


}
