package codi.tool

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(Branch)
class BranchSpec extends Specification {

    @Unroll('Branch #field with value #value should get be validated #validate')
    def "Branch should be validated"() {
        when:
        def branch = new Branch("$field": value)

        then:
        branch.validate([field]) == validate

        where:
        field      | value           | validate
        'name'     | ''              | false
        'name'     | null            | false
        'name'     | 'master'        | true
        'prefix'   | ''              | false
        'prefix'   | null            | false
        'prefix'   | 'ref/heads/'    | true
    }
}
