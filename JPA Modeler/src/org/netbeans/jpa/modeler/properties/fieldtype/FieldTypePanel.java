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
package org.netbeans.jpa.modeler.properties.fieldtype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.netbeans.jcode.core.util.AttributeType;
import org.netbeans.jpa.modeler.spec.Basic;
import org.netbeans.jpa.modeler.spec.ElementCollection;
import org.netbeans.jpa.modeler.spec.EnumType;
import org.netbeans.jpa.modeler.spec.Id;
import org.netbeans.jpa.modeler.spec.Lob;
import org.netbeans.jpa.modeler.spec.TemporalType;
import org.netbeans.jpa.modeler.spec.Transient;
import org.netbeans.jpa.modeler.spec.Version;
import static org.netbeans.jcode.core.util.AttributeType.BIGDECIMAL;
import static org.netbeans.jcode.core.util.AttributeType.BIGINTEGER;
import static org.netbeans.jcode.core.util.AttributeType.BOOLEAN;
import static org.netbeans.jcode.core.util.AttributeType.BOOLEAN_WRAPPER;
import static org.netbeans.jcode.core.util.AttributeType.BYTE;
import static org.netbeans.jcode.core.util.AttributeType.BYTE_ARRAY;
import static org.netbeans.jcode.core.util.AttributeType.BYTE_WRAPPER;
import static org.netbeans.jcode.core.util.AttributeType.BYTE_WRAPPER_ARRAY;
import static org.netbeans.jcode.core.util.AttributeType.CALENDAR;
import static org.netbeans.jcode.core.util.AttributeType.CHAR;
import static org.netbeans.jcode.core.util.AttributeType.CHAR_ARRAY;
import static org.netbeans.jcode.core.util.AttributeType.CHAR_WRAPPER;
import static org.netbeans.jcode.core.util.AttributeType.CHAR_WRAPPER_ARRAY;
import static org.netbeans.jcode.core.util.AttributeType.DATE;
import static org.netbeans.jcode.core.util.AttributeType.DOUBLE;
import static org.netbeans.jcode.core.util.AttributeType.DOUBLE_WRAPPER;
import static org.netbeans.jcode.core.util.AttributeType.FLOAT;
import static org.netbeans.jcode.core.util.AttributeType.FLOAT_WRAPPER;
import static org.netbeans.jcode.core.util.AttributeType.INT;
import static org.netbeans.jcode.core.util.AttributeType.INT_WRAPPER;
import static org.netbeans.jcode.core.util.AttributeType.LONG;
import static org.netbeans.jcode.core.util.AttributeType.LONG_WRAPPER;
import static org.netbeans.jcode.core.util.AttributeType.SHORT;
import static org.netbeans.jcode.core.util.AttributeType.SHORT_WRAPPER;
import static org.netbeans.jcode.core.util.AttributeType.SQL_DATE;
import static org.netbeans.jcode.core.util.AttributeType.SQL_TIME;
import static org.netbeans.jcode.core.util.AttributeType.SQL_TIMESTAMP;
import static org.netbeans.jcode.core.util.AttributeType.STRING;
import static org.netbeans.jcode.core.util.AttributeType.isArray;
import org.netbeans.jpa.modeler.spec.Embeddable;
import org.netbeans.jpa.modeler.spec.Entity;
import org.netbeans.jpa.modeler.spec.EntityMappings;
import org.netbeans.jpa.modeler.spec.extend.Attribute;
import org.netbeans.jpa.modeler.spec.extend.BaseAttribute;
import org.netbeans.jpa.modeler.spec.extend.ColumnHandler;
import org.netbeans.jpa.modeler.spec.extend.MapKeyHandler;
import org.netbeans.jpa.modeler.spec.extend.PersistenceBaseAttribute;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.properties.embedded.GenericEmbeddedEditor;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;

/**
 *
 * @author Gaurav_Gupta
 */
public class FieldTypePanel extends GenericEmbeddedEditor<Attribute> {

    private final ModelerFile modelerFile;
    private final boolean mapKey;
    private Attribute attribute;
    private final EntityMappings entityMappings;

