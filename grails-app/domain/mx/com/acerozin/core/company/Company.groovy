package mx.com.acerozin.core.company

import mx.com.acerozin.core.catalogs.Address
import mx.com.acerozin.pogo.core.company.CompanyWrapper
import mx.com.acerozin.presenter.utils.CommonRoutines
import mx.com.acerozin.security.RoleApp

class Company implements Serializable {

    private static final long serialVersionUID = 2

    String name
    String rfc
    String responsible
    Address address

    static hasMany = [roles: RoleApp]

    static constraints = {
    }

    /*
    String toString(){
        return name
    }*/

    Object asType(Class clazz) {
        if (clazz == CompanyWrapper) {
            def companyWrapper = CommonRoutines.copyProperties(this, clazz)
            return companyWrapper
        } else {
            super.asType(clazz)
        }
    }

}
