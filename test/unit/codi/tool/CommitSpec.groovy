package codi.tool

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll


@TestFor(Commit)
class CommitSpec extends Specification {

    @Unroll("Commit field '#field' with value '#value' should get be validated #validate")
    def "Commit should be validated"() {
        when:
        def commit = new Commit("$field": value)

        then:
        commit.validate([field]) == validate

        where:
        field         | value           | validate
        'identifier'  | ' '             | false
        'identifier'  | null            | false
        'identifier'  | '1e'            | true
        'committedAt' | null            | false
        'committedAt' | new Date()      | true
    }

}
