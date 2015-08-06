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

package org.yukung.daguerreo.domain.repository.publisher;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yukung.daguerreo.domain.model.Publisher;
import org.yukung.daguerreo.infrastructure.generated.tables.records.PublisherRecord;

import java.util.List;
import java.util.Optional;

import static org.yukung.daguerreo.infrastructure.generated.tables.Publisher.*;

/**
 * @author yukung
 */
@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

    @Autowired
    private DSLContext dsl;

    @Override
    public Optional<Publisher> findOne(Long id) {
        Publisher publisher = dsl.selectFrom(PUBLISHER)
                .where(PUBLISHER.ID.eq(id))
                .fetchOneInto(Publisher.class);
        return Optional.ofNullable(publisher);
    }

    @Override
    public List<Publisher> findAll() {
        return dsl.selectFrom(PUBLISHER)
                .fetchInto(Publisher.class);
    }

    @Override
    public Publisher save(Publisher publisher) {
        PublisherRecord publisherRecord;
        if (publisher.getId() == null) {
            publisherRecord = dsl.newRecord(PUBLISHER, publisher);
        } else {
            publisherRecord = dsl.fetchOne(PUBLISHER, PUBLISHER.ID.eq(publisher.getId()));
            if (publisherRecord == null) {
                return null;
            }
            publisherRecord.from(publisher);
        }
        publisherRecord.store();
        return publisherRecord.into(Publisher.class);
    }

    @Override
    public void delete(Long id) {
        dsl.deleteFrom(PUBLISHER)
                .where(PUBLISHER.ID.eq(id))
                .execute();
    }

    @Override
    public void delete(Publisher publisher) {
        delete(publisher.getId());
    }

    @Override
    public long count() {
        return dsl.selectCount()
                .from(PUBLISHER)
                .fetchOneInto(Long.class);
    }

    @Override
    public boolean exists(Long id) {
        return dsl.selectCount()
                .from(PUBLISHER)
                .where(PUBLISHER.ID.eq(id))
                .fetchOneInto(Integer.class) > 0;
    }
}
