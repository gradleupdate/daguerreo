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

package org.yukung.daguerreo.infrastructure.util

import spock.lang.Specification

import java.sql.Timestamp
import java.time.LocalDateTime

/**
 * @author yukung
 */
class LocalDateTimeConverterSpec extends Specification {

    private LocalDateTimeConverter converter

    def setup() {
        converter = new LocalDateTimeConverter()
    }

    def "should return 'java.time.LocalDateTime' if given 'java.sql.Timestamp' parameter"() {
        expect:
        converter.from(Timestamp.valueOf(date)) == LocalDateTime.parse(iso8601Date)

        where:
        date                   || iso8601Date
        '2015-01-01 00:00:000' || '2015-01-01T00:00:00'
        '1970-01-01 00:00:000' || '1970-01-01T00:00:00'
        '2038-01-20 00:00:000' || '2038-01-20T00:00:00'
    }

    def "should return 'java.sql.Timestamp' if given 'java.time.LocalDateTime' parameter"() {
        expect:
        converter.to(LocalDateTime.parse(iso8601Date)) == Timestamp.valueOf(date)

        where:
        iso8601Date           || date
        '2015-01-01T00:00:00' || '2015-01-01 00:00:000'
        '1970-01-01T00:00:00' || '1970-01-01 00:00:000'
        '2038-01-20T00:00:00' || '2038-01-20 00:00:000'
    }

    def "'fromType()' should return 'java.sql.Timestamp'"() {
        expect:
        converter.fromType() == Timestamp
    }

    def "'toType()' should return 'java.time.LocalDateTime'"() {
        expect:
        converter.toType() == LocalDateTime
    }
}
