package se.kth.csc.iprog.dinnerplanner.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

/**
 * Created by emil on 2017-02-02.
 */

public class MenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_menu);

        RecyclerView recyclerViewStarter = (RecyclerView) findViewById(R.id.recyclerViewStarter);
        RecyclerView recyclerViewMain = (RecyclerView) findViewById(R.id.recyclerViewMainCourse);
        RecyclerView recyclerViewDessert = (RecyclerView) findViewById(R.id.recyclerViewDesserts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewStarter.setLayoutManager(linearLayoutManager);

        MenuAdapter adapter = new MenuAdapter(null);
        recyclerViewStarter.setAdapter(adapter);
    }
}
