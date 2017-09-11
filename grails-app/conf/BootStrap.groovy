/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Aug 28, 2015
 * @Copyright © Armando Montoya 2015
 * All rights reserved
 *
 */
import grails.util.Environment
import mx.com.acerozin.core.catalogs.*
import mx.com.acerozin.core.company.Company
import mx.com.acerozin.persondetails.Person
import mx.com.acerozin.presenter.InventoryViewType
import mx.com.acerozin.security.AccViewApp
import mx.com.acerozin.security.RoleApp
import mx.com.acerozin.security.UserApp

class BootStrap {

    def bcryptService

    def init = { servletContext ->
        if (Environment.current == Environment.DEVELOPMENT) {

            if (UserApp.count <= 0) {

                //Create some country information
                Country country = new Country(name: "Mexico", countryCode: "mx", states: [])
                State state = new State(name: "Queretaro", shortName: "Qro", municipalities: [])
                Municipality municipality = new Municipality(name: "Santiago de Queretaro", shortName: "Qro", colonies: [])
                Colony colony = new Colony(name: "Some Colony", zipCode: "76000", addresses: [])
                Address address = new Address(street: "Some street", externalNumber: "100", internalNumber: "")

                colony.addresses << address
                municipality.colonies << colony
                state.municipalities << municipality
                country.states << state

                Colony.withTransaction {
                    colony.save(failOnError: true)
                    municipality.save(failOnError: true)
                    state.save(failOnError: true)
                    country.save(failOnError: true)
                }

                /////////////////////////////////////////////////////

                //Create some view-userRoles information
                //AccViewApp accGridViewAdminApp = new AccViewApp(name:"user-view", description:"UserGrid View with secured content for User role", viewName:InventoryViewType.USERGRID.viewName)
                AccViewApp accUserViewUserApp = new AccViewApp(name: "user-view", description: "User View with secured content for User role", viewName: InventoryViewType.USER.viewName)
                AccViewApp accLocationCatViewUserApp = new AccViewApp(name: "locationcat-view", description: "View to admin Locations catalogs with secured content for Admin role", viewName: InventoryViewType.LOCATION_CAT.viewName)

                RoleApp adminRole = new RoleApp(name: 'ADMIN', description: 'Administrators role', accessibleViews: [])
                //adminRole.accessibleViews << accViewAdminApp
                //adminRole.accessibleViews 		<< accGridViewAdminApp
                adminRole.accessibleViews << accUserViewUserApp
                adminRole.accessibleViews << accLocationCatViewUserApp
                adminRole.save(failOnError: true)

                ///////////////////////////////////////////////////
                Company company = new Company(name: "Facebook Inc.", rfc: "some", responsible: "Marck Zuckerberg", address: address, roles: [])
                company.roles << adminRole
                company.save(failOnError: true)

                ///////////////////////////////////////////////////

                UserApp user = new UserApp()
                user.username = "john"
                user.password = bcryptService.hashPassword("john")
                user.isAccountEnabled = true
                user.nonExpiredAccount = true
                user.nonLockedAccount = true
                user.nonExpiredCredentials = true
                user.forceLocalLogin = true
                //user.locale 				= new Locale("en")
                user.locale = new Locale("es", "ES")
                user.company = company
                user.defaultView = InventoryViewType.USER.viewName

                Person person = new Person()
                person.firstName = "John"
                person.lastName = "Wayne"
                person.title = "Mr."
                person.male = true
                person.email = "john.wayne@facebook.com"
                person.location = "U.S.A."
                person.phone = "555-125-2365"
                person.isCompany = false
                person.save(failOnError: true)

                user.person = person
                user.roles = []
                user.roles << adminRole
                //user.userRoles				<< user1Role
                /**
                 *
                 * Due to error (don't flush the Session after an exception occurs) reported here, it's required to save user this way always.
                 *  http://stackoverflow.com/questions/20993081/grails-null-id-in-com-easytha-student-entry-dont-flush-the-session-after-an-ex
                 *
                 */
                UserApp.withTransaction {
                    user.save(failOnError: true)
                }

                /*
            UserRolesApp userRolesApp = new UserRolesApp(role:adminRole,user:user)
            userRolesApp.save(failOnError: true)
            */
            }
        }
    }

    def destroy = {
    }
}