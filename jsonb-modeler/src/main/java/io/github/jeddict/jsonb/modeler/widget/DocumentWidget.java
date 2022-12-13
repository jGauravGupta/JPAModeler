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
package io.github.jeddict.jsonb.modeler.widget;

import io.github.jeddict.jpa.modeler.initializer.JPAFileActionListener;
import io.github.jeddict.jpa.modeler.initializer.JPAModelerScene;
import io.github.jeddict.jpa.modeler.widget.FlowNodeWidget;
import static io.github.jeddict.jpa.modeler.widget.JavaClassWidget.getFileObject;
import io.github.jeddict.jpa.modeler.widget.OpenSourceCodeAction;
import io.github.jeddict.jpa.spec.ElementCollection;
import io.github.jeddict.jpa.spec.Embedded;
import io.github.jeddict.jpa.spec.extend.Attribute;
import io.github.jeddict.jpa.spec.extend.JavaClass;
import io.github.jeddict.jpa.spec.extend.RelationAttribute;
import io.github.jeddict.jsonb.modeler.initializer.JSONBModelerScene;
import static io.github.jeddict.jsonb.modeler.initializer.JSONBModelerUtil.JSON_DOCUMENT;
import static io.github.jeddict.jsonb.modeler.initializer.JSONBModelerUtil.JSON_DOCUMENT_ICON_PATH;
import static io.github.jeddict.jsonb.modeler.properties.PropertiesHandler.getJsonbTypeAdapter;
import static io.github.jeddict.jsonb.modeler.properties.PropertiesHandler.getJsonbTypeDeserializer;
import static io.github.jeddict.jsonb.modeler.properties.PropertiesHandler.getJsonbTypeSerializer;
import static io.github.jeddict.jsonb.modeler.properties.PropertiesHandler.getJsonbVisibility;
import io.github.jeddict.jsonb.modeler.spec.JSONBDocument;
import io.github.jeddict.jsonb.modeler.spec.JSONBNode;
import io.github.jeddict.jsonb.modeler.widget.context.DocumentContextModel;
import java.awt.Cursor;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import javax.swing.JMenuItem;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modeler.anchors.CustomRectangularAnchor;
import org.netbeans.modeler.config.palette.SubCategoryNodeConfig;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.IRootElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.context.ContextPaletteModel;
import org.netbeans.modeler.widget.node.INodeWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;

public class DocumentWidget extends FlowNodeWidget<JSONBDocument, JSONBModelerScene> {

    private final Map<String, BranchNodeWidget> branchNodeWidgets = new HashMap<>();
    private final Map<String, LeafNodeWidget> leafNodeWidgets = new HashMap<>();

    private GeneralizationFlowWidget outgoingGeneralizationFlowWidget;
    private final List<GeneralizationFlowWidget> incomingGeneralizationFlowWidgets = new ArrayList<>();

    public DocumentWidget(JSONBModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
        this.setImage(this.getNodeWidgetInfo().getModelerDocument().getImage());
    }

    @Override
    public void init() {
        super.init();
        addOpenSourceCodeAction();
    }

    protected void addOpenSourceCodeAction() {
        this.getImageWidget().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.getImageWidget().getActions().addAction(
                new OpenSourceCodeAction(
                        () -> getFileObject(
                                this.getBaseElementSpec().getJavaClass(),
                                this.getModelerScene().getModelerFile().getParentFile()
                        ),
                        this.getBaseElementSpec().getJavaClass(),
                        this.getModelerScene().getModelerFile().getParentFile()
                )
        );
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        JavaClass javaClass = this.getBaseElementSpec().getJavaClass();
        JPAModelerScene parentScene = (JPAModelerScene) this.getModelerScene().getModelerFile().getParentFile().getModelerScene();
        set.put("JSONB_PROP", getJsonbTypeAdapter(javaClass, this, parentScene));
        set.put("JSONB_PROP", getJsonbTypeSerializer(javaClass, this, parentScene));
        set.put("JSONB_PROP", getJsonbTypeDeserializer(javaClass, this, parentScene));
        set.put("JSONB_PROP", getJsonbVisibility(javaClass, this, parentScene));
    }

