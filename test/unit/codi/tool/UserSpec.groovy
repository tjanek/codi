package codi.tool

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(User)
class UserSpec extends Specification {

    @Unroll('User #field with value #value should get be validated #validate')
    def 'User should be validated'() {
        when:
        def user = new User("$field": value)

        then:
        user.validate([field]) == validate

        where:
        field      | value           | validate
        'email'    | ' '             | false
        'email'    | null            | false
        'email'    | 'test'          | false
        'email'    | 'test@user.com' | true
        'password' | ' '             | false
        'password' | null            | false
        'password' | 'pass'          | false
        'password' | 'password'      | true
        'name'     | null            | false
        'name'     | 'Some user'     | true
    }
}
