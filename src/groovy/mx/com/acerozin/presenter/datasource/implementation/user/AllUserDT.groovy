package mx.com.acerozin.presenter.datasource.implementation.user

import com.vaadin.client.data.DataChangeHandler
import com.vaadin.client.data.DataSource
import com.vaadin.grails.Grails
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.security.UserAppService

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Nov 10, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
class AllUserDT implements DataSource<UserWrapper> {

    private final Integer ROWS_MAX = 200;
    private Integer size = ROWS_MAX;

    private static Date lastDataUpdate
    private final UserAppService userAppService = Grails.get(UserAppService)
    private DataChangeHandler dataChangeHandler;
    private List<UserWrapper> users = userAppService.getAllUsers()

    AllUserDT() {
        //users 	= userAppService.getAllUsers()
        size = users.size()
    }

    /* (non-Javadoc)
     * @see com.vaadin.client.data.DataSource#ensureAvailability(int, int)
     */

    @Override
    public void ensureAvailability(int firstRowIndex, int numberOfRows) {
        println "AllUserDT:ensureAvailability"
        dataChangeHandler.dataAvailable(firstRowIndex, numberOfRows)

    }

    /* (non-Javadoc)
     * @see com.vaadin.client.data.DataSource#getRow(int)
     */

    @Override
    public UserWrapper getRow(int rowIndex) {
        println "AllUserDT:getRow"
        if (rowIndex < size && rowIndex >= 0) {
            return users[rowIndex]
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.vaadin.client.data.DataSource#size()
     */

    @Override
    public int size() {
        println "AllUserDT:size"
        return size
    }

    /* (non-Javadoc)
     * @see com.vaadin.client.data.DataSource#setDataChangeHandler(com.vaadin.client.data.DataChangeHandler)
     */

    @Override
    public void setDataChangeHandler(DataChangeHandler dataChangeHandler) {
        println "AllUserDT:setDataChangeHandler"
        dataChangeHandler = dataChangeHandler

    }

    /* (non-Javadoc)
     * @see com.vaadin.client.data.DataSource#getHandle(java.lang.Object)
     */

    @Override
    public com.vaadin.client.data.DataSource.RowHandle<UserWrapper> getHandle(UserWrapper row) {
        println "AllUserDT:getHandle"
        return null;
    }

    public void changeSize() {
        size--
        if (size < ROWS_MAX / 2) {
            size = ROWS_MAX
        }
        dataChangeHandle.resetDataAndSize(size);
    }

}
