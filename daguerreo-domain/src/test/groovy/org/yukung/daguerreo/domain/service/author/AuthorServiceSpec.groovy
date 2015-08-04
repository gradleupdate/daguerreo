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

package org.yukung.daguerreo.domain.service.author

import org.yukung.daguerreo.domain.model.Author
import org.yukung.daguerreo.domain.repository.author.AuthorRepository
import spock.lang.Ignore
import spock.lang.Specification

/**
 * @author yukung
 */
@Ignore
// FIXME
class AuthorServiceSpec extends Specification {

    AuthorRepository repository

    void setup() {
        def stub1 = new Author(id: 1, name: '宮沢賢治')
        def stub2 = new Author(id: 2, name: '川端康成')
        def stub3 = new Author(id: 3, name: '芥川龍之介')
        def mock = Mock(AuthorRepository)
        mock.save(new Author(id: null, name: '宮沢賢治')) >> stub1
        mock.save(new Author(id: null, name: '川端康成')) >> stub2
        mock.save(new Author(id: null, name: '芥川龍之介')) >> stub3
        mock.findOne(1L) >> Optional.of(stub1)
        mock.findOne(2L) >> Optional.of(stub2)
        mock.findOne(3L) >> Optional.empty()
        mock.findAll() >> [stub1, stub2, stub3]
        mock.count() >> [stub1, stub2, stub3].size()
        mock.exists(stub2.id) >> true
        mock.exists(stub3.id) >> false
        repository = mock
    }

    void cleanup() {
    }

    def "should be registered a author"() {
        given:
        def author = new Author(id: null, name: '宮沢賢治')

        when:
        def actual = repository.save(author)

        then:
        repository.findOne(actual.id).present
    }

    def "should be updated the author"() {
        given:
        def author = repository.save(new Author(id: null, name: '川端康成'))
        def expected = '芥川龍之介'
        author.name = expected

        when:
        repository.save(author)

        then:
        def result = repository.findOne(author.id)
        result.present

        result.map { e -> e.name }.orElse("") == expected
    }

    def "should be deleted the author"() {
        given:
        def author = repository.save(new Author(id: null, name: '芥川龍之介'))
        def id = author.id

        when:
        repository.delete(id)

        then:
        !repository.findOne(id).present
    }

    def "should be deleted the author by specified object"() {
        given:
        def author = repository.save(new Author(id: null, name: '芥川龍之介'))
        def id = author.id

        when:
        repository.delete(author)

        then:
        !repository.findOne(id).present
    }

    def "should be find all authors"() {
        given:
        def authors = [new Author(id: null, name: '宮沢賢治'), new Author(id: null, name: '川端康成'), new Author(id: null, name: '芥川龍之介')]
        authors.each { repository.save(it) }

        when:
        List<Author> results = repository.findAll()

        then:
        results.eachWithIndex { Author result, i -> result.equals(authors[i]) }
    }

    def "should be to count the number of authors"() {
        given:
        def authors = [new Author(id: null, name: '宮沢賢治'), new Author(id: null, name: '川端康成'), new Author(id: null, name: '芥川龍之介')]
        authors.each { repository.save(it) }

        when:
        def count = repository.count()

        then:
        count == 3L
    }

    def "should be exists by specified object"() {
        given:
        def existsAuthor = repository.save(new Author(id: null, name: '川端康成'))
        def notExistsAuthor = new Author(id: 3, name: '芥川龍之介')

        when:
        boolean result = repository.exists(existsAuthor.id)

        then:
        result

        when:
        result = repository.exists(notExistsAuthor.id)

        then:
        !result
    }
}
