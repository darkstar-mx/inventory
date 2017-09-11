package mx.com.acerozin.core.catalogs

import mx.com.acerozin.persondetails.Person
import mx.com.acerozin.pogo.core.utils.AddressWrapper
import mx.com.acerozin.presenter.utils.CommonRoutines

class Address {

    String street
    String externalNumber
    String internalNumber

    //static hasMany = [addresses:Address]

    static belongsTo = [Colony, Person]

    static constraints = {
        street nullable: false, blank: false
        externalNumber nullable: false, blank: false
        internalNumber nullable: true, blank: true
    }

    Object asType(Class clazz) {
        if (clazz == AddressWrapper) {
            try {
                def addressWrapper = CommonRoutines.copyProperties(this, clazz)
                return addressWrapper
            }
            catch (InstantiationException | IllegalAccessException ex) {
                log.error ex.message
            }

        } else {
            super.asType(clazz)
        }
    }
}
