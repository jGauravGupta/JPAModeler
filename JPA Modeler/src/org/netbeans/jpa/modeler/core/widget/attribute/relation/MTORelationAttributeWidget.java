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
package org.netbeans.jpa.modeler.core.widget.attribute.relation;

import java.awt.Image;
import org.netbeans.jpa.modeler.core.widget.flow.relation.MTORelationFlowWidget;
import org.netbeans.jpa.modeler.core.widget.flow.relation.RelationFlowWidget;
import org.netbeans.jpa.modeler.core.widget.relation.flow.direction.Unidirectional;
import org.netbeans.jpa.modeler.spec.ManyToOne;
import org.netbeans.jpa.modeler.specification.model.scene.JPAModelerScene;
import org.netbeans.jpa.modeler.specification.model.util.JPAModelerUtil;
import org.netbeans.modeler.widget.node.IPNodeWidget;
import org.netbeans.modeler.widget.pin.info.PinWidgetInfo;

/**
 *
 * @author Gaurav_Gupta
 */
public class MTORelationAttributeWidget extends SingleRelationAttributeWidget<ManyToOne> {

    private MTORelationFlowWidget manyToOneRelationFlowWidget;

    public MTORelationAttributeWidget(JPAModelerScene scene, IPNodeWidget nodeWidget, PinWidgetInfo pinWidgetInfo) {
        super(scene, nodeWidget, pinWidgetInfo);
    }


    /**
     * @return the manyToOneRelationFlowWidget
     */
    public MTORelationFlowWidget getManyToOneRelationFlowWidget() {
        return manyToOneRelationFlowWidget;
    }

    /**
     * @param manyToOneRelationFlowWidget the manyToOneRelationFlowWidget to set
     */
    public void setManyToOneRelationFlowWidget(MTORelationFlowWidget manyToOneRelationFlowWidget) {
        this.manyToOneRelationFlowWidget = manyToOneRelationFlowWidget;
        this.setImage(this.getIcon());
    }

    @Override
    public String getIconPath() {
        if (getBaseElementSpec().isPrimaryKey()) {
            if (manyToOneRelationFlowWidget instanceof Unidirectional) {
                return JPAModelerUtil.PK_UMTO_ATTRIBUTE_ICON_PATH;
            } else {
                return JPAModelerUtil.PK_BMTO_ATTRIBUTE_ICON_PATH;
            }
        } else if (manyToOneRelationFlowWidget instanceof Unidirectional) {
            return JPAModelerUtil.UMTO_ATTRIBUTE_ICON_PATH;
        } else {
            return JPAModelerUtil.BMTO_ATTRIBUTE_ICON_PATH;
        }
    }

    @Override
    public Image getIcon() {
        if (getBaseElementSpec().isPrimaryKey()) {
            if (manyToOneRelationFlowWidget instanceof Unidirectional) {
                return JPAModelerUtil.PK_UMTO_ATTRIBUTE;
            } else {
                return JPAModelerUtil.PK_BMTO_ATTRIBUTE;
            }
        } else if (manyToOneRelationFlowWidget instanceof Unidirectional) {
            return JPAModelerUtil.UMTO_ATTRIBUTE;
        } else {
            return JPAModelerUtil.BMTO_ATTRIBUTE;
        }
    }

    @Override
    public RelationFlowWidget getRelationFlowWidget() {
        return manyToOneRelationFlowWidget;
    }

}
