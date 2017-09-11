package mx.com.acerozin.core.catalogs

class Municipality {

    String name
    String shortName

    static hasMany = [colonies: Colony]

    static belongsTo = [State]

    static constraints = {
    }
}
