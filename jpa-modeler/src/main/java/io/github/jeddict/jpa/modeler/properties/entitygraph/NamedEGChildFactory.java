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
package io.github.jeddict.jpa.modeler.properties.entitygraph;

import java.util.List;
import io.github.jeddict.jpa.modeler.widget.EmbeddableWidget;
import io.github.jeddict.jpa.modeler.widget.PersistenceClassWidget;
import io.github.jeddict.jpa.modeler.widget.attribute.AttributeWidget;
import io.github.jeddict.jpa.modeler.widget.attribute.base.EmbeddedAttributeWidget;
import io.github.jeddict.jpa.modeler.widget.attribute.base.EmbeddedIdAttributeWidget;
import io.github.jeddict.jpa.modeler.widget.attribute.base.IdAttributeWidget;
import io.github.jeddict.jpa.modeler.widget.attribute.base.TransientAttributeWidget;
import io.github.jeddict.jpa.modeler.widget.attribute.base.VersionAttributeWidget;
import io.github.jeddict.jpa.modeler.widget.attribute.relation.RelationAttributeWidget;
import io.github.jeddict.jpa.modeler.navigator.nodes.CheckableAttributeNode;
import io.github.jeddict.jpa.modeler.navigator.nodes.TreeChildFactory;
import io.github.jeddict.jpa.modeler.navigator.nodes.TreeChildNode;
import io.github.jeddict.jpa.modeler.properties.entitygraph.nodes.EGInternalNode;
import io.github.jeddict.jpa.modeler.properties.entitygraph.nodes.EGLeafNode;
import io.github.jeddict.jpa.modeler.properties.entitygraph.nodes.EGRootNode;
import io.github.jeddict.jpa.spec.ManagedClass;
import io.github.jeddict.jpa.spec.NamedAttributeNode;
import io.github.jeddict.jpa.spec.NamedEntityGraph;
import io.github.jeddict.jpa.spec.NamedSubgraph;
import io.github.jeddict.jpa.spec.extend.Attribute;
import io.github.jeddict.jpa.spec.extend.RelationAttribute;
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
