package com.example.kyapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.kyapplication.R;
import com.example.kyapplication.ui.base.BaseActivity;
import com.example.kyapplication.utils.F;

public class ShowViewActivity extends BaseActivity {
    NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_view_activity);
        Toolbar actionBar = findViewById(R.id.tool_bar);
        setSupportActionBar(actionBar);

        Intent intent = getIntent();
        int flag = intent.getIntExtra("view",0);
        navController = Navigation.findNavController(this,R.id.navigation_fragment);


        int resId = R.id.circleFragment;
        switch (flag)
        {
            case 1 :
                resId= R.id.barFragment;
                break;
            case 2:
                resId= R.id.circleFragment;
                break;
            case 3:
                resId= R.id.musicFragment;
                F.d("```````musicFragment````");
                break;
            case 4:
                resId= R.id.musicFragment2;
                F.d("```````musicFragment``2``");
                break;
        }
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.fragment_show_view);
        navGraph.setStartDestination(resId);
        navController.setGraph(navGraph,null);
        NavigationUI.setupActionBarWithNavController(this,navController);
    }

    @Override
    public boolean onNavigateUp() {
        return NavigationUI.navigateUp(navController, (Openable) null);
    }


    protected enum WidgetView {
        Circle("圆环1",1),
        BarCharts("柱形图1",2);


        private String name;
        private int number;

        private WidgetView(String name,int number)
        {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

}
