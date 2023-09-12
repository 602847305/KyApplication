package com.example.kyapplication.ui.activity;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kyapplication.R;
import com.example.kyapplication.contract.LoginContract;
import com.example.kyapplication.presenter.ui.LoginPresenter;
import com.example.kyapplication.ui.base.activity.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @Override
    public void showLoginSuccess() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {

    }
}
