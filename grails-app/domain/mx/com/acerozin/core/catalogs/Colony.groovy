package mx.com.acerozin.core.catalogs

class Colony {

    String name
    String zipCode

    static hasMany = [addresses: Address]

    static belongsTo = [Municipality]

    static constraints = {
    }
}
