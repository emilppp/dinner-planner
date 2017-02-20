package se.kth.csc.iprog.dinnerplanner.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Dish;


public class MenuActivity extends Activity {

    DinnerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        model = ((DinnerPlannerApplication) this.getApplication()).getModel();

        // Title bar
        setContentView(R.layout.choose_menu);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.titlebar);

        // Guest drop down
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Integer[] guests = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, guests);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView costView = (TextView) findViewById(R.id.totalCostSum);

                model.setNumberOfGuests(i+1);
                System.out.println(model.getNumberOfGuests());
                costView.setText(model.getTotalMenuPrice()+ "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Listview for the different courses
        RecyclerView recyclerViewStarter = (RecyclerView) findViewById(R.id.recyclerViewStarter);
        RecyclerView recyclerViewMain = (RecyclerView) findViewById(R.id.recyclerViewMainCourse);
        RecyclerView recyclerViewDessert = (RecyclerView) findViewById(R.id.recyclerViewDesserts);

        LinearLayoutManager linearLayoutManagerStarter = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerMain = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerDessert = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewStarter.setLayoutManager(linearLayoutManagerStarter);
        recyclerViewMain.setLayoutManager(linearLayoutManagerMain);
        recyclerViewDessert.setLayoutManager(linearLayoutManagerDessert);


        // Use menuadapters f
        final MenuAdapter starterAdapter = new MenuAdapter(this, getStarters());
        final MenuAdapter mainAdapter = new MenuAdapter(this, getMain());
        final MenuAdapter dessertAdapter = new MenuAdapter(this, getDesserts());

        recyclerViewStarter.setAdapter(starterAdapter);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewDessert.setAdapter(dessertAdapter);


        model.setAdapters(starterAdapter, mainAdapter, dessertAdapter);
        model.getRecipiesOfAllTypes(new AsyncData() {
            @Override
            public void onData() {

            }
        });

        Button create = (Button) findViewById(R.id.createBtn);

        // Go to overview
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, OverviewActivity.class);
                startActivity(intent);
            }
        });

    }

    private Object[] getStarters() {
        return model.getDishesOfType(Dish.STARTER).toArray();
    }
    private Object[] getMain() {
        return  model.getDishesOfType(Dish.MAIN).toArray();
    }
    private Object[] getDesserts() {
        return  model.getDishesOfType(Dish.DESERT).toArray();
    }



}
