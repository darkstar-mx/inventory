package mx.com.acerozin.presenter

import com.google.common.eventbus.Subscribe
import grails.compiler.GrailsCompileStatic
import groovy.util.logging.Log4j
import mx.com.acerozin.presenter.event.InventoryEvent
import mx.com.acerozin.presenter.event.InventoryEventBus

/**
 *
 * @author ArmandodeJesus
 * @email aj.montoya@outlook.com
 * @Date 5/10/2016
 * @Copyright © Armando Montoya, 2015
 * All rights reserved
 *
 */
@GrailsCompileStatic
@Log4j
class InventoryEventBusSubscriber {
    InventoryEventBusSubscriber(){
        InventoryEventBus.register(this)
    }

    /**
     *
     *   http://tomaszdziurko.pl/2012/01/google-guava-eventbus-easy-elegant-publisher-subscriber-cases/
    */
    @Subscribe
    void userProfileEventUpdateRequested(final InventoryEvent.ProfileUpdatedEvent event) {
        log.info "userProfileEventUpdateRequested:: inside"
        try {
            Boolean result = InventoryManagerUI.getUserDataSourceProvider().updateUser(event.getUserDetails())
            log.info "Execution result was: " + (result ? "Successful" : "Not successful")
            event.setSuccessful(result)
            if (result) {
                event.setErrorMessage("Success")
            } else {
                event.setErrorMessage("Failure")
            }
        } catch (Exception ex) {
            event.setSuccessful(false)
            event.setErrorMessage(ex.getMessage())
        }
    }

}
