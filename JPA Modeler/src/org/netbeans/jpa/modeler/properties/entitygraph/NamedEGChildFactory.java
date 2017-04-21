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
package org.netbeans.jpa.modeler.properties.entitygraph;

import org.netbeans.jpa.modeler.navigator.nodes.CheckableAttributeNode;
import org.netbeans.jpa.modeler.navigator.nodes.TreeChildFactory;
import java.util.List;
import org.netbeans.jpa.modeler.core.widget.EmbeddableWidget;
import org.netbeans.jpa.modeler.core.widget.PersistenceClassWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.AttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.EmbeddedAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.EmbeddedIdAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.IdAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.TransientAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.VersionAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.relation.RelationAttributeWidget;
import org.netbeans.jpa.modeler.properties.entitygraph.nodes.EGInternalNode;
import org.netbeans.jpa.modeler.properties.entitygraph.nodes.EGLeafNode;
import org.netbeans.jpa.modeler.properties.entitygraph.nodes.EGRootNode;
import org.netbeans.jpa.modeler.navigator.nodes.TreeChildNode;
import org.netbeans.jpa.modeler.spec.ManagedClass;
import org.netbeans.jpa.modeler.spec.NamedAttributeNode;
import org.netbeans.jpa.modeler.spec.NamedEntityGraph;
import org.netbeans.jpa.modeler.spec.NamedSubgraph;
import org.netbeans.jpa.modeler.spec.extend.Attribute;
import org.netbeans.jpa.modeler.spec.extend.RelationAttribute;
import org.openide.nodes.Node;

public class NamedEGChildFactory extends TreeChildFactory<NamedEntityGraph, AttributeWidget> {

    @Override
    protected boolean createKeys(List<AttributeWidget> attributeWidgets) {
        PersistenceClassWidget<? extends ManagedClass> classWidget = null;
        if (parentNode instanceof EGRootNode) {
            classWidget = ((EGRootNode) parentNode).getRootWidget();
        } else if (parentNode instanceof EGInternalNode) {
            classWidget = ((EGInternalNode) parentNode).getParentWidget();
        }
        if (classWidget != null) {
            for (AttributeWidget attributeWidget : classWidget.getAllSortedAttributeWidgets()) {
                if (attributeWidget instanceof TransientAttributeWidget) {
                    // skip
                } else {//check for all remaining
                    attributeWidgets.add(attributeWidget);
                }
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(final AttributeWidget attributeWidget) {
        TreeChildNode childNode;
        CheckableAttributeNode checkableNode = new CheckableAttributeNode();
        NamedSubgraph subgraph = null;

        boolean isPK = attributeWidget instanceof IdAttributeWidget || attributeWidget instanceof EmbeddedIdAttributeWidget || attributeWidget instanceof VersionAttributeWidget;

        if (parentNode.getBaseElementSpec() != null && !isPK) {
            Attribute attribute = (Attribute) attributeWidget.getBaseElementSpec();
            NamedAttributeNode namedAttributeNode = null;
            if (parentNode instanceof EGRootNode) {
                namedAttributeNode = parentNode.getBaseElementSpec().findNamedAttributeNode(attribute.getName());
            } else if (parentNode instanceof EGInternalNode && ((EGInternalNode) parentNode).getSubgraph() != null) {
                namedAttributeNode = ((EGInternalNode) parentNode).getSubgraph().findNamedAttributeNode(attribute.getName());
            }

            if (namedAttributeNode != null) {
                checkableNode.setSelected(Boolean.TRUE);
                if (namedAttributeNode.getSubgraph() != null && !namedAttributeNode.getSubgraph().isEmpty()) {
                    subgraph = parentNode.getBaseElementSpec().findSubgraph(namedAttributeNode.getSubgraph());
                }
            }
        }

        if (attributeWidget instanceof EmbeddedAttributeWidget) {
            EmbeddedAttributeWidget embeddedAttributeWidget = (EmbeddedAttributeWidget) attributeWidget;
            EmbeddableWidget embeddableWidget = embeddedAttributeWidget.getEmbeddableFlowWidget().getTargetEmbeddableWidget();

            NamedEGChildFactory childFactory = new NamedEGChildFactory();//parentWidget, embeddedAttributeWidget, embeddableWidget );
            childNode = new EGInternalNode(embeddableWidget, embeddedAttributeWidget, parentNode.getBaseElementSpec(), subgraph, childFactory, checkableNode);
        } else if (attributeWidget instanceof RelationAttributeWidget) {
            RelationAttributeWidget<RelationAttribute> relationAttributeWidget = (RelationAttributeWidget) attributeWidget;
            PersistenceClassWidget connectedEntityWidget = relationAttributeWidget.getConnectedClassWidget();

            NamedEGChildFactory childFactory = new NamedEGChildFactory();//parentWidget, relationAttributeWidget, targetEntityWidget);
            childNode = new EGInternalNode(connectedEntityWidget, relationAttributeWidget, parentNode.getBaseElementSpec(), subgraph, childFactory, checkableNode);
        } else {
            childNode = new EGLeafNode(attributeWidget, parentNode.getBaseElementSpec(), checkableNode);
        }
        childNode.setParent(parentNode);
        parentNode.addChild(childNode);
        childNode.init();

        if (isPK) {
            checkableNode.setCheckEnabled(Boolean.FALSE);
            checkableNode.setEnableWithParent(Boolean.TRUE);
        }

        return (Node) childNode;
    }

}
