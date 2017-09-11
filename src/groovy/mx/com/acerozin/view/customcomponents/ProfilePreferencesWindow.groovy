package mx.com.acerozin.view.customcomponents

import com.vaadin.data.Container
import com.vaadin.data.fieldgroup.BeanFieldGroup
import com.vaadin.data.fieldgroup.FieldGroup.CommitException
import com.vaadin.data.fieldgroup.PropertyId
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.event.ShortcutAction.KeyCode
import com.vaadin.server.*
import com.vaadin.shared.Position
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.ui.*
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.Notification.Type
import com.vaadin.ui.themes.ValoTheme
import grails.compiler.GrailsCompileStatic
import mx.com.acerozin.pogo.security.RoleWrapper
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.InventoryManagerUI
import mx.com.acerozin.presenter.event.InventoryEvent.CloseOpenWindowsEvent
import mx.com.acerozin.presenter.event.InventoryEvent.ProfileUpdatedEvent
import mx.com.acerozin.presenter.event.InventoryEventBus

/**
 *
 * @author ArmandodeJesus
 * @email aj.montoya@outlook.com
 * @Date 3/7/2016
 * @Copyright © Armando Montoya, 2015
 * All rights reserved
 *
 */

@SuppressWarnings("serial")
@GrailsCompileStatic
public class ProfilePreferencesWindow extends Window {

    public static final String ID = "profilepreferenceswindow"

    private UserWrapper userWrapper

    private final BeanFieldGroup<UserWrapper> fieldGroup
    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    @PropertyId("person.firstName")
    private TextField firstNameField

    @PropertyId("person.lastName")
    private TextField lastNameField

    @PropertyId("person.title")
    private ComboBox titleField

    @PropertyId("person.male")
    private OptionGroup sexField

    @PropertyId("person.email")
    private TextField emailField

    @PropertyId("person.phone")
    private TextField phoneField

    // user details section
    @PropertyId("username")
    private TextField username

    @PropertyId("password")
    private PasswordField password

    @PropertyId("company.name")
    private TextField company

    private Grid rolesGrid

