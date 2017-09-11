package mx.com.acerozin.presenter.datasource.implementation

import com.vaadin.grails.Grails
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.datasource.interfaces.SecurityConfigurationProvider
import mx.com.acerozin.security.UserAppService

/**
 *
 * @author ArmandodeJesus
 * @email aj.montoya@outlook.com
 * @Date 3/25/2016
 * @Copyright © Armando Montoya, 2015
 * All rights reserved
 *
 */
class SecurityConfigurationProviderImpl implements SecurityConfigurationProvider {

    private final UserAppService userAppService = Grails.get(UserAppService)

    @Override
    Collection<UserWrapper> getAllSubscribersByCompanyId(Integer companyId) {

    }

    @Override
    Collection<UserWrapper> getUsersByCompanyAndRole(Integer companyId, Integer roleId) {
        return null
    }

    @Override
    UserWrapper getUserByCompanyAndUsername(Integer companyId, String username) {
        return null
    }

    @Override
    UserWrapper getUserByCompanyAndUserId(Integer companyId, Integer userId) {
        return null
    }
}
