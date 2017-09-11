package mx.com.acerozin.security

import org.springframework.security.core.Authentication
import spock.lang.Specification

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date 15/08/2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved
 *
 */

@TestFor(AuthService)
class AuthServiceSpec extends Specification {

    def authService

    def setup() {
        authService = new AuthService()
    }

    def cleanup() {
    }

    void testLogin() {
        given:
        def res
        when:
        res = authService.login("john", "john")
        then:
        res instanceof Authentication

    }
}
