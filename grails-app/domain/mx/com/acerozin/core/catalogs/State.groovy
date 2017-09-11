package mx.com.acerozin.core.catalogs

class State {

    String name
    String shortName

    static hasMany = [municipalities: Municipality]

    static belongsTo = [Country]

    static constraints = {
    }
}
