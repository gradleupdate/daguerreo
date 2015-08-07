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

package org.yukung.daguerreo.domain.repository.author;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yukung.daguerreo.domain.model.Author;
import org.yukung.daguerreo.infrastructure.generated.tables.records.AuthorRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.yukung.daguerreo.infrastructure.generated.tables.Author.*;

/**
 * @author yukung
 */
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @Autowired
    private DSLContext dsl;

    @Override
    public Optional<Author> findOne(Long id) {
        Author author = dsl.selectFrom(AUTHOR)
                .where(AUTHOR.ID.eq(id))
                .fetchOneInto(Author.class);
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> findAll() {
        return dsl.selectFrom(AUTHOR)
                .fetchInto(Author.class);
    }

    @Override
    public List<Author> findAll(Collection<Long> ids) {
        return dsl.selectFrom(AUTHOR)
                .where(AUTHOR.ID.in(ids))
                .fetchInto(Author.class);
    }

    @Override
    public Author save(Author author) {
        AuthorRecord authorRecord;
        if (author.getId() == null) {
            authorRecord = dsl.newRecord(AUTHOR, author);
        } else {
            AuthorRecord fetched = dsl
                    .selectFrom(AUTHOR)
                    .where(AUTHOR.ID.eq(author.getId()))
                    .forUpdate()
                    .fetchOne();
            authorRecord = (fetched != null) ? fetched : dsl.newRecord(AUTHOR, author);
            authorRecord.from(author);
        }
        authorRecord.store();
        return authorRecord.into(Author.class);
    }

    @Override
    public List<Author> save(Collection<Author> authors) {
        if (authors.size() == 1) {
            return Collections.singletonList(save(authors.iterator().next()));
        } else {
            List<Author> result = new ArrayList<>();
            authors.forEach(author -> result.add(save(author)));
            return result;
        }
    }

    @Override
    public void delete(Long id) {
        dsl.deleteFrom(AUTHOR)
                .where(AUTHOR.ID.eq(id))
                .execute();
    }

    @Override
    public void delete(Author author) {
        delete(author.getId());
    }

    @Override
    public void delete(Collection<Author> authors) {
        dsl.deleteFrom(AUTHOR)
                .where(AUTHOR.ID.in(authors.stream()
                        .map(Author::getId)
                        .collect(Collectors.toList())))
                .execute();
    }

    @Override
    public long count() {
        return dsl.selectCount()
                .from(AUTHOR)
                .fetchOneInto(Long.class);
    }

    @Override
    public boolean exists(Long id) {
        return dsl.selectCount()
                .from(AUTHOR)
                .where(AUTHOR.ID.eq(id))
                .fetchOneInto(Integer.class) > 0;
    }
}
