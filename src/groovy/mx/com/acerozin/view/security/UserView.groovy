package mx.com.acerozin.view.security

import com.google.common.eventbus.Subscribe
import com.vaadin.data.Item
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.server.Page
import com.vaadin.server.Responsive
import com.vaadin.server.VaadinSession
import com.vaadin.ui.*
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.Grid.SelectionMode
import com.vaadin.ui.themes.ValoTheme
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode
import groovy.util.logging.Log4j
import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.InventoryManagerUI
import mx.com.acerozin.presenter.event.InventoryEvent.BrowserResizeEvent
import mx.com.acerozin.presenter.event.InventoryEventBus

import static com.vaadin.grails.Grails.i18n

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Nov 6, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved
 *
 */
@GrailsCompileStatic
@Log4j
class UserView extends VerticalLayout implements View {

    private Grid grid
    private Locale locale
    private Button createUser

    private static final List<String> ALWAYS_VISIBLE_FIELDS     = ["username", "isAccountEnabled"]
    private static final List<String> ALLOWED_DEFAULT_FIELDS    = ["username", "isAccountEnabled", "person.firstName", "person.lastName"]
    private List<String> defaultHideableFields                  = []

    /* (non-Javadoc)
     * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
     */

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

    UserView() {

        locale = VaadinSession.getCurrent().getAttribute(UserWrapper.class).locale ?: Locale.US
        grid = buildTable()
        setSizeFull()
        addStyleName("transactions")
        addComponent(buildToolbar())

        addComponent(grid)
        setExpandRatio(grid, 1)

        //by default all of the columns are hidable
        loadNonHideableProperties()

        InventoryEventBus.register(this)
        Responsive.makeResponsive(this)
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    private loadNonHideableProperties() {
        defaultHideableFields = (grid.getColumns()*.propertyId) - ALWAYS_VISIBLE_FIELDS
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout()
        header.addStyleName("viewheader")
        header.setSpacing(true)
        Responsive.makeResponsive(header)

        Label title = new Label(i18n("mx.com.acerozin.view.security.userview.title", locale))
        title.setSizeUndefined()
        title.addStyleName(ValoTheme.LABEL_H1)
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN)
        header.addComponent(title)

        createUser = buildCreateUser();

        return header
    }

    @Subscribe
    public void browserResized(final BrowserResizeEvent event) {
        // Some columns are collapsed when browser window width gets small
        // enough to make the table fit better.

        def isMinimizeResizeRequired = Page.getCurrent().getBrowserWindowWidth() < 800

        for (String propertyId : defaultHideableFields) {
            grid.getColumn(propertyId).setHidden(isMinimizeResizeRequired)
        }


    }

    private boolean filterByProperty(final String prop, final Item item, final String text) {
        if (item == null || item.getItemProperty(prop) == null
                || item.getItemProperty(prop).getValue() == null) {
            return false
        }
        String val = item.getItemProperty(prop).getValue().toString().trim()
                .toLowerCase()
        if (val.contains(text.toLowerCase().trim())) {
            return true
        }
        return false
    }

    void createNewReportFromSelection() {
        //UI.getCurrent().getNavigator().navigateTo(InventoryViewType.REPORTS.getViewName())
        //InventoryEventBus.post(new TransactionReportEvent((Collection<Transaction>) table.getValue()))
    }

    @Override
    public void detach() {
        super.detach()
        // A new instance of TransactionsView is created every time it's
        // navigated to so we'll need to clean up references to it on detach.
        InventoryEventBus.unregister(this)
    }


    private Grid buildTable() {
        grid = new Grid(buildDatasource()) {

        }
        grid.setSizeFull()
        //grid.setContainerDataSource(buildDatasource())

        grid.addStyleName(ValoTheme.TABLE_BORDERLESS)
        grid.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES)
        grid.addStyleName(ValoTheme.TABLE_COMPACT)
        grid.setColumnReorderingAllowed(true)
        grid.setSelectionMode(SelectionMode.SINGLE)
        grid.setColumnReorderingAllowed(true)

        //grid.setColumnOrder(ALLOWED_DEFAULT_FIELDS)
        grid.setColumnOrder("username", "isAccountEnabled", "person.firstName", "person.lastName")

        //set column headers according to user locale
        grid.getColumns().each {

            if (it.propertyId in ALLOWED_DEFAULT_FIELDS) {
                it.setHeaderCaption(i18n("mx.com.acerozin.view.security.userview." + it.propertyId, locale))
            } else {
                grid.removeColumn(it.propertyId)
            }

        }

        return grid
    }

    private Button buildCreateUser() {
        final Button createReport = new Button("Create User");
        createReport
                .setDescription("Create a new user for current company");
        createReport.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                createNewReportFromSelection();
            }
        });
        createReport.setEnabled(false);
        return createReport;
    }

    BeanItemContainer<UserWrapper> buildDatasource() {
        BeanItemContainer<UserWrapper> users = InventoryManagerUI.getUserDataSourceProvider().collectAllUsersBic()
        log.info "Building users datasource"
        return users
    }

}
