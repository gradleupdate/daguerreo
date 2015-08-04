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

package org.yukung.daguerreo.domain.repository.author

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.yukung.daguerreo.domain.config.DomainTestConfig
import org.yukung.daguerreo.domain.model.Author
import spock.lang.Specification

/**
 * @author yukung
 */
@ActiveProfiles("test")
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = DomainTestConfig)
class AuthorRepositorySpec extends Specification {

    @Autowired
    AuthorRepository repository

    def setup() {
    }

    def cleanup() {
    }

    def "should be registered a author"() {
        given:
        def author = new Author(id: null, name: '宮沢賢治')

        when:
        def created = repository.save(author)

        then:
        repository.findOne(created.id).present
    }

    def "should be updated the author"() {
        given:
        def author = repository.save(new Author(id: null, name: '川端康成'))
        def expected = '芥川龍之介'
        author.name = expected

        when:
        repository.save(author)

        then:
        def updated = repository.findOne(author.id)
        updated.present

        updated.map { e -> e.name }.orElse("") == expected
    }
}
