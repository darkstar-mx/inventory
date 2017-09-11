package mx.com.acerozin.presenter.datasource.interfaces

import mx.com.acerozin.security.UserApp

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Nov 7, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
interface CompanyProvider {

    Collection<UserApp> getAllUsersList(Integer companyId)

}
