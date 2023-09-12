package com.example.kyapplication.contract;

import com.example.kyapplication.ui.base.presenter.AbstractPresenter;
import com.example.kyapplication.ui.base.view.AbstractView;

public class LoginContract {
    public interface View extends AbstractView {

        /**
         * Show login data
         *
         */
        void showLoginSuccess();
    }

    public interface Presenter extends AbstractPresenter<View> {

        /**
         * Get Login data
         *
         * @param username user name
         * @param password password
         */
        void getLoginData(String username, String password);
    }
}
