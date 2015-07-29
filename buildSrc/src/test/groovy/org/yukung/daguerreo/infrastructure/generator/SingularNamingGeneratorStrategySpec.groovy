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

package org.yukung.daguerreo.infrastructure.generator
import org.jooq.util.Definition
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static org.jooq.util.GeneratorStrategy.Mode.*

/**
 * @author yukung
 */
class SingularNamingGeneratorStrategySpec extends Specification {
    @Shared
    def authorsDef = Mock(Definition)
    @Shared
    def contentsDef = Mock(Definition)
    @Shared
    def statusesDef = Mock(Definition)
    @Shared
    def categoriesDef = Mock(Definition)
    @Shared
    def peopleDef = Mock(Definition)
    @Shared
    def snakeCaseDef = Mock(Definition)

    void setupSpec() {
        authorsDef.getOutputName() >> 'authors'
        contentsDef.getOutputName() >> 'contents'
        statusesDef.getOutputName() >> 'statuses'
        categoriesDef.getOutputName() >> 'categories'
        peopleDef.getOutputName() >> 'people'
        snakeCaseDef.getOutputName() >> 'sales_summaries'
    }

    @Unroll
    def "should return #result if given #definition.outputName"() {
        setup:
        def strategy = new SingularNamingGeneratorStrategy()

        expect:
        strategy.getJavaIdentifier(definition) == result

        where:
        definition    || result
        authorsDef    || 'AUTHOR'
        contentsDef   || 'CONTENT'
        statusesDef   || 'STATUS'
        categoriesDef || 'CATEGORY'
        peopleDef     || 'PERSON'
    }

    @Unroll
    def "should return #result if given table name is #definition.outputName and parameter type is #mode"() {
        setup:
        def strategy = new SingularNamingGeneratorStrategy()

        expect:
        strategy.getJavaClassName(definition, mode) == result

        where:
        definition    | mode      || result
        authorsDef    | DEFAULT   || 'Author'
        authorsDef    | RECORD    || 'AuthorRecord'
        authorsDef    | DAO       || 'AuthorDao'
        authorsDef    | INTERFACE || 'IAuthor'
        statusesDef   | DEFAULT   || 'Status'
        statusesDef   | RECORD    || 'StatusRecord'
        statusesDef   | DAO       || 'StatusDao'
        statusesDef   | INTERFACE || 'IStatus'
        categoriesDef | DEFAULT   || 'Category'
        categoriesDef | RECORD    || 'CategoryRecord'
        categoriesDef | DAO       || 'CategoryDao'
        categoriesDef | INTERFACE || 'ICategory'
        peopleDef     | DEFAULT   || 'Person'
        peopleDef     | RECORD    || 'PersonRecord'
        peopleDef     | DAO       || 'PersonDao'
        peopleDef     | INTERFACE || 'IPerson'
        snakeCaseDef  | DEFAULT   || 'SalesSummary'
        snakeCaseDef  | RECORD    || 'SalesSummaryRecord'
        snakeCaseDef  | DAO       || 'SalesSummaryDao'
        snakeCaseDef  | INTERFACE || 'ISalesSummary'
    }
}
