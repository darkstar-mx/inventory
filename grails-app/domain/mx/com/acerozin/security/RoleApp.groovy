package mx.com.acerozin.security

import mx.com.acerozin.core.company.Company
import mx.com.acerozin.pogo.security.RoleWrapper
import mx.com.acerozin.presenter.utils.CommonRoutines

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date 15/08/2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved
 *
 */
class RoleApp {

    String name
    String description
    static hasMany = [accessibleViews: AccViewApp, users: UserApp]

    static belongsTo = [Company]

    static constraints = {
        name nullable: false, blank: false
        description nullable: true, blank: true
    }

    static mapping = {
        accessibleViews column: 'acc_view'
    }

    /**
     *
     * @see http://arturoherrero.com/create-your-own-groovy-type-conversion/
     * for further details on asType overriding
     *
     * */

    Object asType(Class clazz) {
        if (clazz == RoleWrapper) {
            try {
                def roleWrapper = CommonRoutines.copyProperties(this, clazz)
                return roleWrapper
            }
            catch (InstantiationException | IllegalAccessException ex) {
                log.error ex.message
            }
        } else {
            super.asType(clazz)
        }
    }

}
