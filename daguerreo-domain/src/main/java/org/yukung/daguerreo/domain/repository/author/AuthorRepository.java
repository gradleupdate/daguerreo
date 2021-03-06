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

import org.yukung.daguerreo.domain.model.Author;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author yukung
 */
public interface AuthorRepository {

    Optional<Author> findOne(Long id);

    List<Author> findAll();

    List<Author> findAll(Collection<Long> ids);

    Author save(Author author);

    List<Author> save(Collection<Author> authors);

    void delete(Long id);

    void delete(Author author);

    void delete(Collection<Author> authors);

    long count();

    boolean exists(Long id);
}
