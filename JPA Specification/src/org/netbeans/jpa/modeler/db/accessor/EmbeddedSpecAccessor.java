/**
 * Copyright [2016] Gaurav Gupta
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
package org.netbeans.jpa.modeler.db.accessor;

import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.EmbeddedAccessor;
import org.netbeans.jpa.modeler.spec.Embedded;
import org.netbeans.jpa.modeler.spec.extend.Attribute;

/**
 *
 * @author Gaurav Gupta
 */
public class EmbeddedSpecAccessor extends EmbeddedAccessor {

    private Embedded embedded;

    private EmbeddedSpecAccessor(Embedded embedded) {
        this.embedded = embedded;
    }

    public static EmbeddedSpecAccessor getInstance(Embedded embedded) {
        EmbeddedSpecAccessor accessor = new EmbeddedSpecAccessor(embedded);
        accessor.setName(embedded.getName());
        accessor.setAttributeType(embedded.getAttributeType());
        return accessor;
    }

    @Override
    public void process() {
        super.process();
        getMapping().setProperty(Attribute.class, embedded);
    }

}
