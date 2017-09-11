package mx.com.acerozin.presenter.datasource.interfaces

import com.vaadin.data.util.BeanItemContainer
import grails.compiler.GrailsCompileStatic
import mx.com.acerozin.pogo.security.RoleWrapper

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
interface RoleProvider {

    BeanItemContainer<RoleWrapper> getAllRolesByCompanyId(Long companyId)

}
