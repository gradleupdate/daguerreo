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

import org.yukung.daguerreo.domain.model.Publisher;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author yukung
 */
public interface PublisherRepository {

    Optional<Publisher> findOne(Long id);

    List<Publisher> findAll();

    List<Publisher> findAll(Collection<Long> ids);

    Publisher save(Publisher publisher);

    List<Publisher> save(Collection<Publisher> publishers);

    void delete(Long id);

    void delete(Publisher publisher);

    void delete(Collection<Publisher> publishers);

    long count();

    boolean exists(Long id);
}
