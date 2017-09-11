package mx.com.acerozin.persondetails

import mx.com.acerozin.core.catalogs.Address
import mx.com.acerozin.pogo.persondetails.PersonWrapper
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
class Person {

    String firstName
    String lastName
    String title
    Boolean male
    String email
    String location
    String phone
    Boolean isCompany
    //String rfc

    static hasMany = [addresses: Address]

    static constraints = {
        firstName blank: false, nullable: false
        lastName blank: true, nullable: true
        title blank: true, nullable: true
        email blank: true, nullable: true
        location blank: true, nullable: true
        phone blank: true, nullable: true
        isCompany blank: false, nullable: false
    }

    Object asType(Class clazz) {
        if (clazz == PersonWrapper) {
            def personWrapper = CommonRoutines.copyProperties(this, clazz)
            return personWrapper
        } else {
            super.asType(clazz)
        }
    }


}
