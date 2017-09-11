package mx.com.acerozin.security

import com.vaadin.grails.Grails
import com.vaadin.server.VaadinService
import com.vaadin.server.VaadinSession
import grails.compiler.GrailsCompileStatic
import grails.transaction.Transactional
import groovy.transform.TypeCheckingMode
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date 15/08/2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@GrailsCompileStatic
@Transactional
class AuthService {

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    Authentication login(String username, String password) {
        log.info "Attempting to login, username: " + username
        UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password)
        AuthenticationManager authenticationManager = Grails.get(AuthManager)
        Authentication result = authenticationManager.authenticate(request)

        // Reinitialize the session to protect against session fixation attacks. This does not work
        // with websocket communication.
        VaadinService.reinitializeSession(VaadinService.getCurrentRequest());

        SecurityContextHolder.getContext().setAuthentication(result)
        return result
    }

    def logout(String username) {
        SecurityContextHolder.context.authentication = null
        VaadinSession.setCurrent(null)
    }

    //@GrailsCompileStatic(TypeCheckingMode.SKIP)
    UserApp userDetails(String username, String password) {
        log.info "Obtaining user details: Start"
        UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password)
        AuthenticationManager authenticationManager = Grails.get(AuthManager)
        return authenticationManager.getUserDetails(request)
    }
}
