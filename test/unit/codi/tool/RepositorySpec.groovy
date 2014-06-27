package codi.tool

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import static codi.tool.Repository.RepositoryStatus.sync

@TestFor(Repository)
class RepositorySpec extends Specification {

    @Unroll("Repository field '#field' with value '#value' should get be validated #validate")
    def "Repository should be validated"() {
        when:
        def repository = new Repository("$field": value)

        then:
        repository.validate([field]) == validate

        where:
        field      | value           | validate
        'name'     | ' '             | false
        'name'     | null            | false
        'name'     | 'some repo'     | true
        'url'      | ' '             | false
        'url'      | null            | false
        'url'      | 'some url'      | true
        'path'     | ' '             | false
        'path'     | null            | false
        'path'     | 'some path'     | true
        'status'   | null            | false
        'status'   | sync            | true
    }
}
