package mx.com.acerozin.pogo.core.company

import grails.compiler.GrailsCompileStatic
import groovy.util.logging.Log4j
import mx.com.acerozin.core.company.Company
import mx.com.acerozin.pogo.core.utils.AddressWrapper
import mx.com.acerozin.pogo.security.RoleWrapper
import mx.com.acerozin.presenter.utils.CommonRoutines

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Jan 19, 2016
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@GrailsCompileStatic
@Log4j
class CompanyWrapper implements Serializable {

    private static final long serialVersionUID = 1000

    Long id
    String name
    String rfc
    String responsible
    AddressWrapper address
    Collection<RoleWrapper> roles

    String toString(){
        return name
    }

    Object asType(Class clazz) {
        log.info "asType:: Applying conversion to " + clazz.name
        if (clazz == Company) {
            return CommonRoutines.copyProperties(this, clazz)
        } else {
            super.asType(clazz)
        }
    }

}
