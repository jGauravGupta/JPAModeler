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
package io.github.jeddict.jpa.modeler.specification.model.workspace;

import static java.util.Collections.singletonMap;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import static javax.swing.SwingUtilities.invokeLater;
import io.github.jeddict.analytics.JeddictLogger;
import io.github.jeddict.jpa.modeler.widget.JavaClassWidget;
import io.github.jeddict.jpa.spec.EntityMappings;
import io.github.jeddict.jpa.spec.extend.Attribute;
import io.github.jeddict.jpa.spec.extend.IAttributes;
import io.github.jeddict.jpa.spec.extend.JavaClass;
import io.github.jeddict.jpa.spec.workspace.WorkSpace;
import io.github.jeddict.jpa.spec.workspace.WorkSpaceElement;
import io.github.jeddict.jpa.spec.workspace.WorkSpaceItem;
import io.github.jeddict.jpa.modeler.specification.model.file.JPAFileDataObject;
import io.github.jeddict.jpa.modeler.initializer.JPAFileActionListener;
import io.github.jeddict.jpa.modeler.initializer.JPAModelerScene;
import static io.github.jeddict.jpa.modeler.initializer.JPAModelerUtil.CREATE_ICON;
import static io.github.jeddict.jpa.modeler.initializer.JPAModelerUtil.DELETE_ALL_ICON;
import static io.github.jeddict.jpa.modeler.initializer.JPAModelerUtil.DELETE_ICON;
import static io.github.jeddict.jpa.modeler.initializer.JPAModelerUtil.EDIT_ICON;
import static io.github.jeddict.jpa.modeler.initializer.JPAModelerUtil.HOME_ICON;
import static io.github.jeddict.jpa.modeler.initializer.JPAModelerUtil.WORKSPACE_ICON;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.widget.design.NodeTextDesign;
import org.netbeans.modeler.widget.design.PinTextDesign;
import org.openide.util.NbBundle;
import static org.openide.util.NbBundle.getMessage;
import org.openide.windows.WindowManager;

/**
 *
 * @author jGauravGupta
 */
public class WorkSpaceManager {
    
    private final JMenu workSpaceMenu;
    private final JPAModelerScene scene;
    public final static String WORK_SPACE = "WORK_SPACE";
    public final static String MAIN_WORK_SPACE = "Main";
    
    public WorkSpaceManager(JPAModelerScene scene) {
        this.scene = scene;
        workSpaceMenu = new JMenu(getMessage(WorkSpaceManager.class, "WorkSpaceManager.workspace"));
        workSpaceMenu.setIcon(WORKSPACE_ICON);
    }
    
    public void openWorkSpace(boolean force, WorkSpace workSpace) {
        ModelerFile file = scene.getModelerFile();
        boolean reload = force;
        if (!force) {
            int option = JOptionPane.showConfirmDialog(
                    WindowManager.getDefault().getMainWindow(),
                    getMessage(WorkSpaceManager.class, "WorkSpaceManager.open.content", workSpace.getName()),
                    getMessage(WorkSpaceManager.class, "WorkSpaceManager.open.title"),
                    YES_NO_OPTION
            );
            reload = option == OK_OPTION;
        }
        if (reload) {
            invokeLater(() -> {
                file.getModelerPanelTopComponent().close();
                JPAFileActionListener fileListener = new JPAFileActionListener((JPAFileDataObject) file.getModelerFileDataObject());
                fileListener.openModelerFile(singletonMap(WORK_SPACE, workSpace));
            });
        } else {
            loadWorkspaceUI();
        }
    }

