package mx.com.acerozin

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date 15/08/2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved
 *
 */
class Subscriber {

    String name
    String lastName
    String address
    String city
    Integer zipCode

    static constraints = {
        name null: false, blank: false
        lastName null: false, blank: false
        zipCode min: 0, max: 999999
        city null: false, blank: false
    }
}
