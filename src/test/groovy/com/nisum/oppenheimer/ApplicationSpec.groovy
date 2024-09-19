package com.nisum.oppenheimer


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertNotNull

@SpringBootTest(classes = Application)
class ApplicationSpec extends Specification {

    @Autowired ApplicationContext context

    def "When context is loaded then is expected to be not null"() {
        expect:
        assertNotNull(context)
    }
}