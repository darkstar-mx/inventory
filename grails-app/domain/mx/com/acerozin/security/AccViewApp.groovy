package mx.com.acerozin.security

import mx.com.acerozin.pogo.security.AccViewWrapper
import mx.com.acerozin.presenter.utils.CommonRoutines

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Oct 28, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
class AccViewApp {

    String name
    String description
    String viewName

    static belongsTo = [RoleApp]

    static constraints = {
        name nullable: false, blank: false
        description nullable: true, blank: true
        viewName nullable: false, blank: false
    }

    static mapping = {
        table 'views'
        //accessible_view_app_id column: 'view_id'
        //id generator: 'view_id'
    }


    Object asType(Class clazz) {
        if (clazz == AccViewWrapper) {
            try {
                def accViewWrapper = CommonRoutines.copyProperties(this, clazz)
                return accViewWrapper
            }
            catch (InstantiationException | IllegalAccessException ex) {
                log.error ex.message
            }
        } else {
            super.asType(clazz)
        }
    }


}
