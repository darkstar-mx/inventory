package mx.com.acerozin.pogo.security

import grails.compiler.GrailsCompileStatic
import groovy.util.logging.Log4j
import mx.com.acerozin.presenter.utils.CommonRoutines
import mx.com.acerozin.security.AccViewApp

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Nov 7, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@GrailsCompileStatic
@Log4j
class AccViewWrapper implements Serializable {
    String name
    String description
    String viewName

    Object asType(Class clazz){
        log.info "asType:: Applying conversion to " + clazz.name
        if (clazz == AccViewApp) {
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
