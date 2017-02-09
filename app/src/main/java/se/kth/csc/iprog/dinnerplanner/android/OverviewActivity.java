package se.kth.csc.iprog.dinnerplanner.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Dish;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;


public class OverviewActivity extends Activity {


    Intent intent = null;
    DinnerModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Default call to load previous state
        super.onCreate(savedInstanceState);
        model = ((DinnerPlannerApplication) this.getApplication()).getModel();
        // Set the view for the main activity screen
        // it must come before any call to findViewById method
        setContentView(R.layout.overview_view);

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.titlebar);

        intent = getIntent();
        TextView totCost = (TextView) findViewById(R.id.totalCost);
        totCost.setText(intent.getStringExtra("totCost").toString());



        View ingredientsButton = findViewById(R.id.ingredient_button);
        ImageView ingredientImg = (ImageView) ingredientsButton.findViewById(R.id.testpic);
        ingredientImg.setImageResource(R.drawable.sourdough);
        TextView ingredientText = (TextView) ingredientsButton.findViewById(R.id.testtext);
        ingredientText.setText("Ingredients");

        RecyclerView courses = (RecyclerView) findViewById(R.id.recyclerViewIngredients);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        courses.setLayoutManager(linearLayoutManager);

        updateStuff();
        ingredientImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateStuff();
            }
        });



        Object[] selected = model.getSelected().toArray();

        MenuAdapter menuAdapter = new MenuAdapter(this, selected){
            @Override
            protected void onClickImage(ViewHolder holder, Dish dish) {
                super.onClickImage(holder, dish);
                changeView(R.layout.instruction_view);

                TextView courseType = (TextView) findViewById(R.id.typeOfCourse);
                TextView nameOfCourse = (TextView) findViewById(R.id.nameOfCourse);
                TextView courseDescr = (TextView) findViewById(R.id.instructionDesc);

                courseType.setText(dish.getTypeName());
                nameOfCourse.setText(dish.getName());
                courseDescr.setText(dish.getDescription());

            }
        };
        courses.setAdapter(menuAdapter);
    }

    private void changeView(int id) {
        View view = findViewById(R.id.in);
        ViewGroup parent = (ViewGroup) view.getParent();
        int index = parent.indexOfChild(view);
        parent.removeView(view);
        view = getLayoutInflater().inflate(id, parent, false);
        parent.addView(view, index);
    }

    private void updateStuff() {
        changeView(R.layout.ingredients_view);

        TextView participants = (TextView) findViewById(R.id.persText);
        participants.setText(intent.getIntExtra("participants", 1) + " pers");

        Set<Ingredient> ingredients = model.getSelectedIngredients();
        final StringBuilder sb = new StringBuilder();
        TextView ingredientsList = (TextView) findViewById(R.id.ingredientsList);
        for(Ingredient i : ingredients) {
            sb.append(i.getName()+" "+i.getQuantity()+" "+i.getUnit()+"\n");
        }
        ingredientsList.setText(sb);

    }

}
