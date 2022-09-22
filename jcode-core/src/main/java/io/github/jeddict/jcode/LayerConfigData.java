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
package io.github.jeddict.jcode;

import java.io.Serializable;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;

/**
 *
 * @author Gaurav Gupta
 */
public abstract class LayerConfigData<T extends LayerConfigData> implements Serializable {

    @JsonbTransient
    private T parentLayerConfigData;

    /**
     * @return the parentLayerConfigData
     */
    public T getParentLayerConfigData() {
        return parentLayerConfigData;
    }

    /**
     * @param parentLayerConfigData the parentLayerConfigData to set
     */
    public void setParentLayerConfigData(T parentLayerConfigData) {
        this.parentLayerConfigData = parentLayerConfigData;
        onLayerConnection();
    }

    @JsonbTransient
    protected void onLayerConnection() {

    }

    @JsonbTransient
    public abstract List<String> getUsageDetails();

}