    private static final String[] BASIC_DEFAULT_DATATYPE = new String[]{STRING, CHAR, BOOLEAN, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, CHAR_WRAPPER, BOOLEAN_WRAPPER,
                        BYTE_WRAPPER, SHORT_WRAPPER, INT_WRAPPER, LONG_WRAPPER, FLOAT_WRAPPER, DOUBLE_WRAPPER, BIGINTEGER, BIGDECIMAL,
                        SQL_DATE, SQL_TIME, SQL_TIMESTAMP};
    
    private static final String[] LOB_DATATYPE = new String[]{STRING, BYTE_ARRAY, BYTE_WRAPPER_ARRAY, CHAR_ARRAY, CHAR_WRAPPER_ARRAY};
    private static final String[] TEMPORAL_DATATYPE = new String[]{DATE, CALENDAR};
    private static final String[] MAPKEY_DEFAULT_DATATYPE = new String[]{STRING, CHAR, BOOLEAN, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, CHAR_WRAPPER, BOOLEAN_WRAPPER,
                        BYTE_WRAPPER, SHORT_WRAPPER, INT_WRAPPER, LONG_WRAPPER, FLOAT_WRAPPER, DOUBLE_WRAPPER, BIGINTEGER, BIGDECIMAL,
                        //BUG : https://java.net/bugzilla/show_bug.cgi?id=6306 Add @Temporal annotation for java.util.Date fields
                        /*DATE, CALENDAR,*/
                        SQL_DATE, SQL_TIME, SQL_TIMESTAMP};
    private static final String[] ELEMENTCOLLECTION_DEFAULT_DATATYPE = new String[]{STRING, CHAR_WRAPPER, BOOLEAN_WRAPPER, BYTE_WRAPPER, SHORT_WRAPPER, INT_WRAPPER,
                        LONG_WRAPPER, FLOAT_WRAPPER, DOUBLE_WRAPPER, BIGINTEGER, BIGDECIMAL,
                        SQL_DATE, SQL_TIME, SQL_TIMESTAMP};
    private static final Set<String> BCLOB_DATATYPE_FILTER = new HashSet<>(Arrays.asList(BYTE_ARRAY, BYTE_WRAPPER_ARRAY, CHAR_ARRAY, CHAR_WRAPPER_ARRAY));

    @Override
    public void init() {
        initComponents();
    }
    
    private String getDataType() {
        String dataType = dataType_ComboBox.getSelectedItem().toString().trim();
        dataType = isArray(dataType) ? AttributeType.getArrayType(dataType) + "[]" : dataType;
        dataType_ComboBox.setSelectedItem(dataType);
        return dataType;
    }
    
