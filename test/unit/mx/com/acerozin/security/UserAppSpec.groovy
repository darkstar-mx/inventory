package mx.com.acerozin.security

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import mx.com.acerozin.persondetails.Person
import mx.com.acerozin.pogo.security.UserWrapper
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class UserAppSpec extends Specification {

    UserApp user

    def setup() {

        user = new UserApp()
        user.username = "john"
        user.password = "john"
        user.isAccountEnabled = true
        user.nonExpiredAccount = true
        user.nonLockedAccount = true
        user.nonExpiredCredentials = true
        user.forceLocalLogin = true
        user.locale = new Locale("en")
        user.salt = "some_salt".hashCode()

        Person person = new Person()
        person.firstName = "John"
        person.lastName = "Angland"
        person.title = "Mr."
        person.male = true
        person.email = "john.angland@mail.com"
        person.location = "U.S.A."
        person.phone = "none"
        person.isCompany = false
        person.save(failOnError: true)

        user.person = person

    }

    def cleanup() {
    }

    void testCastAsUserWrapper() {
        def tmpObj = user as UserWrapper
        println tmpObj.dump()

        assertTrue tmpObject instanceof UserWrapper
    }
}
