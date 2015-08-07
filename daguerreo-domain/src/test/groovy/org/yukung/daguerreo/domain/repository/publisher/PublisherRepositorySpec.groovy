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

package org.yukung.daguerreo.domain.repository.publisher

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import org.yukung.daguerreo.domain.config.DomainTestConfig
import org.yukung.daguerreo.domain.model.Publisher
import spock.lang.Specification

/**
 * @author yukung
 */
@ActiveProfiles("test")
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = DomainTestConfig)
@Transactional
class PublisherRepositorySpec extends Specification {

    @Autowired
    PublisherRepository repository

    @Rollback
    def "should be registered a publisher"() {
        given:
        def publisher = new Publisher(id: null, name: '新潮社')

        when:
        def created = repository.save(publisher)

        then:
        repository.findOne(created.id).present
    }

    @Rollback
    def "should be registered or updated all authors"() {
        given:
        def preSavedPublisher = new Publisher(id: null, name: '事前保存')
        def saved = repository.save(preSavedPublisher)
        def expected = '更新後'
        saved.name = expected
        def publishers = [
                new Publisher(id: null, name: '新潮社'),
                new Publisher(id: null, name: '講談社'),
                saved,
                new Publisher(id: null, name: '角川書店')
        ]

        when:
        List<Publisher> created = repository.save(publishers)

        then:
        created.eachWithIndex { publisher, i -> publisher.equals(publishers[i]) }

        and:
        def updated = repository.findOne(saved.id)
        updated.present

        updated.map { e -> e.name }.orElse("") == expected
    }

    @Rollback
    def "should be updated the publisher"() {
        given:
        def publisher = repository.save(new Publisher(id: null, name: '新潮社'))
        def expected = '講談社'
        publisher.name = expected

        when:
        repository.save(publisher)

        then:
        repository.findOne(publisher.id)

        then:
        def updated = repository.findOne(publisher.id)
        updated.present

        updated.map { e -> e.name }.orElse("") == expected
    }

    @Rollback
    def "should be deleted the publisher by Id"() {
        given:
        def publisher = repository.save(new Publisher(id: null, name: '新潮社'))
        def id = publisher.id
        repository.findOne(id).present

        when:
        repository.delete(id)

        then:
        !repository.findOne(id).present
    }

    @Rollback
    def "should be deleted the publisher by entity"() {
        given:
        def publisher = repository.save(new Publisher(id: null, name: '新潮社'))
        repository.findOne(publisher.id).present

        when:
        repository.delete(publisher)

        then:
        !repository.findOne(publisher.id).present
    }

    @Rollback
    def "should be deleted all publishers"() {
        given:
        def publishers = [new Publisher(id: null, name: '新潮社'), new Publisher(id: null, name: '講談社'), new Publisher(id: null, name: '角川書店')]
        repository.save(publishers)
        def allPublishers = repository.findAll()

        when:
        repository.delete(allPublishers)

        then:
        repository.findAll(allPublishers.collect { it.id }).size() == 0
    }

    @Rollback
    def "should be find all publishers"() {
        given:
        def publishers = [new Publisher(id: null, name: '新潮社'), new Publisher(id: null, name: '講談社'), new Publisher(id: null, name: '角川書店')]
        repository.save(publishers)

        when:
        List<Publisher> result = repository.findAll()

        then:
        result.eachWithIndex { publisher, i -> publisher.equals(publishers[i]) }
    }

    @Rollback
    def "should be to count the number of publishers"() {
        given:
        def publishers = [new Publisher(id: null, name: '新潮社'), new Publisher(id: null, name: '講談社'), new Publisher(id: null, name: '角川書店')]
        repository.save(publishers)

        when:
        def count = repository.count()

        then:
        count == (long) publishers.size()
    }

    @Rollback
    def "should be exists by specified entity"() {
        given:
        def existsPublisher = repository.save(new Publisher(id: null, name: '新潮社'))
        def notExistsPublisher = new Publisher(id: null, name: '講談社')

        expect:
        repository.exists(existsPublisher.id)

        and:
        !repository.exists(notExistsPublisher.id)
    }
}