    public void addBranchNode(String name, JSONBNode node) {
        branchNodeWidgets.put(node.getId(), createPinWidget(node, BranchNodeWidget.class, w -> new BranchNodeWidget(this.getModelerScene(), this, w)));
    }

    public void addLeafNode(String name, JSONBNode node) {
        leafNodeWidgets.put(node.getId(), createPinWidget(node, LeafNodeWidget.class, w -> new LeafNodeWidget(this.getModelerScene(), this, w)));
    }

    @Override
    public void setLabel(String label) {
        if (label != null && !label.trim().isEmpty()) {
            this.setNodeName(label.replaceAll("\\s+", ""));
        }
    }

    public List<JSONNodeWidget> getAllNodeWidgets() {
        List<JSONNodeWidget> nodes = new ArrayList<>(branchNodeWidgets.values());
        nodes.addAll(leafNodeWidgets.values());
        return nodes;
    }

    /**
     * @return the leafNodeWidgets
     */
    public LeafNodeWidget getLeafNodeWidget(String id) {
        return leafNodeWidgets.get(id);
    }

    /**
     * @return the leafNodeWidgets
     */
    public Collection<LeafNodeWidget> getLeafNodeWidgets() {
        return leafNodeWidgets.values();
    }

    /**
     * @return the branchNodeWidgets
     */
    public BranchNodeWidget getBranchNodeWidget(String id) {
        return branchNodeWidgets.get(id);
    }

    /**
     * @return the branchNodeWidgets
     */
    public Collection<BranchNodeWidget> getBranchNodeWidgets() {
        return branchNodeWidgets.values();
    }

    public void removeLeafNodeWidget(LeafNodeWidget key) {
        leafNodeWidgets.remove(key.getId());
    }

    public void removeBranchNodeWidget(BranchNodeWidget key) {
        branchNodeWidgets.remove(key.getId());
    }

    /**
     * @return the JSONNodeWidget
     */
    public JSONNodeWidget getJSONNodeWidget(Attribute attr) {
        if ((attr instanceof RelationAttribute && ((RelationAttribute) attr).getConnectedEntity() != null)
                || (attr instanceof Embedded && ((Embedded) attr).getConnectedClass() != null)
                || (attr instanceof ElementCollection && ((ElementCollection) attr).getConnectedClass() != null)) {
            return branchNodeWidgets.get(attr.getId());
        } else {
            return leafNodeWidgets.get(attr.getId());
        }
    }

    @Override
    public ContextPaletteModel getContextPaletteModel() {
        if (contextPaletteModel == null) {
            contextPaletteModel = DocumentContextModel.getContextPaletteModel(this);
        }
        return contextPaletteModel;
    }

    @Override
    protected List<JMenuItem> getPopupMenuItemList() {
        List<JMenuItem> menuItemList = new LinkedList<>();

        JMenuItem drive = new JMenuItem("Drive to JavaClass");
        drive.addActionListener(e -> {
            JSONBDocument document = DocumentWidget.this.getBaseElementSpec();
            JavaClass javaClass = document.getJavaClass();
            ModelerFile modelerFile = DocumentWidget.this.getModelerScene().getModelerFile();
            ModelerFile parentModelerFile = modelerFile.getParentFile();
            IModelerScene<IRootElement> parentScene = parentModelerFile.getModelerScene();
            parentScene.getBaseElements().stream()
                    .filter(w -> w.getBaseElementSpec() == javaClass)
                    .findAny()
                    .ifPresent(widget -> {
                        JPAFileActionListener.open(parentModelerFile);
                        parentModelerFile.getModelerDiagramEngine().moveToWidget((INodeWidget) widget);
                    });
        });

        menuItemList.add(drive);
        menuItemList.add(getPropertyMenu());

        return menuItemList;
    }