    public void loadWorkspaceUI() {
        EntityMappings entityMappings = scene.getBaseElementSpec();
        workSpaceMenu.removeAll();

        JMenuItem createWPItem = new JMenuItem(
                getMessage(WorkSpaceManager.class, "WorkSpaceManager.create"),
                CREATE_ICON
        );
        createWPItem.addActionListener(e -> {
            WorkSpaceDialog workSpaceDialog = new WorkSpaceDialog(scene, null);
            workSpaceDialog.setVisible(true);
            if (workSpaceDialog.getDialogResult() == OK_OPTION) {
                WorkSpace workSpace = workSpaceDialog.getWorkSpace();
                entityMappings.addWorkSpace(workSpace);
                openWorkSpace(false, workSpace);
                JeddictLogger.createWorkSpace();
            }
        });
        workSpaceMenu.add(createWPItem);

        if (entityMappings.getWorkSpaces().size() > 1) {
            JMenuItem deleteAllWPItem = new JMenuItem(
                    getMessage(WorkSpaceManager.class, "WorkSpaceManager.deleteAll"),
                    DELETE_ALL_ICON
            );
            deleteAllWPItem.addActionListener(e -> {
                WorkSpaceTrashDialog workSpaceDialog = new WorkSpaceTrashDialog(scene);
                workSpaceDialog.setVisible(true);
                if (workSpaceDialog.isCurrentWorkSpaceDeleted()) {
                    openWorkSpace(true, entityMappings.getRootWorkSpace());
                } else {
                    loadWorkspaceUI();
                }
                JeddictLogger.deleteAllWorkSpace();
            });
            workSpaceMenu.add(deleteAllWPItem);
        }
        workSpaceMenu.addSeparator();

        if (entityMappings.getCurrentWorkSpace() != entityMappings.getRootWorkSpace()) {
            JMenuItem updateWPItem = new JMenuItem(
                    getMessage(WorkSpaceManager.class, "WorkSpaceManager.updateCurrent", entityMappings.getCurrentWorkSpace().getName()),
                    EDIT_ICON
            );
            updateWPItem.addActionListener(e -> {
                WorkSpaceDialog workSpaceDialog = new WorkSpaceDialog(scene, entityMappings.getCurrentWorkSpace());
                workSpaceDialog.setVisible(true);
                if (workSpaceDialog.getDialogResult() == OK_OPTION) {
                    openWorkSpace(true, entityMappings.getCurrentWorkSpace());
                }
                JeddictLogger.updateWorkSpace();
            });
            workSpaceMenu.add(updateWPItem);

            JMenuItem deleteWPItem = new JMenuItem(
                    getMessage(WorkSpaceManager.class, "WorkSpaceManager.deleteCurrent", entityMappings.getCurrentWorkSpace().getName()),
                    DELETE_ICON
            );
            deleteWPItem.addActionListener(e -> {
                int option = JOptionPane.showConfirmDialog(
                        WindowManager.getDefault().getMainWindow(),
                        getMessage(WorkSpaceManager.class, "WorkSpaceManager.delete.content"),
                        getMessage(WorkSpaceManager.class, "WorkSpaceManager.delete.title"),
                        JOptionPane.YES_NO_OPTION
                );
                if (option == OK_OPTION) {
                    entityMappings.removeWorkSpace(entityMappings.getCurrentWorkSpace());
                    entityMappings.setNextWorkSpace(entityMappings.getRootWorkSpace());
                    scene.getModelerPanelTopComponent().changePersistenceState(false);
                    openWorkSpace(true, entityMappings.getRootWorkSpace());
                }
                JeddictLogger.deleteWorkSpace();
            });
            workSpaceMenu.add(deleteWPItem);
            workSpaceMenu.addSeparator();
        }

        int count = 0;
//        final int MAX_LIMIT = 10;
        for (WorkSpace ws : entityMappings.getWorkSpaces()) {
            JRadioButtonMenuItem workSpaceMenuItem;
            if (count == 0) {
                workSpaceMenuItem = new JRadioButtonMenuItem(ws.getName(), HOME_ICON);
            } else {
                workSpaceMenuItem = new JRadioButtonMenuItem(ws.getName());
            }
            ++count;
            if (entityMappings.getCurrentWorkSpace() == ws) {
                workSpaceMenuItem.setSelected(true);
            }
//            if (count < MAX_LIMIT) {
//                workSpaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(Character.forDigit(count, 10), InputEvent.SHIFT_DOWN_MASK));
//            }
            workSpaceMenuItem.addActionListener(e -> {
                openWorkSpace(true, ws);
                JeddictLogger.openWorkSpace(ws.getItems().size());
            });
            workSpaceMenu.add(workSpaceMenuItem);
        }
    }

