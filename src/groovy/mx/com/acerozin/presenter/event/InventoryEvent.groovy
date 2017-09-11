package mx.com.acerozin.presenter.event


import mx.com.acerozin.pogo.security.UserWrapper
import mx.com.acerozin.presenter.InventoryViewType;


/**
 *
 * @author Armando Montoya Hernandez
 * @email aj.montoya@outlook.com
 * @Date Aug 30, 2015
 * @Copyright � Armando Montoya 2015
 * All rights reserved 
 *
 */
abstract class InventoryEvent {

    static final class UserLoginRequestedEvent {
        private final String userName, password;

        UserLoginRequestedEvent(final String userName, final String password) {
            this.userName = userName;
            this.password = password;
        }

        String getUserName() {
            return userName;
        }

        String getPassword() {
            return password;
        }
    }

    static class BrowserResizeEvent {

    }

    static class UserLoggedOutEvent {

    }

    static class NotificationsCountUpdatedEvent {
    }

    static final class ReportsCountUpdatedEvent {
        private final int count;

        ReportsCountUpdatedEvent(final int count) {
            this.count = count;
        }

        int getCount() {
            return count;
        }

    }

    static final class PostViewChangeEvent {
        private final InventoryViewType view;

        PostViewChangeEvent(final InventoryViewType view) {
            this.view = view;
        }

        InventoryViewType getView() {
            return view;
        }
    }

    static class CloseOpenWindowsEvent {
    }

    static class ProfileUpdatedEvent {

        private final UserWrapper userDetails
        private Boolean isSuccessful
        private String errorMessage

        ProfileUpdatedEvent (final UserWrapper userDetails) {
            this.userDetails = userDetails
        }

        UserWrapper getUserDetails(){
            return this.userDetails
        }

        void setSuccessful(Boolean isSuccessfulFlag){
            this.isSuccessful = isSuccessfulFlag
        }

        Boolean isSuccessful(){
            return isSuccessful
        }

        String getErrorMessage(){
            return errorMessage
        }

        void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage
        }

    }

}
