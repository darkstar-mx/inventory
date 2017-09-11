package mx.com.acerozin.presenter

import com.vaadin.navigator.Navigator
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewProvider
import com.vaadin.navigator.Navigator.ClassBasedViewProvider
import com.vaadin.ui.UI
import com.vaadin.server.VaadinSession
import com.vaadin.ui.ComponentContainer
import grails.compiler.GrailsCompileStatic
import mx.com.acerozin.security.SecuredViewChangeListener

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Aug 30, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@GrailsCompileStatic
class InventoryNavigator extends Navigator {

    final SecuredViewChangeListener securedViewChangeListener = new SecuredViewChangeListener()
    static final InventoryViewType ERROR_VIEW = InventoryViewType.ERROR
    ViewProvider errorViewProvider

    InventoryNavigator(final ComponentContainer container) {
        super(UI.getCurrent(), container)
        addViewChangeListener(securedViewChangeListener)
        initViewProviders()
    }

    private def initViewProviders() {
        List<InventoryViewType> flattenViewPermissions = (List<InventoryViewType>) VaadinSession.getCurrent().getAttribute("user-view-permissions")
        // A dedicated view provider is added for each separate view type
        for (final InventoryViewType viewType : InventoryViewType.values()) {

            //Create only allowed views, the rest will be omitted
            if (viewType.viewName in flattenViewPermissions*.viewName) {

                ViewProvider viewProvider = new ClassBasedViewProvider(viewType.getViewName(), viewType.getViewClass()) {

                    // This field caches an already initialized view instance if the
                    // view should be cached (stateful views).
                    private View cachedInstance

                    @Override
                    public View getView(final String viewName) {
                        View result = null
                        if (viewType.getViewName().equals(viewName)) {
                            if (viewType.isStateful()) {
                                // Stateful views get lazily instantiated
                                if (cachedInstance == null) {
                                    cachedInstance = super.getView(viewType.getViewName())
                                }
                                result = cachedInstance
                            } else {
                                // Non-stateful views get instantiated every time
                                // they're navigated to
                                result = super.getView(viewType.getViewName())
                            }
                        }
                        return result
                    }
                }


                if (viewType == ERROR_VIEW) {
                    errorViewProvider = viewProvider
                }
                addView(viewProvider.getViewName(), viewProvider.getViewClass())

            } else {
                continue
            }

        }

        setErrorProvider(new ViewProvider() {
            @Override
            public String getViewName(final String viewAndParameters) {
                return ERROR_VIEW.getViewName()
            }

            @Override
            public View getView(final String viewName) {
                return errorViewProvider.getView(ERROR_VIEW.getViewName())
            }
        })
    }

}