    @Override
    public Attribute getValue() {
        String type = (String) type_ComboBox.getSelectedItem();
        String dataType = null;
        if(!ENTITY.equals(type) && !EMBEDDABLE.equals(type)){
            dataType = getDataType();
            if(BCLOB_DATATYPE_FILTER.contains(dataType)){//jpa provider issue , set lob if datatype is blob clob
               type = LOB; 
            }
        }
        
        if (mapKey) {
            MapKeyHandler mapKeyHandler = (MapKeyHandler) attribute;
            mapKeyHandler.resetMapAttribute();
            switch (type) {
                case ENTITY:
                    mapKeyHandler.setMapKeyEntity(((ComboBoxValue<Entity>)dataType_ComboBox.getSelectedItem()).getValue());
                    break;
                case EMBEDDABLE:
                    mapKeyHandler.setMapKeyEmbeddable(((ComboBoxValue<Embeddable>)dataType_ComboBox.getSelectedItem()).getValue());
                    break;
                case ENUMERATED:
                    mapKeyHandler.setMapKeyEnumerated(getSelectedEnumType());
                    mapKeyHandler.setMapKeyAttributeType(dataType);
                    break;
                case TEMPORAL:
                    mapKeyHandler.setMapKeyTemporal(getSelectedTemporalType());
                    mapKeyHandler.setMapKeyAttributeType(dataType);
                    break;
                case DEFAULT:
                    mapKeyHandler.setMapKeyAttributeType(dataType);
                    break;
            }
        } else if (attribute instanceof ElementCollection) {
            ElementCollection elementCollection = (ElementCollection) attribute;
            elementCollection.setLob(null);
            elementCollection.setEnumerated(null);
            elementCollection.setTemporal(null);
            switch (type) {
                case ENUMERATED:
                    elementCollection.setEnumerated(getSelectedEnumType());
                    break;
                case LOB:
                    elementCollection.setLob(new Lob());
                    break;
                case TEMPORAL:
                    elementCollection.setTemporal(getSelectedTemporalType());
                    break;
            }
            elementCollection.setTargetClass(dataType);
        } else if (attribute instanceof Transient) {
            Transient _transient = (Transient) attribute;
            _transient.setAttributeType(dataType);
        } else if (attribute instanceof PersistenceBaseAttribute) {// Id, Version, Basic
            PersistenceBaseAttribute persistenceBaseAttribute = (PersistenceBaseAttribute) attribute;
            persistenceBaseAttribute.setTemporal(null);
            if ((persistenceBaseAttribute instanceof Basic || persistenceBaseAttribute instanceof Id) && type.equals(TEMPORAL)) {
                persistenceBaseAttribute.setTemporal(getSelectedTemporalType());
            }
            persistenceBaseAttribute.setAttributeType(dataType);
            if (persistenceBaseAttribute instanceof Basic) {
                Basic basic = (Basic) persistenceBaseAttribute;
                basic.setLob(null);
                basic.setEnumerated(null);
                switch (type) {
                    case ENUMERATED:
                        basic.setEnumerated(getSelectedEnumType());
                        break;
                    case LOB:
                        basic.setLob(new Lob());
                        break;
                }
            }
        }

        if (attribute instanceof ColumnHandler) { //Issue : #130
            ColumnHandler columnHandler = (ColumnHandler) attribute;
            Integer length = columnHandler.getColumn().getLength();
            Integer precision = columnHandler.getColumn().getPrecision();
            Integer scale = columnHandler.getColumn().getScale();
            columnHandler.getColumn().setLength(!attribute.isTextAttributeType(dataType) ? null : (length==null?255:length));
            columnHandler.getColumn().setPrecision(!attribute.isPrecisionAttributeType(dataType) ? null : (precision==null?0:precision));
            columnHandler.getColumn().setScale(!attribute.isScaleAttributeType(dataType) ? null : (scale==null?0:scale));
        }
        return attribute;
    }

    private void initTypeComboBox() {
        TitledBorder titledBorder = (TitledBorder) jLayeredPane1.getBorder();
        List<String> type = new ArrayList<>();
        type.add(DEFAULT);
        if (mapKey) {
            type.add(ENUMERATED);
            type.add(TEMPORAL);
            type.add(ENTITY);
            type.add(EMBEDDABLE);
            titledBorder.setTitle("MapKey Attribute");
        } else if (attribute instanceof PersistenceBaseAttribute) {
            type.add(TEMPORAL);
            if (attribute instanceof Basic) {
                type.add(ENUMERATED);
                type.add(LOB);
                titledBorder.setTitle("Basic Attribute");
            } else if (attribute instanceof Id) {
                titledBorder.setTitle("Id Attribute");
            } else if (attribute instanceof Version) {
                titledBorder.setTitle("Version Attribute");
            }
        } else if (attribute instanceof ElementCollection) {
            type.add(ENUMERATED);
            type.add(LOB);
            type.add(TEMPORAL);
            titledBorder.setTitle("ElementCollection<Basic> Attribute");
        } else if (attribute instanceof Transient) {
            titledBorder.setTitle("Transient Attribute");
        }

        type_ComboBox.removeAllItems();
        type_ComboBox.setModel(new DefaultComboBoxModel(type.toArray(new String[0])));
        //ElementCollection[Basic Type Value] => Lob,Enumerated,Temporal
        //Id => Temporal
    }

    private void setDataTypeEditable() {
        dataType_ComboBox.setEditable(true);
        dataType_Action.setVisible(true);
    }
    
    private void setDataTypeNonEditable() {
        dataType_ComboBox.setEditable(false);
        dataType_Action.setVisible(false);
    }

