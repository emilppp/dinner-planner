package se.kth.csc.iprog.dinnerplanner.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.HashSet;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Dish;


public class MenuActivity extends Activity {

    DinnerModel model;
    String searchString;

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
        RecyclerView recyclerViewSearch = (RecyclerView) findViewById(R.id.recyclerViewSearch);

        LinearLayoutManager linearLayoutManagerStarter = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerMain = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerDessert = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerSearch = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewStarter.setLayoutManager(linearLayoutManagerStarter);
        recyclerViewMain.setLayoutManager(linearLayoutManagerMain);
        recyclerViewDessert.setLayoutManager(linearLayoutManagerDessert);
        recyclerViewSearch.setLayoutManager(linearLayoutManagerSearch);


        // Use menuadapters f
        final MenuAdapter starterAdapter = new MenuAdapter(this, get());
        final MenuAdapter mainAdapter = new MenuAdapter(this, get());
        final MenuAdapter dessertAdapter = new MenuAdapter(this, get());
        final MenuAdapter searchAdapter = new MenuAdapter(this, get());

        recyclerViewStarter.setAdapter(starterAdapter);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewDessert.setAdapter(dessertAdapter);
        recyclerViewSearch.setAdapter(searchAdapter);

        EditText search = (EditText) findViewById(R.id.searchString);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchAdapter.reset();
                model.loadRecipes(-1, new AsyncData() {
                    @Override
                    public void onData() {
                        
                    }
                }, charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        model.setAdapters(starterAdapter, mainAdapter, dessertAdapter, searchAdapter);
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



    private Object[] get() {
        Set<Dish> result = new HashSet<Dish>();
        return result.toArray();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        model.getDishes().clear();

    }
}
