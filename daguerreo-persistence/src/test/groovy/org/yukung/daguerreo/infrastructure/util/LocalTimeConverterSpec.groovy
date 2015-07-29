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

import java.sql.Time
import java.time.LocalTime

/**
 * @author yukung
 */
class LocalTimeConverterSpec extends Specification {

    LocalTimeConverter converter

    def setup() {
        converter = new LocalTimeConverter()
    }

    def "should return 'java.time.LocalTime' if given 'java.sql.Date' parameter"() {
        expect:
        converter.from(Time.valueOf(date)) == LocalTime.parse(date)

        where:
        date << ['00:00:00', '23:59:59']
    }

    def "should return 'java.sql.Time' if given 'java.time.LocalTime' paramter"() {
        expect:
        converter.to(LocalTime.parse(date)) == Time.valueOf(date)

        where:
        date << ['00:00:00', '23:59:59']
    }

    def "'fromType()' should return 'java.sql.Time'"() {
        expect:
        converter.fromType() == Time
    }

    def "'toType()' should return 'java.time.LocalTime'"() {
        expect:
        converter.toType() == LocalTime
    }
}
