/*
 * Copyright 2015 Yusuke Ikeda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yukung.daguerreo.util;

import org.jooq.tools.StringUtils;
import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;
import org.modeshape.common.text.Inflector;

/**
 * @author yukung
 */
public class SingularNamingGeneratorStrategy extends DefaultGeneratorStrategy {

    private final Inflector inflector = Inflector.getInstance();

    @Override
    public String getJavaIdentifier(Definition definition) {
        return inflector.singularize(definition.getOutputName()).toUpperCase();
    }

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        StringBuilder result = new StringBuilder();

        result.append(StringUtils.toCamelCase(inflector.singularize(definition.getOutputName())));

        switch (mode) {
            case RECORD:
                result.append("Record");
                break;
            case DAO:
                result.append("Dao");
                break;
            case INTERFACE:
                result.insert(0, "I");
                break;
        }

        return result.toString();
    }
}
