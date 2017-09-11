package mx.com.acerozin.presenter.datasource.interfaces

import com.vaadin.client.data.DataSource
import com.vaadin.data.util.BeanItemContainer
import mx.com.acerozin.pogo.security.UserWrapper

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Nov 7, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
interface UserProvider {

    /**
     * @return A Collection of Users.
     */

    DataSource<UserWrapper> getAllUsersList()


    Collection<UserWrapper> collectAllUsersList()

    BeanItemContainer<UserWrapper> collectAllUsersBic()

    Boolean updateUser(UserWrapper userDetails)
}