    private void initDataTypeComboBox() {
        String[] dataType = null;
        List<ComboBoxValue> classList = null;
        setDataTypeEditable();
        String type = (String) type_ComboBox.getSelectedItem();
        if (mapKey) {
            switch (type) {
                case ENTITY:
                    classList = entityMappings.getEntity().stream().map(e -> new ComboBoxValue<Entity>(e, e.getClazz())).collect(toList());
                    setDataTypeNonEditable();
                    break;
                case EMBEDDABLE:
                    classList = entityMappings.getEmbeddable().stream().map(e -> new ComboBoxValue<Embeddable>(e, e.getClazz())).collect(toList());
                    setDataTypeNonEditable();
                    break;
                case ENUMERATED:
                    break;
                case TEMPORAL:
                    dataType = TEMPORAL_DATATYPE;
                    break;
                default:
                    dataType = MAPKEY_DEFAULT_DATATYPE;
                    break;
            }
        } else if (attribute instanceof Basic) {
            switch (type) {
                case ENUMERATED:
                    break;
                case TEMPORAL:
                    dataType = TEMPORAL_DATATYPE;
                    break;
                case LOB:
                    dataType = LOB_DATATYPE;
                    break;
                default:
                    dataType = BASIC_DEFAULT_DATATYPE;
                    break;
            }
        } else if (attribute instanceof ElementCollection) {
            switch (type) {
                case ENUMERATED:
                    break;
                case TEMPORAL:
                    dataType = TEMPORAL_DATATYPE;
                    break;
                case LOB:
                    dataType = LOB_DATATYPE;
                    break;
                default:
                    dataType = ELEMENTCOLLECTION_DEFAULT_DATATYPE;
                    break;
            }
        } else if (attribute instanceof Id) {
            switch (type) {
                case TEMPORAL:
                    dataType = new String[]{DATE};
                    break;
                default:
                    dataType = new String[]{STRING, CHAR, BOOLEAN, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, CHAR_WRAPPER, BOOLEAN_WRAPPER, BYTE_WRAPPER, SHORT_WRAPPER, INT_WRAPPER, LONG_WRAPPER, FLOAT_WRAPPER, DOUBLE_WRAPPER,
                        BIGINTEGER, BIGDECIMAL, DATE, SQL_DATE};
                    break;
            }
        } else if (attribute instanceof Version) {
            dataType = new String[]{INT, INT_WRAPPER, SHORT, SHORT_WRAPPER, LONG, LONG_WRAPPER, SQL_TIMESTAMP};
        } else if (attribute instanceof Transient) {
            //skip it // no datatype is specified in spec
            dataType = BASIC_DEFAULT_DATATYPE;
        }
        
        dataType_ComboBox.removeAllItems();
        if (mapKey && classList != null) {
            dataType_ComboBox.setModel(new DefaultComboBoxModel(classList.toArray()));
        } else {
            if (dataType == null) {
             dataType = new String[]{EMPTY};
            }
            dataType_ComboBox.setModel(new DefaultComboBoxModel(dataType));
            dataType_ComboBox.setSelectedItem(dataType[0]);
        }
        
    }

