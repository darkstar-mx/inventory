package mx.com.acerozin.presenter.event

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.SubscriberExceptionContext
import com.google.common.eventbus.SubscriberExceptionHandler
import mx.com.acerozin.presenter.InventoryManagerUI

//import com.google.common.eventbus.AsyncEventBus
/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Aug 30, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
class InventoryEventBus implements SubscriberExceptionHandler {

    final EventBus eventBus = new EventBus(this);
//		final EventBus eventBus = new AsyncEventBus(java.util.concurrent.Executors.newCachedThreadPool());

    static void post(final Object event) {
        InventoryManagerUI.getInventoryEventbus().eventBus.post(event);
    }

    static void register(final Object object) {
        InventoryManagerUI.getInventoryEventbus().eventBus.register(object);
    }

    static void unregister(final Object object) {
        InventoryManagerUI.getInventoryEventbus().eventBus.unregister(object);
    }

    @Override
    final void handleException(final Throwable exception,
                               final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }

}
