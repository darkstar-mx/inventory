package mx.com.acerozin.core.utilities

import grails.transaction.Transactional
import mx.com.acerozin.core.catalogs.Country

@Transactional
class LoadCitiesService {

    /**
     * Database accepted format is:
     * Country,	City, AccentCity, Region, Population, Latitude,	Longitude
     * source https://www.maxmind.com/es/free-world-cities-database
     *
     * */
    def doLoad(String theInfoName) {
        Country country
        File theInfoFile = new File(theInfoName)

        if (!theInfoFile.exists()) {
            println "File does not exist"

        } else {

            theInfoFile.eachLine { line ->

                if (line.trim().size() == 0) {
                    return null

                } else {

                    def words = line.split(",")
                    country.coutryCode = words[0].trim()
                    country.save(false)
                }

            }

        }

    }

    def processFile() {

    }

    def readLine() {

    }
}