    /**
     * @return the workSpaceMenu
     */
    public JMenu getWorkSpaceMenu() {
        return workSpaceMenu;
    }

    
    public void reloadMainWorkSpace(){
        EntityMappings entityMappings = scene.getBaseElementSpec();
        if (entityMappings.getRootWorkSpace().getItems().size() != entityMappings.getJavaClass().size()) {
            entityMappings.getRootWorkSpace().setItems(
                    entityMappings.getJavaClass()
                            .stream()
                            .map(WorkSpaceItem::new)
                            .collect(toSet())
            );
        }
    }
    
    public void loadDependentItems(WorkSpace workSpace) {
        Set<JavaClass<? extends IAttributes>> selectedClasses = workSpace.getItems()
                .stream()
                .map(wi -> (JavaClass<? extends IAttributes>) wi.getJavaClass())
                .collect(toSet());
        
        Set<JavaClass<? extends IAttributes>> dependantClasses = findDependents(selectedClasses);
        if (dependantClasses.size() > 0) {
            selectedClasses.addAll(dependantClasses);
            
            workSpace.setItems(
                    selectedClasses
                            .stream()
                            .map(WorkSpaceItem::new)
                            .collect(toSet())
            );
        }
    }

    static Set<JavaClass<? extends IAttributes>> findDependents(Set<JavaClass<? extends IAttributes>> selectedClasses){
        Set<JavaClass<? extends IAttributes>> dependantClasses = 
                selectedClasses.stream()
                .flatMap(_class -> _class.getAllSuperclass().stream())
                .collect(toSet());
        dependantClasses.removeAll(selectedClasses);
        return dependantClasses;
    }
        
    public void syncWorkSpaceItem() {
        EntityMappings entityMappings = scene.getBaseElementSpec();
        for (WorkSpaceItem item : entityMappings.getCurrentWorkSpace().getItems()) {
            IBaseElementWidget widget = scene.getBaseElement(item.getJavaClass().getId());
            if (widget != null && widget instanceof JavaClassWidget) {
                JavaClassWidget<JavaClass> classWidget = (JavaClassWidget<JavaClass>) widget;
                if (!scene.isSceneGenerating()) {
                    item.setX(classWidget.getSceneViewBound().x);
                    item.setY(classWidget.getSceneViewBound().y);
                } 
                item.setTextDesign(classWidget.getTextDesign().isChanged()
                        ? (NodeTextDesign)classWidget.getTextDesign():null);
                Map<Attribute, WorkSpaceElement> cache = item.getWorkSpaceElementMap();
                item.setWorkSpaceElement(
                        classWidget.getAllAttributeWidgets(false)
                                .stream()
                                .map(attrWidget -> new WorkSpaceElement(attrWidget.getBaseElementSpec(), (PinTextDesign) attrWidget.getTextDesign()))
                                .collect(toList())
                );
                item.getWorkSpaceElement()
                        .stream()
                        .filter(wse -> cache.get(wse.getAttribute())!=null)
                        .forEach(wse -> wse.setJsonbTextDesign(
                                cache.get(wse.getAttribute()).getJsonbTextDesign().isChanged()?
                                        cache.get(wse.getAttribute()).getJsonbTextDesign():null
                        ));
            } else {
                item.setLocation(null);
            }
        }
    }
    

    
    public void updateWorkSpace() {
        EntityMappings entityMappings = scene.getBaseElementSpec();
        syncWorkSpaceItem();
        entityMappings.setCurrentWorkSpace(entityMappings.getNextWorkSpace());
        entityMappings.setJPADiagram(null);
    }
    
    
}
