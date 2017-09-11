package mx.com.acerozin.presenter.datasource.implementation

import com.vaadin.client.data.DataSource
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.grails.Grails
import groovy.util.logging.Log4j
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.datasource.implementation.user.AllUserDT
import mx.com.acerozin.presenter.datasource.interfaces.UserProvider
import mx.com.acerozin.security.UserAppService

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Nov 7, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@Log4j
class UserProviderImpl implements UserProvider {

    private final Integer ROWS_MAX = 200;
    private Integer size = ROWS_MAX;

    private static Date lastDataUpdate
    private final UserAppService userAppService = Grails.get(UserAppService)

    UserProviderImpl() {
        lastDataUpdate = new Date()
    }

    /**
     *
     * DS examples:
     * https://github.com/Artur-/griddesign/blob/master/src/main/java/org/vaadin/artur/griddesign/client/data/DataContainer.java
     * https://github.com/vaadin/vaadin/blob/master/uitest/src/com/vaadin/tests/widgetset/client/grid/GridCellFocusOnResetSizeWidget.java
     * https://dev.vaadin.com/review/gitweb?p=vaadin.git;a=blob;f=client/src/com/vaadin/client/ui/grid/datasources/ListDataSource.java
     * http://stackoverflow.com/questions/19521671/vaadin-display-a-list-in-table
     * https://morevaadin.com/content/table-dead-long-live-grid/
     *
     * https://github.com/vaadin/vaadin/blob/master/uitest/src/com/vaadin/tests/widgetset/client/grid/GridClientDataSourcesWidget.java
     *
     * */

    @Override
    public DataSource<UserWrapper> getAllUsersList() {
        //println "UserDataImpl:getAllUsersList()"
        return new AllUserDT<UserWrapper>()
    }

    @Override
    public Collection<UserWrapper> collectAllUsersList() {
        def returnValues = userAppService.getAllUsers()
        println returnValues.dump()
        return returnValues
    }

    @Override
    public BeanItemContainer<UserWrapper> collectAllUsersBic() {
        Collection<UserWrapper> users = userAppService.getAllUsers()
        BeanItemContainer<UserWrapper> bic = new BeanItemContainer<UserWrapper>(UserWrapper, users)
        bic.addNestedContainerProperty("person.firstName")
        bic.addNestedContainerProperty("person.lastName")
        return bic
    }

    @Override
    Boolean updateUser(UserWrapper userDetails){
        return userAppService.updateUser(userDetails)
    }

}
