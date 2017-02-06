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

import java.util.ArrayList;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Dish;

/**
 * Created by emil on 2017-02-02.
 */

public class MenuActivity extends Activity {
    DinnerModel model = new DinnerModel();
    int previous = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_menu);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.titlebar);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Integer[] guests = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, guests);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView costView = (TextView) findViewById(R.id.totalCostSum);
                String costString = costView.getText().toString();
                int totCost = Integer.parseInt(costString);
                totCost /= previous;
                totCost *= (i+1);
                costView.setText(totCost+"");
                previous = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RecyclerView recyclerViewStarter = (RecyclerView) findViewById(R.id.recyclerViewStarter);
        RecyclerView recyclerViewMain = (RecyclerView) findViewById(R.id.recyclerViewMainCourse);
        RecyclerView recyclerViewDessert = (RecyclerView) findViewById(R.id.recyclerViewDesserts);

        LinearLayoutManager linearLayoutManagerStarter = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerMain = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerDessert = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewStarter.setLayoutManager(linearLayoutManagerStarter);
        recyclerViewMain.setLayoutManager(linearLayoutManagerMain);
        recyclerViewDessert.setLayoutManager(linearLayoutManagerDessert);


        MenuAdapter starterAdapter = new MenuAdapter(this, getStarters());
        MenuAdapter mainAdapter = new MenuAdapter(this, getMain());
        MenuAdapter dessertAdapter = new MenuAdapter(this, getDesserts());


        recyclerViewStarter.setAdapter(starterAdapter);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewDessert.setAdapter(dessertAdapter);

        Button create = (Button) findViewById(R.id.createBtn);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, OverviewActivity.class);
                intent.putExtra("selectedList", getSelected());
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

    private ArrayList<Dish> getSelected() {
         return model.getSelected();
    }
}
