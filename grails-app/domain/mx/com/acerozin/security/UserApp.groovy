package mx.com.acerozin.security

import groovy.util.logging.Log4j
import mx.com.acerozin.core.company.Company
import mx.com.acerozin.persondetails.Person
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.utils.CommonRoutines
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date 15/08/2015
 * @Copyright © Armando Montoya 2015
 * All rights reserved
 *
 */

@Log4j
class UserApp implements UserDetails, Serializable {

    private static final long serialVersionUID = 1

    //transient bcryptService

    String username
    String password
    Boolean nonExpiredAccount
    Boolean nonLockedAccount
    Boolean nonExpiredCredentials
    Boolean forceLocalLogin
    Boolean isAccountEnabled
    Person person
    Locale locale
    Company company
    String defaultView

    static hasMany = [roles: RoleApp]
    static belongsTo = [RoleApp]

    static constraints = {
        username nullable: false, blank: false, unique: true
        password nullable: false, blank: false
        company nullable: false, blank: false
        defaultView nullable: false, blank: false
        nonExpiredAccount nullable: false, blank: false
        nonLockedAccount nullable: false, blank: false
        nonExpiredCredentials nullable: false, blank: false
        forceLocalLogin nullable: false, blank: false
        isAccountEnabled nullable: false, blank: false
    }

    //static embedded 	= ['userRoles']

    //static transients 	= ['bcryptService']

    static mapping = {
        password column: '`password`'
        nonLockedAccount column: '`accountLocked`'
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        //println "checking"
        return roles.collect { RoleApp authority ->
            new SimpleGrantedAuthority(authority.name)
        }
    }

    @Override
    boolean isAccountNonExpired() {
        return nonExpiredAccount
    }

    @Override
    boolean isAccountNonLocked() {
        return nonLockedAccount
    }

    @Override
    boolean isCredentialsNonExpired() {
        return nonExpiredCredentials
    }

    @Override
    boolean isEnabled() {
        return isAccountEnabled
    }

    def beforeInsert() {
        log.info "beforeInsert"
    }

    def beforeUpdate() {
        log.info "beforeUpdate"
    }
    /*
    protected void encodePassword() {
        log.info "encodePassword start"
        password = bcryptService?bcryptService.hashPassword(password): password
    }*/

    /**
     *
     * @see http://arturoherrero.com/create-your-own-groovy-type-conversion/
     * for further details on asType overriding
     *
     * */

    Object asType(Class clazz) {
        if (clazz == UserWrapper) {
            try {
                def userWrapper = CommonRoutines.copyProperties(this, clazz)
                return userWrapper
            }
            catch (InstantiationException | IllegalAccessException ex) {
                log.error ex.message
            }

        } else {
            super.asType(clazz)
        }
    }

}
