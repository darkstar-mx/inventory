package mx.com.acerozin.presenter.datasource.interfaces

import mx.com.acerozin.pogo.security.UserWrapper

/**
 *
 * @author ArmandodeJesus
 * @email aj.montoya@outlook.com
 * @Date 3/25/2016
 * @Copyright © Armando Montoya, 2015
 * All rights reserved
 *
 */
interface SecurityConfigurationProvider {

    Collection<UserWrapper> getAllSubscribersByCompanyId(Integer companyId)

    Collection<UserWrapper> getUsersByCompanyAndRole(Integer companyId, Integer roleId)

    UserWrapper getUserByCompanyAndUsername(Integer companyId, String username)

    UserWrapper getUserByCompanyAndUserId(Integer companyId, Integer userId)

}
