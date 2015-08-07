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

package org.yukung.daguerreo.domain.repository.book

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import org.yukung.daguerreo.domain.config.DomainTestConfig
import org.yukung.daguerreo.domain.model.Author
import org.yukung.daguerreo.domain.model.Book
import org.yukung.daguerreo.domain.model.Publisher
import org.yukung.daguerreo.domain.repository.author.AuthorRepository
import org.yukung.daguerreo.domain.repository.publisher.PublisherRepository
import spock.lang.Specification

import java.time.LocalDate

/**
 * @author yukung
 */
@ActiveProfiles("test")
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = DomainTestConfig)
@Transactional
class BookRepositorySpec extends Specification {

    @Autowired
    BookRepository bookRepository

    @Autowired
    AuthorRepository authorRepository

    @Autowired
    PublisherRepository publisherRepository

    @Rollback
    def "should be registered a book"() {
        given:
        def book = new Book(
                id: null,
                isbn: '9784101092058',
                title: '新編銀河鉄道の夜',
                pages: 423,
                publishedOn: LocalDate.of(2012, 4, 1),
                price: 464,
                author: authorRepository.save(new Author(id: null, name: '宮沢賢治')),
                publisher: publisherRepository.save(new Publisher(id: null, name: '新潮社'))
        )

        when:
        def created = bookRepository.save(book)

        then:
        bookRepository.findOne(created.id).present
        println bookRepository.findOne(created.id).get().id
    }

    def "FindAll"() {

    }

    def "Save"() {

    }

    def "Delete"() {

    }

    def "Delete1"() {

    }

    def "Count"() {

    }

    def "Exists"() {

    }
}
