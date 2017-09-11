package mx.com.acerozin.security

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import grails.compiler.GrailsCompileStatic
import mx.com.acerozin.presenter.InventoryViewType
import mx.com.acerozin.presenter.event.InventoryEvent.BrowserResizeEvent
import mx.com.acerozin.presenter.event.InventoryEvent.CloseOpenWindowsEvent
import mx.com.acerozin.presenter.event.InventoryEvent.PostViewChangeEvent
import mx.com.acerozin.presenter.event.InventoryEventBus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date 15/08/2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@GrailsCompileStatic
class SecuredViewChangeListener implements ViewChangeListener {

    /* (non-Javadoc)
     * @see com.vaadin.navigator.ViewChangeListener#beforeViewChange(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
     */

    @Override
    public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication){
            return false
        }

        View view = event.newView
        boolean isViewAccessible = ViewSecurity.isViewAccessible(view)
        //println "beforeViewChange:: checking permissions: " + isViewAccessible
        return isViewAccessible && authentication?.isAuthenticated()
        //return true
    }

    /* (non-Javadoc)
     * @see com.vaadin.navigator.ViewChangeListener#afterViewChange(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
     */

    @Override
    public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {

        InventoryViewType view = InventoryViewType.getByViewName(event.getViewName());
        // Appropriate events get fired after the view is changed.
        InventoryEventBus.post(new PostViewChangeEvent(view));
        InventoryEventBus.post(new BrowserResizeEvent());
        InventoryEventBus.post(new CloseOpenWindowsEvent());

        /*
        if (tracker != null) {
            // The view change is submitted as a pageview for GA tracker
            tracker.trackPageview("/dashboard/" + event.getViewName());
        }*/
    }

}
