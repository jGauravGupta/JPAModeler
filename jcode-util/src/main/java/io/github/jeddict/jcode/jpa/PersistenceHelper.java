/**
 * Copyright 2013-2019 the original author or authors from the Jeddict project (https://jeddict.github.io/).
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
package io.github.jeddict.jcode.jpa;

import io.github.jeddict.jcode.util.DOMHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.modules.j2ee.deployment.common.api.ConfigurationException;
import org.netbeans.modules.j2ee.deployment.common.api.Datasource;
import org.netbeans.modules.j2ee.deployment.devmodules.spi.J2eeModuleProvider;
import org.netbeans.modules.j2ee.persistence.api.PersistenceScope;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PersistenceHelper {

    private static final String PERSISTENCE_UNIT_TAG = "persistence-unit";      //NOI18N

    private static final String PROPERTIES_TAG = "properties";      //NOI18N

    private static final String NAME_ATTR = "name";                 //NOI18N

    private static final String EXCLUDE_UNLISTED_CLASSES_TAG = "exclude-unlisted-classes";      //NOI18N

    private static final String TRANSACTION_TYPE_ATTR = "transaction-type";         //NOI18N

    public static final String JTA_VALUE = "JTA";
    
    public static final String RESOURCE_LOCAL_VALUE = "RESOURCE_LOCAL";        //NOI18N

    private static final String JTA_DATA_SOURCE_TAG = "jta-data-source";        //NOI18N

    private static final String NON_JTA_DATA_SOURCE_TAG = "non-jta-data-source";        //NOI18N

    private static final String CLASS_TAG = "class";    //NOI18N

    private static final String PROVIDER_TAG = "provider";  //NOI18N

    private static final String DEFAULT_PERSISTENCE_PROVIDER = "org.eclipse.persistence.jpa.PersistenceProvider";  //NOI18N

    private final Project project;
    private DOMHelper helper;

    public PersistenceHelper(Project project) {
        this.project = project;

        FileObject fobj = getPersistenceXML(project);

        if (fobj != null) {
            helper = new DOMHelper(fobj);
        }
    }

    public PersistenceUnit getPersistenceUnit() {
        if (helper != null) {
            Element puElement = helper.findElement(PERSISTENCE_UNIT_TAG);

            if (puElement != null) {
                Attr puNameNode = puElement.getAttributeNode(NAME_ATTR);
                String puName = puNameNode == null ? null : puNameNode.getValue();

                String provider;
                NodeList nodes = puElement.getElementsByTagName(PROVIDER_TAG);
                if (nodes.getLength() > 0) {
                    provider = helper.getValue((Element) nodes.item(0));
                } else {
                    provider = DEFAULT_PERSISTENCE_PROVIDER;
                }

                Datasource datasource = null;

                NodeList nodeList = puElement.getElementsByTagName(JTA_DATA_SOURCE_TAG);
                if (nodeList.getLength() > 0) {
                    Element dsElement = (Element) nodeList.item(0);
                    String jndiName = helper.getValue(dsElement);
                    J2eeModuleProvider j2eeModuleProvider = (J2eeModuleProvider) project.getLookup().lookup(J2eeModuleProvider.class);
                    try {
                        datasource = j2eeModuleProvider.getConfigSupport().findDatasource(jndiName);
                    } catch (ConfigurationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                return new PersistenceUnit(puName, provider, datasource);
            }
        }

        return null;
    }

    public void configure(Collection<String> classNames) throws IOException {
        if (helper == null) {
            return;
        }
//        RestSupport support = project.getLookup().lookup(RestSupport.class);
//        if (nonNull(support) && support.isServerTomcat()) {
//            unsetExcludeEnlistedClasses();
//            addEntityClasses(classNames);
//        }
        if (!hasJTASupport(project)) {
            switchToResourceLocalTransaction();
        }
        helper.save();
    }

    public boolean hasJTASupport(Project project) {
        return hasResource(project, "jakarta/transaction/UserTransaction.class");  // NOI18N
    }

    public static boolean hasResource(Project project, String resource) {
        SourceGroup[] sgs = ProjectUtils.getSources(project).getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        if (sgs.length < 1) {
            return false;
        }
        FileObject sourceRoot = sgs[0].getRootFolder();
        ClassPath classPath = ClassPath.getClassPath(sourceRoot, ClassPath.COMPILE);
        if (classPath == null) {
            return false;
        }
        FileObject resourceFile = classPath.findResource(resource);
        return resourceFile != null;
    }

    private void unsetExcludeEnlistedClasses() throws IOException {
        Element puElement = helper.findElement(PERSISTENCE_UNIT_TAG);
        NodeList nodes = puElement.getElementsByTagName(EXCLUDE_UNLISTED_CLASSES_TAG);

        if (nodes.getLength() > 0) {
            helper.setValue((Element) nodes.item(0), "false");  //NOI18N
        } else {
            puElement.insertBefore(helper.createElement(EXCLUDE_UNLISTED_CLASSES_TAG, "false"), //NOI18N
                    helper.findElement(PROPERTIES_TAG));
        }
    }

    private void switchToResourceLocalTransaction() throws IOException {
        Element puElement = helper.findElement(PERSISTENCE_UNIT_TAG);
        puElement.setAttribute(TRANSACTION_TYPE_ATTR, RESOURCE_LOCAL_VALUE);

        NodeList nodes = puElement.getElementsByTagName(JTA_DATA_SOURCE_TAG);
        String dataSource = null;

        if (nodes.getLength() > 0) {
            Element oldElement = (Element) nodes.item(0);
            dataSource = helper.getValue(oldElement);
            Element newElement = helper.createElement(NON_JTA_DATA_SOURCE_TAG, dataSource);
            puElement.replaceChild(newElement, oldElement);
        }
    }

    private void addEntityClasses(Collection<String> classNames) throws IOException {
        List<String> toAdd = new ArrayList<>(classNames);
        Element puElement = helper.findElement(PERSISTENCE_UNIT_TAG);
        NodeList nodes = puElement.getElementsByTagName(CLASS_TAG);
        int length = nodes.getLength();

        for (int i = 0; i < length; i++) {
            toAdd.remove(helper.getValue((Element) nodes.item(i)));
        }

        for (String className : toAdd) {
            puElement.insertBefore(helper.createElement(CLASS_TAG, className),
                    helper.findElement(EXCLUDE_UNLISTED_CLASSES_TAG));
        }
    }

    private FileObject getPersistenceXML(Project project) {
        PersistenceScope ps = PersistenceScope.getPersistenceScope(project.getProjectDirectory());
        if (ps != null) {
            return ps.getPersistenceXml();
        }
        return null;
    }

    public static class PersistenceUnit {

        private final String name;
        private final String provider;
        private final Datasource datasource;

        public PersistenceUnit(String name, String provider, Datasource datasource) {
            this.name = name;
            this.provider = provider;
            this.datasource = datasource;
        }

        public String getName() {
            return name;
        }

        public String getProvider() {
            return provider;
        }

        public Datasource getDatasource() {
            return datasource;
        }
    }
}
