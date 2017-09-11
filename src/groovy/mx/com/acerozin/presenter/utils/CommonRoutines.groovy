package mx.com.acerozin.presenter.utils

import mx.com.acerozin.core.catalogs.Address
import mx.com.acerozin.core.company.Company
import mx.com.acerozin.persondetails.Person
import mx.com.acerozin.pogo.core.company.CompanyWrapper
import mx.com.acerozin.pogo.core.utils.AddressWrapper
import mx.com.acerozin.pogo.persondetails.PersonWrapper
import mx.com.acerozin.pogo.security.AccViewWrapper
import mx.com.acerozin.pogo.security.RoleWrapper
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.InventoryViewType
import mx.com.acerozin.security.AccViewApp
import mx.com.acerozin.security.RoleApp
import mx.com.acerozin.security.UserApp

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Jan 14, 2016
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
class CommonRoutines {

    static List<InventoryViewType> flatViewPermissions(UserApp userDetails) {
        def allowedViews = []
        for (role in userDetails.roles) {
            for (view in role.accessibleViews) {
                allowedViews << InventoryViewType.getByViewName(view.viewName)
            }
        }
        allowedViews << InventoryViewType.ERROR
        return allowedViews.unique()
    }

    static def copyProperties(def source, def clazz) throws InstantiationException, IllegalAccessException {

        def loadedClass = loadClass(clazz.name)
        switch (clazz) {

            case UserWrapper:

                source.properties.each { key, value ->
                    if (loadedClass.hasProperty(key) && !(key in ['class', 'metaClass'])) {
                        switch (key) {
                            case 'roles':
                                def changedRoles = []
                                for (item in value) {
                                    changedRoles << item.asType(RoleWrapper)
                                }
                                loadedClass[key] = changedRoles
                                break
                            case 'person':
                                //loadedClass[key] = value.asType(PersonWrapper)
                                break
                            case 'company':
                                //loadedClass[key] = value.asType(CompanyWrapper)
                                break
                            case 'password':
                                loadedClass[key] = ""
                                break
                            default:
                                loadedClass[key] = value
                                break
                        }

                    }

                }
                break

            case RoleWrapper:
                source.properties.each { key, value ->
                    if (loadedClass.hasProperty(key) && !(key in ['class', 'metaClass'])) {
                        switch (key) {
                            case 'accessibleViews':
                                def changedViews = []
                                for (viewItem in value) {
                                    changedViews << viewItem.asType(AccViewWrapper)
                                }
                                loadedClass[key] = changedViews
                                break
                            case 'users':
                                def changedUsers = []
                                for (userItem in value) {
                                    changedUsers << userItem.asType(UserWrapper)
                                }
                                loadedClass[key] = changedUsers
                                break
                            case 'id':
                                loadedClass[key] = value
                                break
                            default:
                                loadedClass[key] = value
                        }
                    }
                }
                break

            case CompanyWrapper:
                source.properties.each { key, value ->
                    if (loadedClass.hasProperty(key) && !(key in ['class', 'metaClass'])) {
                        switch (key) {
                            case 'address':
                                loadedClass[key] = value.asType(AddressWrapper)
                                break
                            case 'warehouses':
                                def changedWarehouses = []
                                for (changedItem in value) {
                                    changedWarehouses << changedItem.asType(WarehouseWrapper)
                                }
                                loadedClass[key] = changedWarehouses
                                break
                            case 'roles':
                                def changedItems = []
                                for (item in value) {
                                    changedItems << item.asType(RoleWrapper)
                                }
                                loadedClass[key] = changedItems
                                break
                            default:
                                //println "key found: " + key
                                loadedClass[key] = value
                        }
                        /**
                         * FIXME: The GORM for MongoDB doesn't return the Id property, so a manual loading is required
                         * */
                        loadedClass.id = source.id
                    }
                }
                break
            ///////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////// Domain classes wrapping begins //////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////
            case UserApp:
                source.properties.each { key, value ->
                    if (loadedClass.hasProperty(key) && !(key in ['class', 'metaClass'])) {
                        switch (key) {
                            case 'roles':
                                def changedRoles = []
                                for (item in value) {
                                    changedRoles << item.asType(RoleApp)
                                }
                                loadedClass[key] = changedRoles
                                break
                            case 'person':
                                loadedClass[key] = value.asType(Person)
                                break
                            case 'company':
                                loadedClass[key] = value.asType(Company)
                                break
                            default:
                                loadedClass[key] = value
                                break
                        }

                    }

                }
                break

            case RoleApp:
                source.properties.each { key, value ->
                    if (loadedClass.hasProperty(key) && !(key in ['class', 'metaClass'])) {
                        switch (key) {
                            case 'accessibleViews':
                                def changedViews = []
                                for (viewItem in value) {
                                    changedViews << viewItem.asType(AccViewApp)
                                }
                                loadedClass[key] = changedViews
                                break
                            case 'users':
                                def changedUsers = []
                                for (userItem in value) {
                                    changedUsers << userItem.asType(UserApp)
                                }
                                loadedClass[key] = changedUsers
                                break
                            case 'id':
                                loadedClass[key] = value
                                break
                            default:
                                loadedClass[key] = value
                        }
                    }
                }
                break

            case Company:
                source.properties.each { key, value ->
                    if (loadedClass.hasProperty(key) && !(key in ['class', 'metaClass'])) {
                        switch (key) {
                            case 'address':
                                loadedClass[key] = value.asType(Address)
                                break
                            case 'warehouses':
                                def changedWarehouses = []
                                for (changedItem in value) {
                                    changedWarehouses << changedItem.asType(Warehouse)
                                }
                                loadedClass[key] = changedWarehouses
                                break
                            case 'roles':
                                def changedItems = []
                                for (item in value) {
                                    changedItems << item.asType(RoleApp)
                                }
                                loadedClass[key] = changedItems
                                break
                            default:
                                //println "key found: " + key
                                loadedClass[key] = value
                        }
                        /**
                         * FIXME: The GORM for MongoDB doesn't return the Id property, so a manual loading is required
                         * */
                        loadedClass.id = source.id
                    }
                }
                break

            default:
                source.properties.each { key, value ->
                    if (loadedClass.hasProperty(key) && !(key in ['class', 'metaClass'])) {
                        loadedClass[key] = value
                    }
                }
                break
        }

        return loadedClass

    }

    static def loadClass(String className) throws InstantiationException, IllegalAccessException {
        Class.forName(className).newInstance()
    }

}