    private ProfilePreferencesWindow(final UserWrapper user, final boolean preferencesTabOpen) {
        userWrapper = user
        addStyleName("profile-window")
        setId(ID)
        Responsive.makeResponsive(this)

        setModal(true)
        setCloseShortcut(KeyCode.ESCAPE, null)
        setResizable(false)
        setClosable(false)
        setHeight(90.0f, Sizeable.Unit.PERCENTAGE)

        VerticalLayout content = new VerticalLayout()
        content.setSizeFull()
        content.setMargin(new MarginInfo(true, false, false, false))
        setContent(content)

        TabSheet detailsWrapper = new TabSheet()
        detailsWrapper.setSizeFull()
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR)
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP)
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS)
        content.addComponent(detailsWrapper)
        content.setExpandRatio(detailsWrapper, 1f)

        detailsWrapper.addComponent(buildProfileTab())
        detailsWrapper.addComponent(buildUserDetails(user))
        detailsWrapper.addComponent(buildPreferencesTab())

        if (preferencesTabOpen) {
            detailsWrapper.setSelectedTab(1)
        }

        content.addComponent(buildFooter())

        fieldGroup = new BeanFieldGroup<UserWrapper>(UserWrapper.class)
        fieldGroup.bindMemberFields(this)
        fieldGroup.setItemDataSource(user)
    }

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout()
        root.setCaption("Profile")
        root.setIcon(FontAwesome.USER)
        root.setWidth(100.0f, Sizeable.Unit.PERCENTAGE)
        root.setSpacing(true)
        root.setMargin(true)
        root.addStyleName("profile-form")

        VerticalLayout pic = new VerticalLayout()
        pic.setSizeUndefined()
        pic.setSpacing(true)
        Image profilePic = new Image(null, new ThemeResource("img/profile-pic-300px.jpg"))
        profilePic.setWidth(100.0f, Sizeable.Unit.PIXELS)
        pic.addComponent(profilePic)

        Button upload = new Button("Change…", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Not implemented in this demo")
            }
        })
        upload.addStyleName(ValoTheme.BUTTON_TINY)
        pic.addComponent(upload)

        root.addComponent(pic)

        FormLayout details = new FormLayout()
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT)
        root.addComponent(details)
        root.setExpandRatio(details, 1)

        firstNameField = new TextField("First Name")
        details.addComponent(firstNameField)
        lastNameField = new TextField("Last Name")
        details.addComponent(lastNameField)

        titleField = new ComboBox("Title")
        titleField.setInputPrompt("Please specify")
        titleField.addItem("Mr.")
        titleField.addItem("Mrs.")
        titleField.addItem("Ms.")
        titleField.setNewItemsAllowed(true)
        details.addComponent(titleField)

        sexField = new OptionGroup("Sex")
        sexField.addItem(Boolean.FALSE)
        sexField.setItemCaption(Boolean.FALSE, "Female")
        sexField.addItem(Boolean.TRUE)
        sexField.setItemCaption(Boolean.TRUE, "Male")
        sexField.addStyleName("horizontal")
        details.addComponent(sexField)

        Label section = new Label("Contact Info")
        section.addStyleName(ValoTheme.LABEL_H4)
        section.addStyleName(ValoTheme.LABEL_COLORED)
        details.addComponent(section)

        emailField = new TextField("Email")
        emailField.setWidth("100%")
        emailField.setRequired(true)
        emailField.setNullRepresentation("")
        details.addComponent(emailField)

        phoneField = new TextField("Phone")
        phoneField.setWidth("100%")
        phoneField.setNullRepresentation("")
        details.addComponent(phoneField)

        return root
    }


    private Component buildUserDetails(UserWrapper userWrapper) {
        HorizontalLayout root = new HorizontalLayout()
        root.setCaption("User Details")
        root.setIcon(FontAwesome.USER)
        root.setWidth(100.0f, Sizeable.Unit.PERCENTAGE)
        root.setSpacing(true)
        root.setMargin(true)
        root.addStyleName("profile-form")

        FormLayout details = new FormLayout()
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT)
        root.addComponent(details)
        root.setExpandRatio(details, 1)

        Label accountSection = new Label("Account Status")
        accountSection.addStyleName(ValoTheme.LABEL_H4)
        accountSection.addStyleName(ValoTheme.LABEL_COLORED)
        details.addComponent(accountSection)

        company = new TextField("Company")
        company.setReadOnly(true)
        company.setEnabled(false)
        details.addComponent(company)

        username = new TextField("Username")
        details.addComponent(username)

        password = new PasswordField("Password")
        details.addComponent(password)

        Label rolesSection = new Label("Permissions")
        rolesSection.addStyleName(ValoTheme.LABEL_H4)
        rolesSection.addStyleName(ValoTheme.LABEL_COLORED)
        details.addComponent(rolesSection)

        rolesGrid = new Grid(buildRolesDatasource(userWrapper))
        rolesGrid.setSizeFull()
        rolesGrid.setHeight("200px")
        rolesGrid.addStyleName(ValoTheme.TABLE_BORDERLESS)
        rolesGrid.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES)
        rolesGrid.addStyleName(ValoTheme.TABLE_COMPACT)
        rolesGrid.setColumnReorderingAllowed(true)
        rolesGrid.setSelectionMode(Grid.SelectionMode.MULTI)
        rolesGrid.setColumnReorderingAllowed(true)
        rolesGrid.setColumnOrder("name", "description")
        rolesGrid.removeColumn("accessibleViews")
        rolesGrid.removeColumn("metaClass")
        rolesGrid.removeColumn("id")
        rolesGrid.removeColumn("defaultView")
        rolesGrid.removeColumn("users")
        rolesGrid.setSelectionMode(Grid.SelectionMode.NONE)
        //selectUserRolesOnGrid(userWrapper)

        details.addComponent(rolesGrid)

        return root
    }


    private Component buildPreferencesTab() {
        VerticalLayout root = new VerticalLayout()
        root.setCaption("Preferences")
        root.setIcon(FontAwesome.COGS)
        root.setSpacing(true)
        root.setMargin(true)
        root.setSizeFull()

        Label message = new Label("Not implemented in this demo")
        message.setSizeUndefined()
        message.addStyleName(ValoTheme.LABEL_LIGHT)
        root.addComponent(message)
        root.setComponentAlignment(message, Alignment.MIDDLE_CENTER)

        return root
    }

    private BeanItemContainer<RoleWrapper> buildRolesDatasource(UserWrapper userWrapper) {
        return new BeanItemContainer<RoleWrapper>(RoleWrapper, userWrapper?.roles)
    }

    /*
    private void selectUserRolesOnGrid(UserWrapper userWrapper) {

        Container.Indexed gridContainerDataSource = rolesGrid.containerDataSource
        String[] userRoles = userWrapper?.roles*.name

        gridContainerDataSource?.getItemIds().each {
            RoleWrapper role = (RoleWrapper) it
            if (role.name in userRoles) {
                rolesGrid.select(it)
            }

        }

    }*/

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout()
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR)
        footer.setWidth(100.0f, Sizeable.Unit.PERCENTAGE)

        Button ok = new Button("OK")
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY)
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    fieldGroup.commit()
                    // Updated user should also be persisted to database.

                    userWrapper.person.firstName = firstNameField.getValue()
                    userWrapper.person.lastName = lastNameField.getValue()
                    userWrapper.person.title = titleField.getValue().toString()
                    userWrapper.person.male = sexField.getValue()
                    userWrapper.person.email = emailField.getValue()
                    userWrapper.person.phone = phoneField.getValue()
                    userWrapper.password = password.getValue()

                    Notification success = new Notification("Profile updated successfully")
                    success.setDelayMsec(2000)
                    success.setStyleName("bar success small")
                    success.setPosition(Position.BOTTOM_CENTER)
                    success.show(Page.getCurrent())

                    InventoryEventBus.post(new ProfileUpdatedEvent(userWrapper))
                    close()
                } catch (CommitException e) {
                    Notification.show("Error while updating profile",Type.ERROR_MESSAGE)
                }

            }
        })
        ok.focus()
        footer.addComponent(ok)
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT)
        return footer
    }

    public static void open(final UserWrapper user, final boolean preferencesTabActive) {
        InventoryEventBus.post(new CloseOpenWindowsEvent())
        Window w = new ProfilePreferencesWindow(user, preferencesTabActive)
        UI.getCurrent().addWindow(w)
        w.focus()
    }
}
