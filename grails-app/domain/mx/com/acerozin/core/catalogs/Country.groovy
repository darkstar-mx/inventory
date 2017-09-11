package mx.com.acerozin.core.catalogs

class Country {

    String name
    String countryCode

    static hasMany = [states: State]

    static constraints = {
    }
}