    @Override
    public void setValue(Attribute attribute) {
        this.attribute = attribute;
        initTypeComboBox();
        if (mapKey) {
            MapKeyHandler mapKeyHandler = (MapKeyHandler) attribute;
            if (mapKeyHandler.getMapKeyEntity()!= null) {
                type_ComboBox.setSelectedItem(ENTITY);
            } else if (mapKeyHandler.getMapKeyEmbeddable()!= null) {
                type_ComboBox.setSelectedItem(EMBEDDABLE);
            } else if (mapKeyHandler.getMapKeyEnumerated() != null) {
                selectEnumType(mapKeyHandler.getMapKeyEnumerated());
            } else if (mapKeyHandler.getMapKeyTemporal() != null) {
                selectedTemporalType(mapKeyHandler.getMapKeyTemporal());
            } else {
                type_ComboBox.setSelectedItem(DEFAULT);
            }
        } else if (attribute instanceof Basic) {
            Basic basic = (Basic) attribute;
            if (basic.getLob() != null) {
                type_ComboBox.setSelectedItem(LOB);
            } else if (basic.getEnumerated() != null) {
                selectEnumType(basic.getEnumerated());
            } else if (basic.getTemporal() != null) {
                selectedTemporalType(basic.getTemporal());
            } else {
                type_ComboBox.setSelectedItem(DEFAULT);
            }
        } else if (attribute instanceof ElementCollection) {
            ElementCollection elementCollection = (ElementCollection) attribute;
            if (elementCollection.getLob() != null) {
                type_ComboBox.setSelectedItem(LOB);
            } else if (elementCollection.getEnumerated() != null) {
                selectEnumType(elementCollection.getEnumerated());
            } else if (elementCollection.getTemporal() != null) {
                selectedTemporalType(elementCollection.getTemporal());
            } else {
                type_ComboBox.setSelectedItem(DEFAULT);
            }

        } else if (attribute instanceof Id) {
            selectedTemporalType(((Id) attribute).getTemporal());
        } else if (attribute instanceof Version) {
            selectedTemporalType(((Version) attribute).getTemporal());
        } else if (attribute instanceof Transient) {

        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        type_ComboBoxActionPerformed(null);
        initDataTypeComboBox();
        
        if (mapKey) {
             MapKeyHandler relationAttribute = (MapKeyHandler) attribute;
            if (relationAttribute.getMapKeyEntity() != null) {
                dataType_ComboBox.setSelectedItem(new ComboBoxValue<>(relationAttribute.getMapKeyEntity(), relationAttribute.getMapKeyEntity().getClazz()));
            } else if (relationAttribute.getMapKeyEmbeddable() != null) {
                dataType_ComboBox.setSelectedItem(new ComboBoxValue<>(relationAttribute.getMapKeyEmbeddable(), relationAttribute.getMapKeyEmbeddable().getClazz()));
            } else if (relationAttribute.getMapKeyAttributeType() != null) {
                dataType_ComboBox.setSelectedItem(relationAttribute.getMapKeyAttributeType());
            }
        } else {
            dataType_ComboBox.setSelectedItem(((BaseAttribute) attribute).getAttributeType());
        }

    }

    public FieldTypePanel(ModelerFile modelerFile, boolean mapKey) {
        this.modelerFile = modelerFile;
        this.mapKey = mapKey;
        this.entityMappings = (EntityMappings) modelerFile.getModelerScene().getBaseElementSpec();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Temporal_buttonGroup = new javax.swing.ButtonGroup();
        Enumerated_buttonGroup = new javax.swing.ButtonGroup();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        type_LayeredPane = new javax.swing.JLayeredPane();
        type_Label = new javax.swing.JLabel();
        type_ComboBox = new javax.swing.JComboBox();
        extendType_LayeredPane = new javax.swing.JLayeredPane();
        Enumerated_LayeredPane1 = new javax.swing.JLayeredPane();
        Ordinal_RadioButton = new javax.swing.JRadioButton();
        String_RadioButton = new javax.swing.JRadioButton();
        Default_Ordinal_RadioButton = new javax.swing.JRadioButton();
        Temporal_LayeredPane = new javax.swing.JLayeredPane();
        Date_RadioButton = new javax.swing.JRadioButton();
        Time_RadioButton = new javax.swing.JRadioButton();
        TimeStamp_RadioButton = new javax.swing.JRadioButton();
        dataType_LayeredPane = new javax.swing.JLayeredPane();
        dataType_Label = new javax.swing.JLabel();
        dataType_ComboBox = new javax.swing.JComboBox();
        dataType_Action = new javax.swing.JButton();

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.jLayeredPane1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(type_Label, org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.type_Label.text")); // NOI18N

        type_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                type_ComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout type_LayeredPaneLayout = new javax.swing.GroupLayout(type_LayeredPane);
        type_LayeredPane.setLayout(type_LayeredPaneLayout);
        type_LayeredPaneLayout.setHorizontalGroup(
            type_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(type_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        type_LayeredPaneLayout.setVerticalGroup(
            type_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(type_LayeredPaneLayout.createSequentialGroup()
                .addGroup(type_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_Label))
                .addGap(0, 3, Short.MAX_VALUE))
        );
        type_LayeredPane.setLayer(type_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        type_LayeredPane.setLayer(type_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane1.add(type_LayeredPane);
        type_LayeredPane.setBounds(10, 30, 428, 30);

        extendType_LayeredPane.setLayout(new java.awt.FlowLayout());

        Enumerated_buttonGroup.add(Ordinal_RadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(Ordinal_RadioButton, org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.Ordinal_RadioButton.text")); // NOI18N

        Enumerated_buttonGroup.add(String_RadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(String_RadioButton, org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.String_RadioButton.text")); // NOI18N

        Enumerated_buttonGroup.add(Default_Ordinal_RadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(Default_Ordinal_RadioButton, org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.Default_Ordinal_RadioButton.text")); // NOI18N

        javax.swing.GroupLayout Enumerated_LayeredPane1Layout = new javax.swing.GroupLayout(Enumerated_LayeredPane1);
        Enumerated_LayeredPane1.setLayout(Enumerated_LayeredPane1Layout);
        Enumerated_LayeredPane1Layout.setHorizontalGroup(
            Enumerated_LayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Enumerated_LayeredPane1Layout.createSequentialGroup()
                .addComponent(String_RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ordinal_RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Default_Ordinal_RadioButton)
                .addGap(1, 1, 1))
        );
        Enumerated_LayeredPane1Layout.setVerticalGroup(
            Enumerated_LayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Enumerated_LayeredPane1Layout.createSequentialGroup()
                .addGroup(Enumerated_LayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Ordinal_RadioButton)
                    .addComponent(String_RadioButton)
                    .addComponent(Default_Ordinal_RadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Enumerated_LayeredPane1.setLayer(Ordinal_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        Enumerated_LayeredPane1.setLayer(String_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        Enumerated_LayeredPane1.setLayer(Default_Ordinal_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        extendType_LayeredPane.add(Enumerated_LayeredPane1);

        Temporal_buttonGroup.add(Date_RadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(Date_RadioButton, org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.Date_RadioButton.text")); // NOI18N

        Temporal_buttonGroup.add(Time_RadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(Time_RadioButton, org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.Time_RadioButton.text")); // NOI18N

        Temporal_buttonGroup.add(TimeStamp_RadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(TimeStamp_RadioButton, org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.TimeStamp_RadioButton.text")); // NOI18N

        javax.swing.GroupLayout Temporal_LayeredPaneLayout = new javax.swing.GroupLayout(Temporal_LayeredPane);
        Temporal_LayeredPane.setLayout(Temporal_LayeredPaneLayout);
        Temporal_LayeredPaneLayout.setHorizontalGroup(
            Temporal_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Temporal_LayeredPaneLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(Date_RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Time_RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TimeStamp_RadioButton)
                .addGap(1, 1, 1))
        );
        Temporal_LayeredPaneLayout.setVerticalGroup(
            Temporal_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Temporal_LayeredPaneLayout.createSequentialGroup()
                .addGroup(Temporal_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Date_RadioButton)
                    .addComponent(Time_RadioButton)
                    .addComponent(TimeStamp_RadioButton))
                .addContainerGap())
        );
        Temporal_LayeredPane.setLayer(Date_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        Temporal_LayeredPane.setLayer(Time_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        Temporal_LayeredPane.setLayer(TimeStamp_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        extendType_LayeredPane.add(Temporal_LayeredPane);

        jLayeredPane1.add(extendType_LayeredPane);
        extendType_LayeredPane.setBounds(120, 60, 320, 30);

        org.openide.awt.Mnemonics.setLocalizedText(dataType_Label, org.openide.util.NbBundle.getMessage(FieldTypePanel.class, "FieldTypePanel.dataType_Label.text")); // NOI18N

        dataType_ComboBox.setEditable(true);

        dataType_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jpa/modeler/properties/resource/searchbutton.png"))); // NOI18N
        dataType_Action.setPreferredSize(new java.awt.Dimension(37, 37));
        dataType_Action.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataType_ActionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataType_LayeredPaneLayout = new javax.swing.GroupLayout(dataType_LayeredPane);
        dataType_LayeredPane.setLayout(dataType_LayeredPaneLayout);
        dataType_LayeredPaneLayout.setHorizontalGroup(
            dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataType_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataType_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dataType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dataType_LayeredPaneLayout.setVerticalGroup(
            dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dataType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dataType_Label)))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        dataType_LayeredPane.setLayer(dataType_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dataType_LayeredPane.setLayer(dataType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dataType_LayeredPane.setLayer(dataType_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane1.add(dataType_LayeredPane);
        dataType_LayeredPane.setBounds(10, 100, 457, 38);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

//    private String previousType = null;
    private void type_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_type_ComboBoxActionPerformed
        String type = (String) type_ComboBox.getSelectedItem();
        Temporal_LayeredPane.setVisible(false);
        Enumerated_LayeredPane1.setVisible(false);
        if (null != type) {
            switch (type) {
                case ENUMERATED:
                    Enumerated_LayeredPane1.setVisible(true);
                    if (evt != null) {
                        Default_Ordinal_RadioButton.setSelected(true);
                    }
                    break;
                case TEMPORAL:
                    Temporal_LayeredPane.setVisible(true);
                    if (evt != null) {
                        Date_RadioButton.setSelected(true);
                    }
                    break;
//                    if (ENUMERATED.equals(previousType) || TEMPORAL.equals(previousType)) {
//                    }
            }
            initDataTypeComboBox();

        }
//        previousType = (String) type;
    }//GEN-LAST:event_type_ComboBoxActionPerformed

    private void dataType_ActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataType_ActionActionPerformed
        String dataType = NBModelerUtil.browseClass(modelerFile, (String)dataType_ComboBox.getSelectedItem());
        if (((DefaultComboBoxModel) dataType_ComboBox.getModel()).getIndexOf(dataType) == -1) {
            ((DefaultComboBoxModel) dataType_ComboBox.getModel()).addElement(dataType);
        }
        dataType_ComboBox.setSelectedItem(dataType);
    }//GEN-LAST:event_dataType_ActionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Date_RadioButton;
    private javax.swing.JRadioButton Default_Ordinal_RadioButton;
    private javax.swing.JLayeredPane Enumerated_LayeredPane1;
    private javax.swing.ButtonGroup Enumerated_buttonGroup;
    private javax.swing.JRadioButton Ordinal_RadioButton;
    private javax.swing.JRadioButton String_RadioButton;
    private javax.swing.JLayeredPane Temporal_LayeredPane;
    private javax.swing.ButtonGroup Temporal_buttonGroup;
    private javax.swing.JRadioButton TimeStamp_RadioButton;
    private javax.swing.JRadioButton Time_RadioButton;
    private javax.swing.JButton dataType_Action;
    private javax.swing.JComboBox dataType_ComboBox;
    private javax.swing.JLabel dataType_Label;
    private javax.swing.JLayeredPane dataType_LayeredPane;
    private javax.swing.JLayeredPane extendType_LayeredPane;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JComboBox type_ComboBox;
    private javax.swing.JLabel type_Label;
    private javax.swing.JLayeredPane type_LayeredPane;
    // End of variables declaration//GEN-END:variables

    private void selectEnumType(EnumType enumType) {
        if (enumType != null) {
            type_ComboBox.setSelectedItem(ENUMERATED);
            if (enumType == EnumType.STRING) {
                String_RadioButton.setSelected(true);
            } else if(enumType == EnumType.ORDINAL) {
                Ordinal_RadioButton.setSelected(true);
            } else {
                Default_Ordinal_RadioButton.setSelected(true);
            }
        }
    }

    private EnumType getSelectedEnumType() {
        if (String_RadioButton.isSelected()) {
            return EnumType.STRING;
        } else if (Ordinal_RadioButton.isSelected()) {
            return EnumType.ORDINAL;
        }
        return EnumType.DEFAULT;
    }

    private void selectedTemporalType(TemporalType temporalType) {
        if (temporalType != null) {
            type_ComboBox.setSelectedItem(TEMPORAL);
            if (temporalType == TemporalType.DATE) {
                Date_RadioButton.setSelected(true);
            } else if (temporalType == TemporalType.TIME) {
                Time_RadioButton.setSelected(true);
            } else if (temporalType == TemporalType.TIMESTAMP) {
                TimeStamp_RadioButton.setSelected(true);
            }
        } else {
            type_ComboBox.setSelectedItem(DEFAULT);
        }
    }

    private TemporalType getSelectedTemporalType() {
        if (Date_RadioButton.isSelected()) {
            return TemporalType.DATE;
        } else if (Time_RadioButton.isSelected()) {
            return TemporalType.TIME;
        } else if (TimeStamp_RadioButton.isSelected()) {
            return TemporalType.TIMESTAMP;
        }
        return null;
    }

    private final static String ENTITY = "Entity";
    private final static String TEMPORAL = "Temporal";
    private final static String DEFAULT = "Default";
    private final static String ENUMERATED = "Enumerated";
    private final static String EMBEDDABLE = "Embeddable";
    private final static String LOB = "Lob";

}
