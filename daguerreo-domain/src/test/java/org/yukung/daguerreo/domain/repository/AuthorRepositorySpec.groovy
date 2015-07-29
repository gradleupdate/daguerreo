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

package org.yukung.daguerreo.domain.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import org.yukung.daguerreo.App
import org.yukung.daguerreo.domain.model.Author
import spock.lang.Specification

/**
 * @author yukung
 */
@SpringApplicationConfiguration(classes = App.class)
class AuthorRepositorySpec extends Specification {

    @Autowired
    AuthorRepository repository

    @Transactional
    @Rollback
    def "should be registered a author"() {
        given:
        def author = new Author(id: null, name: '宮沢賢治')

        when:
        def actual = repository.save(author)

        then:
        repository.findOne(actual.id).present
    }
}
