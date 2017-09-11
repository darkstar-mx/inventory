package mx.com.acerozin.pogo.persondetails

import grails.compiler.GrailsCompileStatic
import groovy.util.logging.Log4j
import mx.com.acerozin.persondetails.Person
import mx.com.acerozin.presenter.utils.CommonRoutines

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Jan 18, 2016
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@GrailsCompileStatic
@Log4j
class PersonWrapper implements Serializable {

    Long id
    String firstName
    String lastName
    String title
    Boolean male
    String email
    String location
    String phone
    Boolean isCompany
    def addresses

    Object asType(Class clazz){
        log.info "asType:: Applying conversion to " + clazz.name
        if (clazz == Person) {
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
