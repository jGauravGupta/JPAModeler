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
package io.github.jeddict.jpa.modeler.widget.attribute.relation;

import io.github.jeddict.jpa.modeler.widget.EntityWidget;
import io.github.jeddict.jpa.modeler.widget.PrimaryKeyContainerWidget;
import io.github.jeddict.jpa.modeler.rules.attribute.AttributeValidator;
import io.github.jeddict.jpa.spec.extend.SingleRelationAttribute;
import io.github.jeddict.jpa.modeler.initializer.JPAModelerScene;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.widget.node.IPNodeWidget;
import org.netbeans.modeler.widget.pin.info.PinWidgetInfo;

/**
 *
 * @author Gaurav_Gupta
 */
public abstract class SingleRelationAttributeWidget<E extends SingleRelationAttribute> extends RelationAttributeWidget<E> {

    public SingleRelationAttributeWidget(JPAModelerScene scene, IPNodeWidget nodeWidget, PinWidgetInfo pinWidgetInfo) {
        super(scene, nodeWidget, pinWidgetInfo);
    }

    @Override
    public void onConnection() {
        compile();
    }

    public void compile() {
        this.getClassWidget().sortAttributes();
        this.getClassWidget().scanDuplicateAttributes(null, this.getBaseElementSpec().getName());
        if (this.getBaseElementSpec().isPrimaryKey()) {
            AttributeValidator.validateEmbeddedIdAndIdFound(this.getClassWidget());
            if (this.getClassWidget() instanceof EntityWidget) {
                ((EntityWidget) this.getClassWidget()).scanKeyError();
            }
            this.getClassWidget().getAllSubclassWidgets()
                    .stream()
                    .filter((classWidget) -> (classWidget instanceof EntityWidget))
                    .forEach((classWidget) -> {
                        ((EntityWidget) classWidget).scanKeyError();
                    });
            if (this.getClassWidget() instanceof PrimaryKeyContainerWidget) {
                ((PrimaryKeyContainerWidget) this.getClassWidget()).isCompositePKPropertyAllow();//to update default CompositePK class , type //for manual created attribute
            }
        }
    }
}
