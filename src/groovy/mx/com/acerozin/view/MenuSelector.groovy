package mx.com.acerozin.view

import com.google.common.eventbus.Subscribe
import com.vaadin.event.dd.DragAndDropEvent
import com.vaadin.event.dd.DropHandler
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion
import com.vaadin.server.FontAwesome
import com.vaadin.server.ThemeResource
import com.vaadin.server.VaadinSession
import com.vaadin.shared.ui.label.ContentMode
import com.vaadin.ui.*
import com.vaadin.ui.AbstractSelect.AcceptItem
import com.vaadin.ui.DragAndDropWrapper.DragStartMode
import com.vaadin.ui.MenuBar.Command
import com.vaadin.ui.MenuBar.MenuItem
import com.vaadin.ui.themes.ValoTheme
import mx.com.acerozin.pogo.persondetails.PersonWrapper
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.InventoryManagerUI
import mx.com.acerozin.presenter.InventoryViewType
import mx.com.acerozin.presenter.event.InventoryEvent.PostViewChangeEvent
import mx.com.acerozin.presenter.event.InventoryEvent.ProfileUpdatedEvent
import mx.com.acerozin.presenter.event.InventoryEvent.UserLoggedOutEvent
import mx.com.acerozin.presenter.event.InventoryEventBus
import mx.com.acerozin.view.customcomponents.ProfilePreferencesWindow

import static com.vaadin.grails.Grails.i18n

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date 19/08/2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
class MenuSelector extends CustomComponent {

    public static final String ID = "inventory-menu"
    public static final String REPORTS_BADGE_ID = "inventory-menu-reports-badge"
    public static final String NOTIFICATIONS_BADGE_ID = "inventory-menu-notifications-badge"
    private static final String STYLE_VISIBLE = "valo-menu-visible"
    private Label notificationsBadge
    private Label reportsBadge
    private MenuItem settingsItem
    private Locale locale

    MenuSelector() {
        locale = (Locale) VaadinSession.getCurrent().getAttribute("user-locale")
        setPrimaryStyleName("valo-menu")
        setId(ID)
        setSizeUndefined()
        setCompositionRoot(buildContent())
    }

    def Component buildContent() {
        final CssLayout menuContent = new CssLayout()
        menuContent.addStyleName("sidebar")
        menuContent.addStyleName(ValoTheme.MENU_PART)
        menuContent.addStyleName("no-vertical-drag-hints")
        menuContent.addStyleName("no-horizontal-drag-hints")
        menuContent.setWidth(null)
        menuContent.setHeight("100%")

        menuContent.addComponent(buildTitle())
        menuContent.addComponent(buildUserMenu())
        menuContent.addComponent(buildToggleButton())
        menuContent.addComponent(buildMenuItems())
        //menuContent.addComponent(buildMenuUtilsItems())

        return menuContent
    }

    def Component buildTitle() {
        Label logo = new Label(i18n("mx.com.acerozin.view.menuselector.title", locale), ContentMode.HTML)
        logo.setSizeUndefined()
        HorizontalLayout logoWrapper = new HorizontalLayout(logo)
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER)
        logoWrapper.addStyleName("valo-menu-title")
        return logoWrapper
    }

    def UserWrapper getCurrentUser() {
        return (UserWrapper) VaadinSession.getCurrent().getAttribute(UserWrapper.class)
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar()
        settings.addStyleName("user-menu")
        final UserWrapper user = getCurrentUser()
        settingsItem = settings.addItem("", new ThemeResource("img/profile-pic-300px.jpg"), null)
        updateUserName(null)
        settingsItem.addItem(i18n("mx.com.acerozin.view.menuselector.edit-profile", locale), new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(getCurrentUser(), false)
            }
        })
        settingsItem.addItem(i18n("mx.com.acerozin.view.menuselector.preferences", locale), new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                //ProfilePreferencesWindow.open(user, true)
            }
        })
        settingsItem.addSeparator()
        settingsItem.addItem(i18n("mx.com.acerozin.view.menuselector.signout", locale), new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                InventoryEventBus.post(new UserLoggedOutEvent())
            }
        })
        return settings
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button(i18n("mx.com.acerozin.view.menuselector.menu", locale), new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE)
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE)
                }
            }
        })
        valoMenuToggleButton.setIcon(FontAwesome.LIST)
        valoMenuToggleButton.addStyleName("valo-menu-toggle")
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS)
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL)
        return valoMenuToggleButton
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout()
        menuItemsLayout.addStyleName("valo-menuitems")
        final UserWrapper user = getCurrentUser()
        List<InventoryViewType> flattenViewPermisions = VaadinSession.getCurrent().getAttribute("user-view-permissions")
        flattenViewPermisions << InventoryViewType.ERROR
        for (final InventoryViewType view : InventoryViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view, locale)
            if (view.viewName in flattenViewPermisions*.viewName && !view.viewName.equals(InventoryViewType.ERROR.viewName)) {
                if (view == InventoryViewType.USER) {
                    notificationsBadge = new Label()
                    notificationsBadge.setId(NOTIFICATIONS_BADGE_ID)
                    menuItemComponent = buildBadgeWrapper(menuItemComponent, notificationsBadge)
                }


                menuItemsLayout.addComponent(menuItemComponent)
            }
        }
        return menuItemsLayout

    }

    private Component buildMenuUtilsItems() {

        final MenuBar settings = new MenuBar()
        //settings.addStyleName("user-menu")
        settingsItem.addItem("User View", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                //ProfilePreferencesWindow.open(user, false)
                //InventoryManagerUI.getCurrent().getNavigator().navigateTo('user')
            }
        })
        //return settings

        CssLayout menuItemsLayout = new CssLayout()
        menuItemsLayout.addStyleName("valo-menuitems")

        for (final InventoryViewType view : InventoryViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view, locale)
            menuItemsLayout.addComponent(menuItemComponent)
        }
        return menuItemsLayout

    }

    private Component buildBadgeWrapper(final Component menuItemButton,
                                        final Component badgeLabel) {
        CssLayout badgeWrapper = new CssLayout(menuItemButton)
        badgeWrapper.addStyleName("badgewrapper")
        badgeWrapper.addStyleName(ValoTheme.MENU_ITEM)
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE)
        badgeLabel.setWidthUndefined()
        badgeLabel.setVisible(false)
        badgeWrapper.addComponent(badgeLabel)
        return badgeWrapper
    }

    @Subscribe
    public void updateUserName(final ProfileUpdatedEvent event) {
        UserWrapper user = getCurrentUser()
        PersonWrapper person = user.person
        settingsItem.setText(person?.firstName + " " + person?.lastName)
    }

    public final class ValoMenuItemButton extends Button {

        private static final String STYLE_SELECTED = "selected"

        private final InventoryViewType view

        public ValoMenuItemButton(final InventoryViewType view, final Locale locale) {
            this.view = view
            setPrimaryStyleName("valo-menu-item")
            setIcon(view.getIcon())
            setCaption(i18n(view.getLocaleEntry(), locale))
            InventoryEventBus.register(this)
            addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(final Button.ClickEvent event) {
                    InventoryManagerUI.getCurrent().getNavigator().navigateTo(view.getViewName())
                }
            })

        }

        @Subscribe
        public void postViewChange(final PostViewChangeEvent event) {
            removeStyleName(STYLE_SELECTED)
            if (event.getView() == view) {
                addStyleName(STYLE_SELECTED)
            }
        }
    }

}
