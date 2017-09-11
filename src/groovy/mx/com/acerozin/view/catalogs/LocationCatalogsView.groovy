package mx.com.acerozin.view.catalogs

import com.vaadin.data.util.BeanItemContainer
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.server.Responsive
import com.vaadin.server.VaadinSession
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import grails.compiler.GrailsCompileStatic
import groovy.util.logging.Log4j
import mx.com.acerozin.pogo.core.utils.CountryWrapper

//import mx.com.acerozin.mocks.MockService
import mx.com.acerozin.pogo.security.UserWrapper

import static com.vaadin.grails.Grails.i18n

/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Dec 12, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
@GrailsCompileStatic
@Log4j
class LocationCatalogsView extends VerticalLayout implements View {

    /* (non-Javadoc)
     * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
     */

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        //println("Accessing LocationCatalogsView")

    }

    LocationCatalogsView() {
        locale = VaadinSession.getCurrent().getAttribute(UserWrapper.class).locale ?: Locale.US

        setSizeFull()
        addStyleName("transactions")
        addComponent(buildToolbar())

        //Label lblTitle = new Label("Secured content only")
        //lblTitle.setSizeUndefined()
        //lblTitle.addStyleName(ValoTheme.LABEL_H1)
        //lblTitle.addStyleName(ValoTheme.LABEL_NO_MARGIN)
        //addComponent(lblTitle)
        //setComponentAlignment(lblTitle, Alignment.MIDDLE_LEFT)
        //setComponentAlignment(lblTitle, Alignment.TOP_CENTER)

        //InventoryEventBus.register(this)
        Responsive.makeResponsive(this)
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout()
        header.addStyleName("viewheader")
        header.setSpacing(true)
        Responsive.makeResponsive(header)

        Label title = new Label(i18n("mx.com.acerozin.view.security.locationcatview.title", locale))
        title.setSizeUndefined()
        title.addStyleName(ValoTheme.LABEL_H1)
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN)
        header.addComponent(title)

        return header
    }

    private Component buildTreeTable() {
        TreeTable catalogsContainer = new TreeTable();
        catalogsContainer.setSizeFull();
        catalogsContainer.setSelectable(true);


    }

    BeanItemContainer<CountryWrapper> buildCountryDatasource() {
        //BeanItemContainer<CountryWrapper> countries = InventoryManagerUI.getCountryDataSourceProvider().collectAllCountriesBic()
        //BeanItemContainer<CountryWrapper> countries = MockService.getAllCountries().collectAllCountriesBic()
        log.info "Building users datasource"
        //return users
    }
}
