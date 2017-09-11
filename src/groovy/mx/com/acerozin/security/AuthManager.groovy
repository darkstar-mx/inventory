package mx.com.acerozin.security

import grails.compiler.GrailsCompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority

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
class AuthManager implements AuthenticationManager {

    @Autowired
    private UserAppService userAppService

    def Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = (String) auth.principal
        String password = (String) auth.credentials

        UserApp user = userAppService.findByUsernameAndPassword(username, password)
        if (user) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities()

            return new UsernamePasswordAuthenticationToken(username, password, authorities)
        }
        return null
        //throw new BadCredentialsException("Bad Credentials")
    }

    def UserApp getUserDetails(Authentication auth) {
        String username = (String) auth.principal
        String password = (String) auth.credentials
        return userAppService.findByUsernameAndPassword(username, password)
    }

}
