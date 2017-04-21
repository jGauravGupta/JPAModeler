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
package org.netbeans.orm.converter.generator.managed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.jcode.console.Console;
import static org.netbeans.jcode.console.Console.BOLD;
import static org.netbeans.jcode.console.Console.FG_RED;
import org.netbeans.jcode.task.ITaskSupervisor;
import org.netbeans.jpa.modeler.collaborate.issues.ExceptionUtils;
import org.netbeans.jpa.modeler.spec.DefaultClass;
import org.netbeans.jpa.modeler.spec.Embeddable;
import org.netbeans.jpa.modeler.spec.Entity;
import org.netbeans.jpa.modeler.spec.EntityMappings;
import org.netbeans.jpa.modeler.spec.MappedSuperclass;
import org.netbeans.orm.converter.compiler.ClassDefSnippet;
import org.netbeans.orm.converter.compiler.InvalidDataException;
import org.netbeans.orm.converter.compiler.WritableSnippet;
import org.netbeans.orm.converter.generator.DefaultClassGenerator;
import org.netbeans.orm.converter.generator.EmbeddableGenerator;
import org.netbeans.orm.converter.generator.EmbeddableIdClassGenerator;
import org.netbeans.orm.converter.generator.EntityGenerator;
import org.netbeans.orm.converter.generator.MappedSuperClassGenerator;
import org.netbeans.orm.converter.spec.ModuleGenerator;
import org.netbeans.orm.converter.util.ClassType;
import org.netbeans.orm.converter.util.ClassesRepository;
import org.netbeans.orm.converter.util.ORMConverterUtil;
import org.openide.filesystems.FileUtil;

@org.openide.util.lookup.ServiceProvider(service = ModuleGenerator.class)
public class ManagedClassModuleGeneratorImpl implements ModuleGenerator {

    private EntityMappings parsedEntityMappings;//Required Generation based on inheritance means if any entity metamodel is generated then its super class metamodel must be generated either user want or not .
    private String packageName;
    private File destDir;
    private ITaskSupervisor task;
    private final ClassesRepository classesRepository = ClassesRepository.getInstance();

    @Override
    public void generate(ITaskSupervisor task, Project project, SourceGroup sourceGroup, EntityMappings parsedEntityMappings) {
        try {
            this.parsedEntityMappings = parsedEntityMappings;
            this.task = task;
            destDir = FileUtil.toFile(sourceGroup.getRootFolder());
            this.packageName = parsedEntityMappings.getPackage();

            generateMappedSuperClasses();
            generateEntityClasses();
            generateEmbededClasses();
            generateDefaultClasses();
        } catch (InvalidDataException | IOException ex) {
            ExceptionUtils.printStackTrace(ex);
        }
    }

    private void generateDefaultClasses() throws InvalidDataException, IOException {
        List<DefaultClass> parsedDefaultClasses = parsedEntityMappings.getDefaultClass().stream().filter(e -> e.getGenerateSourceCode()).collect(toList());
        if(!parsedDefaultClasses.isEmpty()){
            task.log(Console.wrap("Generating IdClass/PrimaryKey Class : " , FG_RED, BOLD), true);
        }
        for (DefaultClass parsedDefaultClasse : parsedDefaultClasses) {
            task.log(parsedDefaultClasse.getClazz(), true);
            if (parsedDefaultClasse.isEmbeddable()) {
                generateEmbededIdClasses(parsedDefaultClasse);
            } else {
                generateIdClasses(parsedDefaultClasse);
            }
        }
    }
    private void generateEmbededClasses() throws InvalidDataException, IOException {
        List<Embeddable> parsedEmbeddables = parsedEntityMappings.getEmbeddable().stream().filter(e -> e.getGenerateSourceCode()).collect(toList());
        if(!parsedEmbeddables.isEmpty()){
            task.log(Console.wrap("Generating Embeddable Class : " , FG_RED, BOLD), true);
        }
        for (Embeddable parsedEmbeddable : parsedEmbeddables) {
            task.log(parsedEmbeddable.getClazz(), true);
            ManagedClassDefSnippet classDef = new EmbeddableGenerator(parsedEmbeddable, packageName).getClassDef();
            classDef.setJaxbSupport(parsedEntityMappings.getJaxbSupport());

            classesRepository.addWritableSnippet(ClassType.EMBEDED_CLASS, classDef);
            parsedEmbeddable.setFileObject(ORMConverterUtil.writeSnippet(classDef, destDir));
        }
    }

    private void generateEntityClasses() throws InvalidDataException, IOException {
        List<Entity> parsedEntities = parsedEntityMappings.getEntity().stream().filter(e -> e.getGenerateSourceCode()).collect(toList());
        if(!parsedEntities.isEmpty()){
            task.log(Console.wrap("Generating Entity Class : " , FG_RED, BOLD), true);
        }
        for (Entity parsedEntity : parsedEntities) {
            task.log(parsedEntity.getClazz(), true);
            ManagedClassDefSnippet classDef = new EntityGenerator(parsedEntity, packageName).getClassDef();
            classDef.setJaxbSupport(parsedEntityMappings.getJaxbSupport());

            classesRepository.addWritableSnippet(ClassType.ENTITY_CLASS, classDef);
            parsedEntity.setFileObject(ORMConverterUtil.writeSnippet(classDef, destDir));
        }
    }

    private void generateMappedSuperClasses() throws InvalidDataException, IOException {
        List<MappedSuperclass> parsedMappedSuperclasses = parsedEntityMappings.getMappedSuperclass().stream().filter(e -> e.getGenerateSourceCode()).collect(toList());
        if(!parsedMappedSuperclasses.isEmpty()){
        task.log(Console.wrap("Generating MappedSuperclass Class : " , FG_RED, BOLD), true);
        }
        for (MappedSuperclass parsedMappedSuperclass : parsedMappedSuperclasses) {
            task.log(parsedMappedSuperclass.getClazz(), true);
            ManagedClassDefSnippet classDef = new MappedSuperClassGenerator(parsedMappedSuperclass, packageName).getClassDef();
            classDef.setJaxbSupport(parsedEntityMappings.getJaxbSupport());

            classesRepository.addWritableSnippet(ClassType.SUPER_CLASS, classDef);
            parsedMappedSuperclass.setFileObject(ORMConverterUtil.writeSnippet(classDef, destDir));
        }
    }

    private void generateEmbededIdClasses(DefaultClass defaultClass) throws InvalidDataException, IOException {
        ClassDefSnippet classDef = new EmbeddableIdClassGenerator(defaultClass, packageName).getClassDef();
        classesRepository.addWritableSnippet(ClassType.EMBEDED_CLASS, classDef);
        ORMConverterUtil.writeSnippet(classDef, destDir);//TODO set file object
    }

    private void generateIdClasses(DefaultClass defaultClass) throws InvalidDataException, IOException {
        ClassDefSnippet classDef = new DefaultClassGenerator(defaultClass, packageName).getClassDef();
        classesRepository.addWritableSnippet(ClassType.SERIALIZER_CLASS, classDef);
        ORMConverterUtil.writeSnippet(classDef, destDir);//TODO set file object
    }

    private List<ClassDefSnippet> getPUXMLEntries() {
        List<WritableSnippet> entitySnippets = classesRepository.getWritableSnippets(ClassType.ENTITY_CLASS);
        List<ClassDefSnippet> classDefs = new ArrayList<>();
        for (WritableSnippet writableSnippet : entitySnippets) {
            classDefs.add((ClassDefSnippet) writableSnippet);
        }
        return classDefs;
    }

}
