package com.example.kyapplication.ui.base.view;

public interface AbstractView {
    /**
     * 夜间模式
     */
    void setNightMode(boolean isNightMode);


    /**
     * showNormal
     */
    void showNormal();

    /**
     * Show error
     */
    void showErrorMsg();

    /**
     * Show loading
     */
    void showLoading();

    /**
     * Reload
     */
    void reload();

    /**
     * Show login view
     */
    void showLoginView();

    /**
     * Show logout view
     */
    void showLogoutView();

    /**
     * Show collect success
     */
    void showCollectSuccess();

    /**
     * Show cancel collect success
     */
    void showCancelCollectSuccess();

    /**
     * Show toast
     *
     * @param message Message
     */
    void showToast(String message);

    /**
     * Show snackBar
     *
     * @param message Message
     */
    void showSnackBar(String message);



}
