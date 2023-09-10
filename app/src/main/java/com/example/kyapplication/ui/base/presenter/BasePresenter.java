package com.example.kyapplication.ui.base.presenter;

import com.example.kyapplication.ui.base.view.AbstractView;

import io.reactivex.disposables.Disposable;

public class BasePresenter <T extends AbstractView> implements AbstractPresenter<T>{


    @Override
    public void attachView(T view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void addRxBindingSubscribe(Disposable disposable) {

    }

    @Override
    public boolean getNightModeState() {
        return false;
    }

    @Override
    public void setLoginStatus(boolean loginStatus) {

    }

    @Override
    public boolean getLoginStatus() {
        return false;
    }

    @Override
    public String getLoginAccount() {
        return null;
    }

    @Override
    public void setLoginAccount(String account) {

    }

    @Override
    public void setLoginPassword(String password) {

    }

    @Override
    public int getCurrentPage() {
        return 0;
    }
}