    @Override
    public void createPinWidget(SubCategoryNodeConfig subCategoryInfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<DocumentWidget> getAllSuperclassWidget() {
        List<DocumentWidget> superclassWidgetList = new LinkedList<>();
        boolean exist = false;
        GeneralizationFlowWidget generalizationFlowWidget_TMP = this.outgoingGeneralizationFlowWidget;
        if (generalizationFlowWidget_TMP != null) {
            exist = true;
        }
        while (exist) {
            DocumentWidget superclassWidget_Next = generalizationFlowWidget_TMP.getSuperclassWidget();
            superclassWidgetList.add(superclassWidget_Next);
            generalizationFlowWidget_TMP = superclassWidget_Next.getOutgoingGeneralizationFlowWidget();
            if (generalizationFlowWidget_TMP == null) {
                exist = false;
            }
        }
        return superclassWidgetList;
    }

    public List<DocumentWidget> getSubclassWidgets() {
        List<DocumentWidget> subclassWidgetList = new LinkedList<>();
        for (GeneralizationFlowWidget generalizationFlowWidget_TMP : this.incomingGeneralizationFlowWidgets) {
            DocumentWidget subclassWidget_Nest = generalizationFlowWidget_TMP.getSubclassWidget();
            subclassWidgetList.add(subclassWidget_Nest);
        }
        return subclassWidgetList;
    }

    public List<DocumentWidget> getAllSubclassWidgets() {
        List<DocumentWidget> subclassWidgetList = new LinkedList<>();
        for (GeneralizationFlowWidget generalizationFlowWidget_TMP : this.incomingGeneralizationFlowWidgets) {
            DocumentWidget subclassWidget_Nest = generalizationFlowWidget_TMP.getSubclassWidget();
            subclassWidgetList.add(subclassWidget_Nest);
            subclassWidgetList.addAll(subclassWidget_Nest.getAllSubclassWidgets());
        }
        return subclassWidgetList;
    }

    /**
     * @return the outgoingGeneralizationFlowWidget
     */
    public GeneralizationFlowWidget getOutgoingGeneralizationFlowWidget() {
        return outgoingGeneralizationFlowWidget;
    }

    /**
     * @param outgoingGeneralizationFlowWidget the
     * outgoingGeneralizationFlowWidget to set
     */
    public void setOutgoingGeneralizationFlowWidget(GeneralizationFlowWidget outgoingGeneralizationFlowWidget) {
        this.outgoingGeneralizationFlowWidget = outgoingGeneralizationFlowWidget;
    }

    /**
     * @return the incomingGeneralizationFlowWidgets
     */
    public List<GeneralizationFlowWidget> getIncomingGeneralizationFlowWidgets() {
        return incomingGeneralizationFlowWidgets;
    }

    public void addIncomingGeneralizationFlowWidget(GeneralizationFlowWidget generalizationFlowWidget) {
        incomingGeneralizationFlowWidgets.add(generalizationFlowWidget);
    }

    public void removeIncomingGeneralizationFlowWidget(GeneralizationFlowWidget generalizationFlowWidget) {
        incomingGeneralizationFlowWidgets.remove(generalizationFlowWidget);
    }

    @Override
    public String getIconPath() {
        return JSON_DOCUMENT_ICON_PATH;
    }

    @Override
    public Image getIcon() {
        return JSON_DOCUMENT;
    }

    public void sortNodes() {
        this.sortPins(getNodeCategories());
    }

    public Map<String, List<Widget>> getNodeCategories() {
        JavaClass javaClass = this.getBaseElementSpec().getJavaClass();
        List<Attribute> attributes;
        if (javaClass.getJsonbPropertyOrder().isEmpty()) {
            attributes = javaClass.getAttributes().getAllAttribute();
            attributes.sort(Comparator.comparing(Attribute::getName));
        } else {
            attributes = javaClass.getAllJsonbPropertyOrder();
        }
        return Collections.singletonMap("", attributes.stream()
                .map(attr -> getJSONNodeWidget(attr))
                .collect(toList()));
    }

    @Override
    public Anchor getAnchor() {
        return new CustomRectangularAnchor(this, -5, true);
    }
}
