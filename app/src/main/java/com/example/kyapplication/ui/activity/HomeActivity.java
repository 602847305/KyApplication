package com.example.kyapplication.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kyapplication.R;
import com.example.kyapplication.ui.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private NavController mNavController;
    private BottomNavigationView mBottomNavigationView;
    private AppBarConfiguration appBarConfiguration;
    private final String[] permission ={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};
    public static final int RC_READ_EXTERNAL_STORAGE = 1; // requestCode
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar actionBar = findViewById(R.id.tool_bar);
//        actionBar.setCollapseIcon(R.mipmap.btn_1);
//        actionBar.setNavigationIcon(R.mipmap.btn_1);
        actionBar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.b8z));
        setSupportActionBar(actionBar);
        mNavController = Navigation.findNavController(this,R.id.navigation_home);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        appBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        NavigationUI.setupWithNavController(mBottomNavigationView,mNavController);
        NavigationUI.setupActionBarWithNavController(this,mNavController);
//      initClickListener();
        if (EasyPermissions.hasPermissions(this,permission))
        {
            //已经获取权限

        }else {
            EasyPermissions.requestPermissions(this, "申请内存权限",
                    RC_READ_EXTERNAL_STORAGE, permission);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item,mNavController)||super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        return NavigationUI.navigateUp(mNavController, appBarConfiguration)||super.onNavigateUp();
    }

    private void initClickListener()
    {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("TAG",item.getItemId()+"");
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_nav,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //一些权限被允许
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //一些权限被禁止
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
