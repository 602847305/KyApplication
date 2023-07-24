package com.example.kyapplication.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kyapplication.R;
import com.example.kyapplication.ui.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity {

    private NavController mNavController;
    private BottomNavigationView mBottomNavigationView;
    private AppBarConfiguration appBarConfiguration;
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
//        initClickListener();

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
}
