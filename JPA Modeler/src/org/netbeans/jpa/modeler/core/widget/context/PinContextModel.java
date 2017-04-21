/**
 * Copyright [2013] Gaurav Gupta
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
package org.netbeans.jpa.modeler.core.widget.context;

import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;
import org.netbeans.jpa.modeler.core.widget.JavaClassWidget;
import org.netbeans.jpa.modeler.core.widget.PersistenceClassWidget;
import org.netbeans.jpa.modeler.core.widget.PrimaryKeyContainerWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.AttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.BasicAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.BasicCollectionAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.EmbeddedAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.IdAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.MultiValueEmbeddedAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.SingleValueEmbeddedAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.TransientAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.base.VersionAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.relation.MTMRelationAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.relation.MTORelationAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.relation.OTMRelationAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.relation.OTORelationAttributeWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.relation.RelationAttributeWidget;
import org.netbeans.jpa.modeler.spec.IdentifiableClass;
import org.netbeans.jpa.modeler.spec.ManagedClass;
import org.netbeans.jpa.modeler.spec.extend.IPersistenceAttributes;
import org.netbeans.jpa.modeler.spec.extend.JavaClass;
import org.netbeans.jpa.modeler.specification.model.scene.JPAModelerScene;
import static org.netbeans.jpa.modeler.specification.model.util.JPAModelerUtil.DELETE_ICON;
import static org.netbeans.jpa.modeler.specification.model.util.JPAModelerUtil.DOWN_ICON;
import static org.netbeans.jpa.modeler.specification.model.util.JPAModelerUtil.PAINT_ICON;
import static org.netbeans.jpa.modeler.specification.model.util.JPAModelerUtil.UP_ICON;
import org.netbeans.jpa.modeler.specification.model.workspace.HighlightWidgetDialog;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.widget.context.ContextPaletteButtonModel;
import org.netbeans.modeler.widget.context.ContextPaletteModel;
import org.netbeans.modeler.widget.context.base.DefaultContextPaletteModel;
import org.netbeans.modeler.widget.context.base.DefaultPaletteButtonModel;
import org.netbeans.modeler.widget.node.IWidget;
import org.netbeans.modeler.widget.pin.IPinWidget;

public class PinContextModel {

    public static MouseListener getPopupMenuLstener(final javax.swing.JPopupMenu addWidgetPopupMenu) {

        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Rectangle rec = evt.getComponent().getBounds();
                addWidgetPopupMenu.show(evt.getComponent(), (int) (rec.getX() + rec.getWidth()), 0);
            }
        };
    }

    public static ContextPaletteModel getContextPaletteModel(IPinWidget pinWidget) {
        ContextPaletteModel contextPaletteModel = new DefaultContextPaletteModel(pinWidget);
        addMoveModel(contextPaletteModel, pinWidget);
        addHighlightWdgetModel(contextPaletteModel, pinWidget);
        addDeleteModel(contextPaletteModel, pinWidget);
        return contextPaletteModel;
    }

    private static void addHighlightWdgetModel(ContextPaletteModel contextPaletteModel, IPinWidget pinWidget) {
        ContextPaletteButtonModel highlightModel = new DefaultPaletteButtonModel();
        highlightModel.setImage(PAINT_ICON.getImage());
        highlightModel.setTooltip("Highlight");
        highlightModel.setPaletteModel(contextPaletteModel);
        highlightModel.setMouseListener(getHighlightWidgetAction(pinWidget));
        contextPaletteModel.getChildren().add(highlightModel);
    }    
    
    private static void addDeleteModel(ContextPaletteModel contextPaletteModel, IPinWidget pinWidget) {
        ContextPaletteButtonModel deleteModel = new DefaultPaletteButtonModel();
        deleteModel.setImage(DELETE_ICON.getImage());
        deleteModel.setTooltip("Delete");
        deleteModel.setPaletteModel(contextPaletteModel);
        deleteModel.setMouseListener(getRemoveWidgetAction(pinWidget));
        contextPaletteModel.getChildren().add(deleteModel);
    }

    private static void addMoveModel(ContextPaletteModel contextPaletteModel, IPinWidget pinWidget) {
        ContextPaletteButtonModel upModel = new DefaultPaletteButtonModel();
        upModel.setImage(UP_ICON);
        upModel.setTooltip("Move Up");
        upModel.setPaletteModel(contextPaletteModel);
        upModel.setMouseListener(getMoveUpWidgetAction(pinWidget, -1));
        upModel.checkVisibility(() -> checkMoveWidgetVisibility(pinWidget, -1));
        contextPaletteModel.getChildren().add(upModel);

        ContextPaletteButtonModel downModel = new DefaultPaletteButtonModel();
        downModel.setImage(DOWN_ICON);
        downModel.setTooltip("Move Down");
        downModel.setPaletteModel(contextPaletteModel);
        downModel.setMouseListener(getMoveUpWidgetAction(pinWidget, 1));
        downModel.checkVisibility(() -> checkMoveWidgetVisibility(pinWidget, 1));
        contextPaletteModel.getChildren().add(downModel);
    }

    private static MouseListener getHighlightWidgetAction(final IPinWidget widget) {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NBModelerUtil.hideContextPalette(widget.getModelerScene());
                HighlightWidgetDialog widgetDialog = new HighlightWidgetDialog(widget, widget.getTextDesign());
                widgetDialog.setVisible(true);
            }
        };
    }
    private static MouseListener getMoveUpWidgetAction(final IPinWidget widget, final int distance) {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (widget instanceof AttributeWidget) {
                    JavaClassWidget<JavaClass> classWidget = ((AttributeWidget) widget).getClassWidget();
                    List list = null;
                    List specList = null;
                    AttributeWidget attributeWidget = (AttributeWidget) widget;
                    
                    if (classWidget instanceof PersistenceClassWidget) {
                        PersistenceClassWidget<? extends ManagedClass<? extends IPersistenceAttributes>> persistenceClassWidget = (PersistenceClassWidget) classWidget;
                        if (attributeWidget instanceof EmbeddedAttributeWidget) {
                            if (attributeWidget instanceof SingleValueEmbeddedAttributeWidget) {
                                list = persistenceClassWidget.getSingleValueEmbeddedAttributeWidgets();
                                specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getEmbedded();
                            } else if (attributeWidget instanceof MultiValueEmbeddedAttributeWidget) {
                                list = persistenceClassWidget.getMultiValueEmbeddedAttributeWidgets();
                                specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getElementCollection();
                            }
                        } else if (attributeWidget instanceof BasicAttributeWidget) {
                            list = persistenceClassWidget.getBasicAttributeWidgets();
                            specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getBasic();
                        } else if (attributeWidget instanceof BasicCollectionAttributeWidget) {
                            list = persistenceClassWidget.getBasicCollectionAttributeWidgets();
                            specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getElementCollection();
                        } else if (attributeWidget instanceof RelationAttributeWidget) {
                            if (attributeWidget instanceof OTORelationAttributeWidget) {
                                list = persistenceClassWidget.getOneToOneRelationAttributeWidgets();
                                specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getOneToOne();
                            } else if (attributeWidget instanceof OTMRelationAttributeWidget) {
                                list = persistenceClassWidget.getOneToManyRelationAttributeWidgets();
                                specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getOneToMany();
                            } else if (attributeWidget instanceof MTORelationAttributeWidget) {
                                list = persistenceClassWidget.getManyToOneRelationAttributeWidgets();
                                specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getManyToOne();
                            } else if (attributeWidget instanceof MTMRelationAttributeWidget) {
                                list = persistenceClassWidget.getManyToManyRelationAttributeWidgets();
                                specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getManyToMany();
                            }
                        } else if (attributeWidget instanceof TransientAttributeWidget) {
                            list = persistenceClassWidget.getTransientAttributeWidgets();
                            specList = persistenceClassWidget.getBaseElementSpec().getAttributes().getTransient();
                        } else if (classWidget instanceof PrimaryKeyContainerWidget) {
                            PrimaryKeyContainerWidget<? extends IdentifiableClass> primaryKeyContainerWidget = (PrimaryKeyContainerWidget) classWidget;
                            if (attributeWidget instanceof IdAttributeWidget) {
                                list = primaryKeyContainerWidget.getIdAttributeWidgets();
                                specList = primaryKeyContainerWidget.getBaseElementSpec().getAttributes().getId();
                            } else if (attributeWidget instanceof VersionAttributeWidget) {
                                list = primaryKeyContainerWidget.getVersionAttributeWidgets();
                                specList = primaryKeyContainerWidget.getBaseElementSpec().getAttributes().getVersion();
                            }
                        }
                    }
                            
                    int index = list.indexOf(attributeWidget);
                    if ((index == 0 && distance < 0) || (list.size() == index + 1 && distance > 0)) {
                        return;
                    }

                    if ((index == 1 && distance < 0) || (list.size() == index + 2 && distance > 0)) {  //if just before the last/first then hide context palette
                        NBModelerUtil.hideContextPalette(widget.getModelerScene());
                    }
                    Collections.swap(list, index, index + distance);

                    int specIndex = specList.indexOf(attributeWidget.getBaseElementSpec());
                    Collections.swap(specList, specIndex, specIndex + distance);

                    classWidget.sortAttributes();

                }

//                widget.remove(true);
//                NBModelerUtil.hideContextPalette(widget.getModelerScene());
                widget.getModelerScene().getModelerPanelTopComponent().changePersistenceState(false);
            }
        };
    }

    private static boolean checkMoveWidgetVisibility(final IPinWidget widget, final int distance) {
        if (widget instanceof AttributeWidget) {
            JavaClassWidget<JavaClass> classWidget = ((AttributeWidget) widget).getClassWidget();
            List list = null;
            AttributeWidget attributeWidget = (AttributeWidget) widget;

            if (classWidget instanceof PersistenceClassWidget) {
                PersistenceClassWidget<? extends ManagedClass<? extends IPersistenceAttributes>> persistenceClassWidget = (PersistenceClassWidget)classWidget;
                if (attributeWidget instanceof EmbeddedAttributeWidget) {
                    if (attributeWidget instanceof SingleValueEmbeddedAttributeWidget) {
                        list = persistenceClassWidget.getSingleValueEmbeddedAttributeWidgets();
                    } else if (attributeWidget instanceof MultiValueEmbeddedAttributeWidget) {
                        list = persistenceClassWidget.getMultiValueEmbeddedAttributeWidgets();
                    }
                } else if (attributeWidget instanceof BasicAttributeWidget) {
                    list = persistenceClassWidget.getBasicAttributeWidgets();
                } else if (attributeWidget instanceof BasicCollectionAttributeWidget) {
                    list = persistenceClassWidget.getBasicCollectionAttributeWidgets();
                } else if (attributeWidget instanceof RelationAttributeWidget) {
                    if (attributeWidget instanceof OTORelationAttributeWidget) {
                        list = persistenceClassWidget.getOneToOneRelationAttributeWidgets();
                    } else if (attributeWidget instanceof OTMRelationAttributeWidget) {
                        list = persistenceClassWidget.getOneToManyRelationAttributeWidgets();
                    } else if (attributeWidget instanceof MTORelationAttributeWidget) {
                        list = persistenceClassWidget.getManyToOneRelationAttributeWidgets();
                    } else if (attributeWidget instanceof MTMRelationAttributeWidget) {
                        list = persistenceClassWidget.getManyToManyRelationAttributeWidgets();
                    }
                } else if ((attributeWidget instanceof VersionAttributeWidget) && (classWidget instanceof PrimaryKeyContainerWidget)) {
                    list = ((PrimaryKeyContainerWidget) classWidget).getVersionAttributeWidgets();
                } else if (attributeWidget instanceof TransientAttributeWidget) {
                    list = persistenceClassWidget.getTransientAttributeWidgets();
                } else if (classWidget instanceof PrimaryKeyContainerWidget) {
                    PrimaryKeyContainerWidget<? extends IdentifiableClass> primaryKeyContainerWidget = (PrimaryKeyContainerWidget) classWidget;
                    if (attributeWidget instanceof IdAttributeWidget) {
                        list = primaryKeyContainerWidget.getIdAttributeWidgets();
                    } else if (attributeWidget instanceof VersionAttributeWidget) {
                        list = primaryKeyContainerWidget.getVersionAttributeWidgets();
                    }
                }
            }
            
            if (list == null) {
                return false;
            }
            int index = list.indexOf(attributeWidget);
            if ((index == 0 && distance < 0) || (list.size() == index + 1 && distance > 0)) {
                return false;
            }
        }

        return true;

    }

    private static MouseListener getRemoveWidgetAction(final IPinWidget widget) {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                widget.remove(true);
                NBModelerUtil.hideContextPalette(widget.getModelerScene());
                widget.getModelerScene().getModelerPanelTopComponent().changePersistenceState(false);
            }
        };
    }

    public static MouseListener getFocusWidgetAction(final IWidget widget) {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPAModelerScene modelerScene = (JPAModelerScene) widget.getScene();

                if (widget instanceof JavaClassWidget) {
                    JavaClassWidget classWidget = (JavaClassWidget) widget;
                    if (modelerScene.getHighlightedWidget() == classWidget) {
                        classWidget.hideInheritancePath();
                        modelerScene.setHighlightedWidget(null);
                    } else {
                        PinContextModel.manageHightlight(modelerScene, widget);
                        classWidget.showInheritancePath();
                    }
                } else if (widget instanceof SingleValueEmbeddedAttributeWidget) {
                    SingleValueEmbeddedAttributeWidget embeddedAttributeWidget = (SingleValueEmbeddedAttributeWidget) widget;
                    if (modelerScene.getHighlightedWidget() == embeddedAttributeWidget) {
                        embeddedAttributeWidget.hideCompositionPath();
                        modelerScene.setHighlightedWidget(null);
                    } else {
                        PinContextModel.manageHightlight(modelerScene, widget);
                        embeddedAttributeWidget.showCompositionPath();
                    }

                }
                NBModelerUtil.hideContextPalette((IModelerScene) widget.getScene());
            }
        };
    }

    private static void manageHightlight(JPAModelerScene modelerScene, IWidget widget) {
        if (modelerScene.getHighlightedWidget() != null) {
            if (modelerScene.getHighlightedWidget() instanceof JavaClassWidget) {
                ((JavaClassWidget) modelerScene.getHighlightedWidget()).hideInheritancePath();
            } else if (modelerScene.getHighlightedWidget() instanceof EmbeddedAttributeWidget) {
                ((EmbeddedAttributeWidget) modelerScene.getHighlightedWidget()).hideCompositionPath();
            }
        }
        modelerScene.setHighlightedWidget(widget);
    }

}
