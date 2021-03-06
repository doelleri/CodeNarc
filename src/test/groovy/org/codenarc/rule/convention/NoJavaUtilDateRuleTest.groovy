/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.convention

import org.codenarc.rule.AbstractRuleTestCase
import org.junit.Test

/**
 * Tests for NoJavaUtilDateRule
 *
 * @author Eric Helgeson
 * @author Chris Mair
 */
class NoJavaUtilDateRuleTest extends AbstractRuleTestCase<NoJavaUtilDateRule> {

    private static final VIOLATION_MESSAGE = NoJavaUtilDateAstVisitor.VIOLATION_MESSAGE

    @Test
    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'NoJavaUtilDate'
    }

    @Test
    void test_NoViolations() {
        final SOURCE = '''
            def timestamp = new LocalDateTime()
            MyDate getMyDate(LocalDate localDate) { }
        '''
        assertNoViolations(SOURCE)
    }

    @Test
    void test_UsingExistingDateObjectsAndTypes_NoViolations() {
        final SOURCE = '''
            def getMyDate(java.util.Date date) { }
            def getOtherDate(String name, Date date) { }
        '''
        assertNoViolations(SOURCE)
    }

    @Test
    void test_ConstructingANewDateObject_Violations() {
        final SOURCE = '''
            def timestamp = new Date()
            Date myDate = new java.util.Date()
            Date startTime = new Date(123456789L)
        '''
        assertViolations(SOURCE,
                [lineNumber:2, sourceLineText:'def timestamp = new Date()', messageText:VIOLATION_MESSAGE],
                [lineNumber:3, sourceLineText:'Date myDate = new java.util.Date()', messageText:VIOLATION_MESSAGE],
                [lineNumber:4, sourceLineText:'Date startTime = new Date(123456789L)', messageText:VIOLATION_MESSAGE])
    }

    @Override
    protected NoJavaUtilDateRule createRule() {
        new NoJavaUtilDateRule()
    }

}
