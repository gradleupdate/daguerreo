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

package org.yukung.daguerreo.domain.repository.book;

import org.jooq.DSLContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yukung.daguerreo.domain.model.Author;
import org.yukung.daguerreo.domain.model.Book;
import org.yukung.daguerreo.domain.model.Publisher;
import org.yukung.daguerreo.infrastructure.generated.tables.records.AuthorRecord;
import org.yukung.daguerreo.infrastructure.generated.tables.records.BookRecord;
import org.yukung.daguerreo.infrastructure.generated.tables.records.PublisherRecord;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.yukung.daguerreo.infrastructure.generated.tables.Author.*;
import static org.yukung.daguerreo.infrastructure.generated.tables.Book.*;
import static org.yukung.daguerreo.infrastructure.generated.tables.Publisher.*;

/**
 * @author Yusuke Ikeda
 */
@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private DSLContext dsl;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<Book> findOne(Long id) {
        Book book = dsl
                .selectFrom(BOOK
                        .join(AUTHOR)
                        .on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
                        .join(PUBLISHER)
                        .on(BOOK.PUBLISHER_ID.eq(PUBLISHER.ID)))
                .where(BOOK.ID.eq(id))
                .fetchOne(record -> {
                    Book b = record.into(Book.class);
                    AuthorRecord authorRecord = record.into(AUTHOR);
                    PublisherRecord publisherRecord = record.into(PUBLISHER);
                    b.setAuthor(mapper.map(authorRecord, Author.class));
                    b.setPublisher(mapper.map(publisherRecord, Publisher.class));
                    return b;
                });
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public List<Book> findAll(Collection<Long> ids) {
        return null;
    }

    @Override
    public Book save(Book book) {
        BookRecord bookRecord;
        if (book.getId() == null) {
            bookRecord = dsl.newRecord(BOOK, book);
            mapper.map(book, bookRecord);
        } else {
            BookRecord fetched = dsl.selectFrom(BOOK)
                    .where(BOOK.ID.eq(book.getId()))
                    .forUpdate()
                    .fetchOne();
            bookRecord = (fetched != null) ? fetched : dsl.newRecord(BOOK, book);
            bookRecord.from(book);
            mapper.map(book, bookRecord);
        }
        bookRecord.store();
        return bookRecord.into(Book.class);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(Book book) {

    }

    @Override
    public void delete(Collection<Book> books) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }
}
