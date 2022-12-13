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
package io.github.jeddict.orm.generator.service.packageinfo;

import io.github.jeddict.jpa.spec.EntityMappings;
import io.github.jeddict.jpa.spec.extend.Attribute;
import io.github.jeddict.orm.generator.compiler.def.VariableDefSnippet;
import io.github.jeddict.orm.generator.service.ClassGenerator;
import io.github.jeddict.orm.generator.util.ClassHelper;

public class PackageInfoGenerator extends ClassGenerator<PackageInfoClassDefSnippet> {

    private EntityMappings parsedEntityMappings;

    public PackageInfoGenerator(EntityMappings parsedEntityMappings, String packageName) {
        super(new PackageInfoClassDefSnippet());
        this.parsedEntityMappings = parsedEntityMappings;
        this.rootPackageName = packageName;
        this.packageName = packageName;
    }

    @Override
    public PackageInfoClassDefSnippet getClassDef() {
        ClassHelper classHelper = new ClassHelper("package-info");
        classHelper.setPackageName(packageName);
        classDef.setClassName(classHelper.getFQClassName());
        classDef.setPackageName(classHelper.getPackageName());
        classDef.setNamespace(parsedEntityMappings.getJaxbNameSpace());
        classDef.setJSONBSnippets(getJSONBCPackageSnippet(parsedEntityMappings));
        return classDef;
    }

    @Override
    protected VariableDefSnippet processVariable(Attribute attr) {
        throw new IllegalStateException("Invalid Attribute Type");
    }

}
