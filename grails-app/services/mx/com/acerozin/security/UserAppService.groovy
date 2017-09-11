package mx.com.acerozin.security

import grails.compiler.GrailsCompileStatic
import grails.transaction.Transactional
import grails.validation.ValidationException
import groovy.transform.TypeCheckingMode
import mx.com.acerozin.pogo.security.UserWrapper
import org.codehaus.groovy.runtime.typehandling.GroovyCastException

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date 15/08/2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved
 *
 */
@Transactional
@GrailsCompileStatic
class UserAppService {

    def bcryptService

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    UserApp findByUsernameAndPassword(final String username, final String password) {
        log.info "findByUsernameAndPassword:: username: " + username
        def returnUser = null
        UserApp user = UserApp.findByUsernameAndPasswordIsNotNull(username)

        if (user) {
            // if true continue, else bye.
            returnUser = bcryptService.checkPassword(password, user.password) ? user : null
        }
        return returnUser
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    def getUserDetails(String username) {
        log.info "getUserDetails::Getting user details for: " + username
        return UserApp.findByUsername(username)
    }

    Collection<UserWrapper> getAllUsers() {
        log.info "getAllUsers::Getting all users "
        List<UserWrapper> result = new ArrayList<UserWrapper>()
        UserApp.findAll().each {
            result << (it as UserWrapper)
        }
        return result
    }

    Boolean updateUser(UserWrapper userDetails) {
        log.info "updateUser::Updating user: " + userDetails.dump()
        try {
            UserApp userApp = (UserApp)userDetails.asType(UserApp)
            return userApp.save(failOnError: true)?:false
        } catch (ValidationException/*|GroovyCastException*/ ex) {
            log.info "updateUser::" + ex.message
            return false
        }
        return false
    }

}
