package mx.com.acerozin.presenter

import com.google.common.eventbus.Subscribe
import com.vaadin.annotations.PreserveOnRefresh
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.grails.Grails
import com.vaadin.server.Page
import com.vaadin.server.Page.BrowserWindowResizeEvent
import com.vaadin.server.Page.BrowserWindowResizeListener
import com.vaadin.server.Responsive
import com.vaadin.server.VaadinRequest
import com.vaadin.server.VaadinSession
import com.vaadin.shared.communication.PushMode
import com.vaadin.shared.ui.ui.Transport
import com.vaadin.ui.UI
import com.vaadin.ui.Window
import com.vaadin.ui.themes.ValoTheme
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode
import groovy.util.logging.Log4j
import mx.com.acerozin.inventory.ProductInventoryService
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.datasource.implementation.RoleProviderImpl
import mx.com.acerozin.presenter.datasource.implementation.UserProviderImpl
import mx.com.acerozin.presenter.datasource.interfaces.RoleProvider
import mx.com.acerozin.presenter.datasource.interfaces.UserProvider
import mx.com.acerozin.presenter.event.InventoryEvent.BrowserResizeEvent
import mx.com.acerozin.presenter.event.InventoryEvent.CloseOpenWindowsEvent
import mx.com.acerozin.presenter.event.InventoryEvent.UserLoggedOutEvent
import mx.com.acerozin.presenter.event.InventoryEvent.UserLoginRequestedEvent
import mx.com.acerozin.presenter.event.InventoryEventBus
import mx.com.acerozin.presenter.utils.CommonRoutines
import mx.com.acerozin.security.*
import mx.com.acerozin.view.LoginView
import mx.com.acerozin.view.MainView
import org.apache.log4j.Logger
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Aug 30, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@Theme("dashboard")
@Title("Inventory Manager")
@PreserveOnRefresh
@GrailsCompileStatic
@Log4j
class InventoryManagerUI extends UI {

    final InventoryEventBus inventoryBus = new InventoryEventBus()
    final AuthService authService = Grails.get(AuthService)
    final ProductInventoryService productInventoryService = Grails.get(ProductInventoryService)
    final UserProvider userDataProvider = new UserProviderImpl()
    final RoleProvider roleDataProvider = new RoleProviderImpl()
    InventoryEventBusSubscriber inventoryEventBusSubscriber
    /* (non-Javadoc)
     * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
     */

    @Override
    protected void init(VaadinRequest request) {
        log.info "Starting Inventory Manager UI Application"
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            //Set default locale to session value
            VaadinSession.getCurrent().setAttribute("user-locale", UI.getCurrent().getSession().getLocale() ?: Locale.US)
            this.setLocale((Locale) VaadinSession.getCurrent().getAttribute("user-locale"))
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }

        InventoryEventBus.register(this)
        Responsive.makeResponsive(this)
        addStyleName(ValoTheme.UI_WITH_MENU)

        updateContent(null)

        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(
                new BrowserWindowResizeListener() {
                    @Override
                    void browserWindowResized(
                            final BrowserWindowResizeEvent event) {
                        InventoryEventBus.post(new BrowserResizeEvent())
                    }
                })
        inventoryEventBusSubscriber= new InventoryEventBusSubscriber()
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    private void updateContent(Authentication auth) {
        Collection<GrantedAuthority> authorities = auth?.authorities
        if (auth?.isAuthenticated()) {
            setContent(new MainView())
            removeStyleName("loginview")

            UserWrapper userWrapper = (UserWrapper) VaadinSession.getCurrent().getAttribute(UserWrapper.class)
            getNavigator().navigateTo(userWrapper.defaultView)
        } else {
            setContent(new LoginView())
            addStyleName("loginview")
        }

    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    private void setViewPermissions(UserApp userApp) {
        userApp?.roles?.each { RoleApp role ->
            String roleName = role.name
            role?.accessibleViews?.each { AccViewApp viewItem ->
                def viewClass = InventoryViewType.getByViewName(viewItem.getViewName())
                ViewSecurity.add(viewClass.viewClass, [roleName])
                //ViewSecurity.add(viewClass.viewClass, ['ADMIN'])

            }

        }

    }

    @Subscribe
    void userLoginRequested(final UserLoginRequestedEvent event) {
        Authentication authToken = getDataProvider().login(event.getUserName(), event.getPassword())
        try {
            if (authToken?.isAuthenticated()) {
                UserApp user = getDataProvider().userDetails(event.getUserName(), event.getPassword())

                try {
                    VaadinSession.getCurrent().getLockInstance().lock();
                    UserWrapper userWrapper = user as UserWrapper
                    this.setLocale(userWrapper.getLocale())
                    VaadinSession.getCurrent().setLocale(userWrapper.getLocale())
                    VaadinSession.getCurrent().setAttribute(UserWrapper.class, userWrapper)
                    VaadinSession.getCurrent().setAttribute("user-view-permissions", CommonRoutines.flatViewPermissions(user))
                    VaadinSession.getCurrent().setAttribute("SPRING_SECURITY_CONTEXT", authToken)
                    //SecurityContextHolder.getContext().setAuthentication(authToken)

                    // Now when the session is reinitialized, we can enable websocket communication. Or we could have just
                    // used WEBSOCKET_XHR and skipped this step completely.
                    getPushConfiguration().setTransport(Transport.WEBSOCKET);
                    getPushConfiguration().setPushMode(PushMode.AUTOMATIC);

                    //Set user locale
                    VaadinSession.getCurrent().setAttribute("user-locale", userWrapper?.locale ?: Locale.US)
                } finally {
                    VaadinSession.getCurrent().getLockInstance().unlock();
                }


                setViewPermissions(user)
                updateContent(authToken)
            } else {
                updateContent(null)
            }
        } catch (BadCredentialsException ex) {
            updateContent(null)
        }


    }

    @Subscribe
    void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice this doesn't
        // invalidate the current HttpSession.

        Page.getCurrent().setLocation("/");
        VaadinSession.getCurrent().close()
        Page.getCurrent().reload()

    }

    @Subscribe
    void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close()
        }
    }

    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static AuthService getDataProvider() {
        return ((InventoryManagerUI) getCurrent()).getAuthService()
    }

    public static ProductInventoryService getDataSourceProvider() {
        return ((InventoryManagerUI) getCurrent()).getProductInventoryService()
    }

    public static UserProvider getUserDataSourceProvider() {
        return ((InventoryManagerUI) getCurrent()).getUserDataProvider()
    }

    public static InventoryEventBus getInventoryEventbus() {
        return ((InventoryManagerUI) getCurrent()).getInventoryBus()
    }

    public static RoleProvider getRoleDataSourceProvider() {
        return ((InventoryManagerUI) getCurrent()).getRoleDataProvider()
    }

}
