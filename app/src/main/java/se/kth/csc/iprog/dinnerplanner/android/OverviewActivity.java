package se.kth.csc.iprog.dinnerplanner.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import se.kth.csc.iprog.dinnerplanner.model.Dish;


public class OverviewActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Default call to load previous state
        super.onCreate(savedInstanceState);

        // Set the view for the main activity screen
        // it must come before any call to findViewById method
        setContentView(R.layout.overview_view);

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.titlebar);

        View ingredientsButton = findViewById(R.id.ingredient_button);
        ImageView ingredientImg = (ImageView) ingredientsButton.findViewById(R.id.testpic);
        ingredientImg.setImageResource(R.drawable.sourdough);
        TextView ingredientText = (TextView) ingredientsButton.findViewById(R.id.testtext);
        ingredientText.setText("Ingredients");


        RecyclerView courses = (RecyclerView) findViewById(R.id.recyclerViewIngredients);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        courses.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("selectedList");
        ArrayList<Dish> selected = (ArrayList<Dish>) args.getSerializable("selectedList");

        MenuAdapter menuAdapter = new MenuAdapter(this, selected.toArray());
        courses.setAdapter(menuAdapter);


    }

}
