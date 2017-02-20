package se.kth.csc.iprog.dinnerplanner.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.android.AsyncData;
import se.kth.csc.iprog.dinnerplanner.android.MenuAdapter;
import se.kth.csc.iprog.dinnerplanner.android.R;
import se.kth.csc.iprog.dinnerplanner.android.SpoonacularAPIClient;

public class DinnerModel implements IDinnerModel{
	
	int numOfGuests = 0;
	Set<Dish> dishes = new HashSet<Dish>();
	MenuAdapter a, b, c;
	/**
	 * TODO: For Lab2 you need to implement the IDinnerModel interface.
	 * When you do this you will have all the needed fields and methods
	 * for the dinner planner (number of guests, selected dishes, etc.). 
	 */
	
	
	/**
	 * The constructor of the overall model. Set the default values here
	 */
	public DinnerModel(){

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

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		Dish dish;
		AsyncData data;

		public DownloadImageTask(Dish dish, AsyncData data) {
			this.dish = dish;
			this.data = data;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				mIcon11 = BitmapFactory.decodeStream(in, null, options);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {

			dish.setImageBitmap(result);

			dishes.add(dish);

			if(dish.getType() == Dish.STARTER && a != null) a.add(dish);
			if(dish.getType() == Dish.MAIN && b != null) b.add(dish);
			if(dish.getType() == Dish.DESERT && c != null) c.add(dish);

			data.onData();

		}
	}

	// 1-3
	public void loadRecipes(final int type, final AsyncData data) {

		String typ;
		if(type == 1) {
			typ = "appetizer";
		} else if (type == 2) {
			typ = "main course";
		} else {
			typ = "dessert";
		}
		SpoonacularAPIClient.get("recipes/search?type="+typ+"&number=2", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				System.out.println(response);
				try {
					String imgURL = response.getString("baseUri");
					JSONArray recipes = response.getJSONArray("results");
					for(int i = 0; i < recipes.length(); i++) {
						JSONObject obj = recipes.getJSONObject(i);
						Dish dish = new Dish(obj.getString("title"), type, imgURL + obj.getString("image"), "apa", R.drawable.toast, obj.getString("id"));
						new DownloadImageTask(dish, data).execute(dish.getImage());

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
		data.onData();
	}

	public void getRecipiesOfAllTypes(final AsyncData data) {
		loadRecipes(1, new AsyncData() {
			@Override
			public void onData() {
				loadRecipes(2, new AsyncData() {
					@Override
					public void onData() {
						loadRecipes(3, new AsyncData() {
							@Override
							public void onData() {
								data.onData();

							}
						});
					}
				});
			}
		});
	}

	public void setAdapters(MenuAdapter a, MenuAdapter b, MenuAdapter c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
}
