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
package org.netbeans.db.modeler.core.widget.column;

import java.util.List;
import org.netbeans.db.modeler.core.widget.flow.ReferenceFlowWidget;
import org.netbeans.db.modeler.core.widget.table.TableWidget;
import org.netbeans.jpa.modeler.spec.extend.BaseElement;

/**
 *
 * @author Gaurav Gupta
 */
public interface IReferenceColumnWidget<E extends BaseElement> extends IColumnWidget {

    boolean addReferenceFlowWidget(ReferenceFlowWidget flowWidget);

    boolean removeReferenceFlowWidget(ReferenceFlowWidget flowWidget);

    E getBaseElementSpec();

    TableWidget getTableWidget();

    List<ReferenceFlowWidget> getReferenceFlowWidget();

    void setAnchorGap(int anchorGap);
}
