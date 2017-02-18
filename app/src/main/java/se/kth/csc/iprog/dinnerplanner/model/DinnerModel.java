package se.kth.csc.iprog.dinnerplanner.model;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.entity.mime.Header;
import se.kth.csc.iprog.dinnerplanner.android.R;
import se.kth.csc.iprog.dinnerplanner.android.SpoonacularAPIClient;

public class DinnerModel implements IDinnerModel{
	
	int numOfGuests = 0;
	Set<Dish> dishes = new HashSet<Dish>();
	/**
	 * TODO: For Lab2 you need to implement the IDinnerModel interface.
	 * When you do this you will have all the needed fields and methods
	 * for the dinner planner (number of guests, selected dishes, etc.). 
	 */
	
	
	/**
	 * The constructor of the overall model. Set the default values here
	 */
	public DinnerModel(){

		SpoonacularAPIClient.get("recipes/search?query=pizza", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				System.out.println(response);
				try {
					String imgURL = response.getString("baseUri");
					JSONArray recipes = response.getJSONArray("results");
					for(int i = 0; i < recipes.length(); i++) {
						JSONObject obj = recipes.getJSONObject(i);
						Dish dish = new Dish(obj.getString("title"), 1, imgURL + obj.getString("image"), "apa", R.drawable.toast, obj.getString("id"));
						dishes.add(dish);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}

			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});




	}

	public void fetchIngredients(final Dish dishe, final AsyncData callback) {
		if(dishe.hasFetched) { callback.onData(); return; }
		SpoonacularAPIClient.get("recipes/"+ dishe.id + "/information", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				System.out.println(response);
				try {
					JSONArray ingredients = response.getJSONArray("extendedIngredients");
					for(int i = 0; i<ingredients.length(); i++) {
						JSONObject obj = ingredients.getJSONObject(i);
						dishe.addIngredient(new Ingredient(obj.getString("name"), Double.parseDouble(obj.getString("amount")), obj.getString("unit"), Double.parseDouble(obj.getString("amount"))));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}

			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}

		});
		callback.onData();
	}

	public void fetchInstructions(final Dish dishe, final AsyncData callback) {
		if(dishe.hasFetched) { callback.onData(); return; }
		dishe.hasFetched = true;
		SpoonacularAPIClient.get("recipes/"+ dishe.id + "/analyzedInstructions", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
				super.onSuccess(statusCode, headers, response);
				System.out.println(response);
				try {
					JSONObject instructions = response.getJSONObject(0);
					JSONArray steps = instructions.getJSONArray("steps");

					for(int i = 0; i<steps.length(); i++) {
						JSONObject obj = steps.getJSONObject(i);
						dishe.setDescription(dishe.getDescription() + obj.getString("step") + "\n");
					}

					callback.onData();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});

	}

	/*
	public Dish newDish(String s, int dish) {
		Dish dish3 = new Dish("Meat ballerinos",dish,"meatballs.jpg","Preheat an oven to 400 degrees F (200 degrees C). Place the beef into a mixing bowl, and season with salt, onion, garlic salt, Italian seasoning, oregano, red pepper flakes, hot pepper sauce, and Worcestershire sauce; mix well. Add the milk, Parmesan cheese, and bread crumbs. Mix until evenly blended, then form into 1 1/2-inch meatballs, and place onto a baking sheet. Bake in the preheated oven until no longer pink in the center, 20 to 25 minutes.", R.drawable.meatballs);
		Ingredient dish3ing1 = new Ingredient("extra lean ground beef",115,"g",20);
		Ingredient dish3ing2 = new Ingredient("sea salt",0.7,"g",3);
		Ingredient dish3ing3 = new Ingredient("small onion, diced",0.25,"",2);
		Ingredient dish3ing4 = new Ingredient("garlic salt",0.6,"g",3);
		Ingredient dish3ing5 = new Ingredient("Italian seasoning",0.3,"g",3);
		Ingredient dish3ing6 = new Ingredient("dried oregano",0.3,"g",3);
		Ingredient dish3ing7 = new Ingredient("crushed red pepper flakes",0.6,"g",3);
		Ingredient dish3ing8 = new Ingredient("Worcestershire sauce",16,"ml",7);
		Ingredient dish3ing9 = new Ingredient("milk",20,"ml",4);
		Ingredient dish3ing10 = new Ingredient("grated Parmesan cheese",5,"g",8);
		Ingredient dish3ing11 = new Ingredient("seasoned bread crumbs",115,"g",4);
		dish3.addIngredient(dish3ing1);
		dish3.addIngredient(dish3ing2);
		dish3.addIngredient(dish3ing3);
		dish3.addIngredient(dish3ing4);
		dish3.addIngredient(dish3ing5);
		dish3.addIngredient(dish3ing6);
		dish3.addIngredient(dish3ing7);
		dish3.addIngredient(dish3ing8);
		dish3.addIngredient(dish3ing9);
		dish3.addIngredient(dish3ing10);
		dish3.addIngredient(dish3ing11);
		dish3.setName(s);
		return dish3;
	}
	*/
	/**
	 * Returns the set of dishes of specific type. (1 = starter, 2 = main, 3 = desert).
	 */
	public Set<Dish> getDishes(){
		return dishes;
	}
	
	/**
	 * Returns the set of dishes of specific type. (1 = starter, 2 = main, 3 = desert).
	 */
	public Set<Dish> getDishesOfType(int type){
		Set<Dish> result = new HashSet<Dish>();
		for(Dish d : dishes){
			if(d.getType() == type){
				result.add(d);
			}
		}
		return result;
	}
	
	/**
	 * Returns the set of dishes of specific type, that contain filter in their name
	 * or name of any ingredient. 
	 */
	public Set<Dish> filterDishesOfType(int type, String filter){
		Set<Dish> result = new HashSet<Dish>();
		for(Dish d : dishes){
			if(d.getType() == type && d.contains(filter)){
				result.add(d);
			}
		}
		return result;
	}


	@Override
	public int getNumberOfGuests() {
		return numOfGuests;
	}

	@Override
	public void setNumberOfGuests(int numberOfGuests) {
        numOfGuests = numberOfGuests;
	}

	@Override
	public Dish getSelectedDish(int type) {
		return null;
	}

	@Override
	public Set<Dish> getFullMenu() {
		return dishes;
	}

	@Override
	public Set<Ingredient> getAllIngredients() {
        Set<Ingredient> ingredients = new HashSet<Ingredient>();
        for(Dish d : dishes) {
            for(Ingredient i : d.getIngredients()) {
                ingredients.add(i);
            }
        }

        return ingredients;
	}

	public Set<Ingredient> getSelectedIngredients() {
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		for(Dish d : dishes) {
			if(d.marked) {
				for (Ingredient i : d.getIngredients()) {
					ingredients.add(i);
				}
			}
		}

		return ingredients;
	}

	@Override
	public float getTotalMenuPrice() {
		int sum = 0;
		Set<Dish> ing = getSelected();
		for(Dish i : ing) {
			sum += i.getCost();

		}
		return sum * getNumberOfGuests();
	}

	public Set<Dish> getSelected() {
			Set<Dish> result = new HashSet<>();
			for(Dish d : dishes){
				if(d.marked){
					result.add(d);
				}
			}

		return result;
	}



	@Override
	public void addDishToMenu(Dish dish) {
        dish.marked = true;
		dishes.add(dish);
	}

	@Override
	public void removeDishFromMenu(Dish dish) {
        dish.marked = false;
		dishes.remove(dish);
	}
}
