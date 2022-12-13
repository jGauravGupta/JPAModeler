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
package io.github.jeddict.jpa.modeler.widget.flow.association;

import io.github.jeddict.jpa.modeler.widget.BeanClassWidget;
import io.github.jeddict.jpa.modeler.initializer.JPAModelerScene;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.widget.IFlowElementWidget;
import org.netbeans.modeler.widget.edge.info.EdgeWidgetInfo;

/**
 *
 * @author Gaurav_Gupta
 */
public class UMTMAssociationFlowWidget extends MTMAssociationFlowWidget implements UnidirectionalAssociation {

    private BeanClassWidget targetJavaClass;

    public UMTMAssociationFlowWidget(JPAModelerScene scene, EdgeWidgetInfo edge) {
        super(scene, edge);
    }

    /**
     * @return the targetJavaClass
     */
    @Override
    public BeanClassWidget getTargetClassWidget() {
        return targetJavaClass;
    }

    /**
     * @param targetJavaClass the targetJavaClass to set
     */
    @Override
    public void setTargetClassWidget(BeanClassWidget targetJavaClass) {
        this.targetJavaClass = targetJavaClass;
        if (targetJavaClass != null) {
            targetJavaClass.addUnidirectionalAssociationFlowWidget(this);
        }
    }

    @Override
    public IFlowElementWidget getTargetWidget() {
        return targetJavaClass;
    }

    @Override
    public void destroy() {
        if (targetJavaClass != null) {
            targetJavaClass.removeUnidirectionalAssociationFlowWidget(this);
        }
    }

}
