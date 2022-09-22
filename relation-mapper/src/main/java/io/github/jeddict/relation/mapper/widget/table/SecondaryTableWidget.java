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
package io.github.jeddict.relation.mapper.widget.table;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JMenuItem;
import io.github.jeddict.util.StringUtils;
import io.github.jeddict.relation.mapper.spec.DBMapping;
import io.github.jeddict.relation.mapper.spec.DBSecondaryTable;
import io.github.jeddict.relation.mapper.initializer.RelationMapperScene;
import static io.github.jeddict.relation.mapper.initializer.RelationMapperUtil.SECONDARY_TABLE;
import static io.github.jeddict.relation.mapper.initializer.RelationMapperUtil.SECONDARY_TABLE_ICON_PATH;
import io.github.jeddict.analytics.JeddictLogger;
import io.github.jeddict.jpa.modeler.rules.entity.ClassValidator;
import io.github.jeddict.jpa.modeler.rules.entity.SQLKeywords;
import io.github.jeddict.jpa.spec.Entity;
import io.github.jeddict.jpa.spec.extend.PersistenceBaseAttribute;
import io.github.jeddict.jpa.modeler.initializer.DBUtil;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import static org.netbeans.modeler.widget.node.IWidgetStateHandler.StateType.ERROR;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;

public class SecondaryTableWidget extends TableWidget<DBSecondaryTable> {

//    private SecondaryTable table;
    public SecondaryTableWidget(RelationMapperScene scene, NodeWidgetInfo node) {
        super(scene, node);
        this.addPropertyChangeListener("table_name", (PropertyChangeListener<String>) (oldValue, value) -> {
            setName(value);
            setLabel(name);
        });
    }
    
    @Override
    public void init() {
//        table = (SecondaryTable)this.getBaseElementSpec().getEntity().getTable(this.getName());
    }

    private void setDefaultName() {
        Entity entity = this.getBaseElementSpec().getEntity();
        this.name = entity.getDefaultTableName();
        entity.getTable().setName(null);
        setLabel(name);
    }

    @Override
    public void setName(String name) {

        if (StringUtils.isNotBlank(name)) {
            this.name = name.replaceAll("\\s+", "");
            if (this.getModelerScene().getModelerFile().isLoaded()) {
                Entity entity = this.getBaseElementSpec().getEntity();
                entity.getTable().setName(this.name);
            }
        } else {
            setDefaultName();
        }

        if (SQLKeywords.isSQL99ReservedKeyword(SecondaryTableWidget.this.getName())) {
            this.getSignalManager().fire(ERROR, ClassValidator.CLASS_TABLE_NAME_WITH_RESERVED_SQL_KEYWORD);
        } else {
            this.getSignalManager().clear(ERROR, ClassValidator.CLASS_TABLE_NAME_WITH_RESERVED_SQL_KEYWORD);
        }

        DBMapping mapping = SecondaryTableWidget.this.getModelerScene().getBaseElementSpec();
        if (mapping.findAllTable(SecondaryTableWidget.this.getName()).size() > 1) {
            getSignalManager().fire(ERROR, ClassValidator.NON_UNIQUE_TABLE_NAME);
        } else {
            getSignalManager().clear(ERROR, ClassValidator.NON_UNIQUE_TABLE_NAME);
        }

    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        set.createPropertySet(this, this.getBaseElementSpec().getSecondaryTable(), getPropertyChangeListeners());
    }

    @Override
    protected List<JMenuItem> getPopupMenuItemList() {
        List<JMenuItem> menuList = super.getPopupMenuItemList();
        JMenuItem menuItem = new JMenuItem("Delete Secondary Table");
        menuItem.addActionListener((ActionEvent e) -> {
            Entity entity = this.getBaseElementSpec().getEntity();
            entity.getAttributes().getAllAttribute().stream().filter(a -> a instanceof PersistenceBaseAttribute)
                    .filter(a -> StringUtils.equalsIgnoreCase(((PersistenceBaseAttribute)a).getColumn().getTable(),this.getBaseElementSpec().getSecondaryTable().getName()))
                    .forEach(a -> ((PersistenceBaseAttribute)a).getColumn().setTable(null));
                entity.removeSecondaryTable(this.getBaseElementSpec().getSecondaryTable());
                ModelerFile parentFile = SecondaryTableWidget.this.getModelerScene().getModelerFile().getParentFile();
                DBUtil.openDBModeler(parentFile);
            JeddictLogger.recordDBAction("Delete Secondary Table");
        });
        menuList.add(0, menuItem);
        return menuList;
    }

    @Override
    public String getIconPath() {
        return SECONDARY_TABLE_ICON_PATH;
    }

    @Override
    public Image getIcon() {
        return SECONDARY_TABLE;
    }

}
