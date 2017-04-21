/* 
 * Copyright [2017] Gaurav Gupta
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
package org.netbeans.jpa.modeler.external.jpqleditor;

import org.netbeans.api.project.Project;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modules.j2ee.persistence.jpqleditor.JPQLEditorController;
import org.netbeans.modules.j2ee.persistence.jpqleditor.ui.JPQLEditorTopComponent;
import org.netbeans.modules.j2ee.persistence.provider.InvalidPersistenceXmlException;
import org.netbeans.modules.j2ee.persistence.provider.ProviderUtil;
import org.netbeans.modules.j2ee.persistence.unit.PUDataObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * JPQL Editor controller. Controls overall JPQL query execution.
 */
public class JPQLExternalEditorController extends JPQLEditorController {

    public void init(ModelerFile modelerFile) {
        JPQLEditorTopComponent editorTopComponent = new JPQLEditorTopComponent(this);
        editorTopComponent.open();
        editorTopComponent.requestActive();
        editorTopComponent.setFocusToEditor();
        PUDataObject pud;
        try {
            Project project = modelerFile.getProject();
            pud = ProviderUtil.getPUDataObject(project);
            
            InstanceContent lookupContent = new InstanceContent();
            lookupContent.add(pud);
            AbstractLookup lookup = new AbstractLookup(lookupContent);
            AbstractNode node = new AbstractNode(Children.LEAF, lookup);
            
            editorTopComponent.fillPersistenceConfigurations(new Node[]{node});
        } catch (InvalidPersistenceXmlException ex) {
            modelerFile.handleException(ex);
        }
    }
}
