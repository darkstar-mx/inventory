package mx.com.acerozin.presenter

import com.vaadin.navigator.View
import com.vaadin.server.FontAwesome
import com.vaadin.server.Resource
import mx.com.acerozin.view.catalogs.LocationCatalogsView
import mx.com.acerozin.view.company.CompanyView
import mx.com.acerozin.view.error.ErrorView
import mx.com.acerozin.view.security.UserView

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Aug 30, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
enum InventoryViewType {
    USER("user", "mx.com.acerozin.view.menuselector.user", UserView.class, FontAwesome.USERS, true)
    , COMPANY("company", "mx.com.acerozin.view.menuselector.company", CompanyView.class, FontAwesome.BUILDING, false)
    , LOCATION_CAT("locationcat", "mx.com.acerozin.view.menuselector.locationcat", LocationCatalogsView.class, FontAwesome.GEAR, false)
    , ERROR("error", "mx.com.acerozin.view.menuselector.error", ErrorView.class, FontAwesome.BAN, false)


    private final String viewName
    private final String localeEntry
    private final Class<? extends View> viewClass
    private final Resource icon
    private final boolean stateful

    private InventoryViewType(final String viewName,
                              final String viewLocaleEntry,
                              final Class<? extends View> viewClass,
                              final Resource icon,
                              final boolean stateful) {
        this.viewName = viewName
        this.localeEntry = viewLocaleEntry
        this.viewClass = viewClass
        this.icon = icon
        this.stateful = stateful
    }

    public boolean isStateful() {
        return stateful
    }

    public String getViewName() {
        return viewName
    }

    public String getLocaleEntry() {
        return localeEntry
    }

    public Class<? extends View> getViewClass() {
        return viewClass
    }

    public Resource getIcon() {
        return icon
    }

    public static InventoryViewType getByViewName(final String viewName) {
        InventoryViewType result = null
        for (InventoryViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType
                break
            }
        }
        return result
    }

}
