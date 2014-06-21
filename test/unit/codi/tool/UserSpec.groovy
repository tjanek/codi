package codi.tool

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(User)
class UserSpec extends Specification {

    void "Test email for user"() {
        when: 'email is blank'
        def user = new User(email: '')

        then: 'validation should fail'
        !user.validate(['email'])

        when: 'email is not set'
        user = new User()

        then: 'validation should fail'
        !user.validate(['email'])

        when: 'invalid email is set'
        user = new User(email: 'test')

        then: 'validation should fail'
        !user.validate(['email'])

        when: 'valid email is set'
        user = new User(email: 'test@test.pl')

        then: 'validation should pass'
        user.validate(['email'])
    }

    void "Test password for user"() {
        when: 'password is blank'
        def user = new User(password: '')

        then: 'validation should fail'
        !user.validate(['password'])

        when: 'password is not set'
        user = new User()

        then: 'validation should fail'
        !user.validate(['password'])

        when: 'password has wrong size'
        user = new User(password: 'pass')

        then: 'validation should fail'
        !user.validate(['password'])

        when: 'valid password is set'
        user = new User(password: 'password')

        then: 'validation should pass'
        user.validate(['password'])
    }

    void "Test username for user"() {
        when: 'username is not set'
        def user = new User()

        then: 'validation should fail'
        !user.validate(['name'])

        when: 'username is set'
        user = new User(name: "Some user")

        then: 'validation should pass'
        user.validate(['name'])
    }
}
