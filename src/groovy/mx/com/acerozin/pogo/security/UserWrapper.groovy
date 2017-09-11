package mx.com.acerozin.pogo.security

import groovy.util.logging.Log4j
import mx.com.acerozin.pogo.core.company.CompanyWrapper
import mx.com.acerozin.pogo.persondetails.PersonWrapper
import mx.com.acerozin.presenter.utils.CommonRoutines
import mx.com.acerozin.security.UserApp

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Nov 7, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@Log4j
class UserWrapper implements Serializable {
    Long id
    String username
    String password
    Boolean nonExpiredAccount
    Boolean nonLockedAccount
    Boolean nonExpiredCredentials
    Boolean forceLocalLogin
    Boolean isAccountEnabled
    PersonWrapper person
    Locale locale
    CompanyWrapper company
    String defaultView
    Collection<RoleWrapper> roles

    Object asType(Class clazz) {
        log.info "asType:: Applying conversion to " + clazz.name
        if (clazz == UserApp) {
            try {
                return CommonRoutines.copyProperties(this, clazz)
            } catch (InstantiationException | IllegalAccessException ex){
                log.error ex.message
            }
        } else {
            super.asType(clazz)
        }
    }

}
