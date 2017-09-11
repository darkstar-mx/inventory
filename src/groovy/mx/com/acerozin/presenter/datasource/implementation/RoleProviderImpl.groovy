package mx.com.acerozin.presenter.datasource.implementation

import com.vaadin.data.util.BeanItemContainer
import com.vaadin.grails.Grails
import grails.compiler.GrailsCompileStatic
import mx.com.acerozin.pogo.security.RoleWrapper
import mx.com.acerozin.presenter.datasource.interfaces.RoleProvider
import mx.com.acerozin.security.RoleService

/**
 *
 * @author ArmandodeJesus
 * @email aj.montoya@outlook.com
 * @Date 3/16/2016
 * @Copyright © Armando Montoya, 2015
 * All rights reserved
 *
 */
@GrailsCompileStatic
class RoleProviderImpl implements RoleProvider {

    private final RoleService roleService = Grails.get(RoleService)

    @Override
    BeanItemContainer<RoleWrapper> getAllRolesByCompanyId(Long companyId) {
        Collection<RoleWrapper> result = roleService.getAllRolesByCompanyId(companyId)
        BeanItemContainer<RoleWrapper> bic = new BeanItemContainer<RoleWrapper>(RoleWrapper, result)
        return bic
    }

}
