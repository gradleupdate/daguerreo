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

package org.yukung.daguerreo.infrastructure.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yukung.daguerreo.domain.model.Author;
import org.yukung.daguerreo.domain.repository.AuthorRepository;
import org.yukung.daguerreo.infrastructure.generated.tables.daos.AuthorDao;
import org.yukung.daguerreo.infrastructure.generated.tables.records.AuthorRecord;

import java.util.Optional;

import static org.yukung.daguerreo.infrastructure.generated.Tables.*;

/**
 * @author yukung
 */
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @Autowired
    private DSLContext dsl;

    @Override
    public Optional<Author> findOne(Long id) {
        AuthorDao dao = new AuthorDao(dsl.configuration());
//        return Optional.of(dao.findById(id));
        return Optional.of(new Author());
    }

    @Override
    public Author save(Author author) {
        AuthorRecord authorRecord = dsl.newRecord(AUTHOR, author);
        authorRecord.store();
        return authorRecord.into(Author.class);
    }
}
