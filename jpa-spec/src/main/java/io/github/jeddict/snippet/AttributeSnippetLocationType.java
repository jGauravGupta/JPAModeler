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
package io.github.jeddict.snippet;

public enum AttributeSnippetLocationType implements SnippetLocation {

    BEFORE_FIELD("Before Field"),
    AFTER_FIELD("After Field"),
    BEFORE_METHOD("Before Method"),
    AFTER_METHOD("After Method"),
    PROPERTY_JAVADOC("Property Javadoc"),
    GETTER_JAVADOC("Getter Javadoc"),
    GETTER_THROWS("Getter throws"),
    PRE_GETTER("Pre Getter"),
    GETTER("Getter"),
    POST_GETTER("Post Getter"),
    SETTER_JAVADOC("Setter Javadoc"),
    SETTER_THROWS("Setter throws"),
    PRE_SETTER("Pre Setter"),
    SETTER("Setter"),
    POST_SETTER("Post Setter"),
    PRE_FLUENT("Pre Fluent"),
    PRE_ADD_HELPER("Pre Add Helper"),
    ADD_HELPER("Add Helper"),
    PRE_REMOVE_HELPER("Pre Remove Helper"),
    REMOVE_HELPER("Remove Helper"),
    FLUENT("Fluent"),
    IMPORT("Import");
    
    private final String title;

    
    private AttributeSnippetLocationType(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
    
}
