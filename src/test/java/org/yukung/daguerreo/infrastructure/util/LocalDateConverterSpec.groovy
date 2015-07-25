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

import java.time.LocalDate

/**
 * @author yukung
 */
class LocalDateConverterSpec extends Specification {

    LocalDateConverter converter

    void setup() {
        converter = new LocalDateConverter()
    }

    def "should return 'java.time.LocalDate' if given 'java.sql.Date' parameter"() {
        expect:
        converter.from(java.sql.Date.valueOf(date)) == LocalDate.parse(date)

        where:
        date << ['2015-01-01', '1969-12-31', '2038-01-20']
    }

    def "should return 'java.sql.Date' if given 'java.time.LocalDate' parameter"() {
        expect:
        converter.to(LocalDate.parse(date)) == java.sql.Date.valueOf(date)

        where:
        date << ['2015-01-01', '1969-12-31', '2038-01-20']
    }

    def "'fromType()' should return 'java.sql.Date'"() {
        expect:
        converter.fromType() == java.sql.Date
    }

    def "'toType()' should return 'java.time.LocalDate'"() {
        expect:
        converter.toType() == LocalDate
    }
}
