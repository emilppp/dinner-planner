package se.kth.csc.iprog.dinnerplanner.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.android.R;

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
		
		//Adding some example data, you can add more
		Dish dish1 = new Dish("French toast",Dish.STARTER,"toast.jpg","In a large mixing bowl, beat the eggs. Add the milk, brown sugar and nutmeg; stir well to combine. Soak bread slices in the egg mixture until saturated. Heat a lightly oiled griddle or frying pan over medium high heat. Brown slices on both sides, sprinkle with cinnamon and serve hot.", R.drawable.toast);
		Ingredient dish1ing1 = new Ingredient("eggs",0.5,"",1);
		Ingredient dish1ing2 = new Ingredient("milk",30,"ml",6);
		Ingredient dish1ing3 = new Ingredient("brown sugar",7,"g",1);
		Ingredient dish1ing4 = new Ingredient("ground nutmeg",0.5,"g",12);
		Ingredient dish1ing5 = new Ingredient("white bread",2,"slices",2);
		dish1.addIngredient(dish1ing1);
		dish1.addIngredient(dish1ing2);
		dish1.addIngredient(dish1ing3);
		dish1.addIngredient(dish1ing4);
		dish1.addIngredient(dish1ing5);
		dishes.add(dish1);

		Dish dish2 = new Dish("Meat balls",Dish.MAIN,"meatballs.jpg","Preheat an oven to 400 degrees F (200 degrees C). Place the beef into a mixing bowl, and season with salt, onion, garlic salt, Italian seasoning, oregano, red pepper flakes, hot pepper sauce, and Worcestershire sauce; mix well. Add the milk, Parmesan cheese, and bread crumbs. Mix until evenly blended, then form into 1 1/2-inch meatballs, and place onto a baking sheet. Bake in the preheated oven until no longer pink in the center, 20 to 25 minutes.", R.drawable.meatballs);
		Ingredient dish2ing1 = new Ingredient("extra lean ground beef",115,"g",20);
		Ingredient dish2ing2 = new Ingredient("sea salt",0.7,"g",3);
		Ingredient dish2ing3 = new Ingredient("small onion, diced",0.25,"",2);
		Ingredient dish2ing4 = new Ingredient("garlic salt",0.6,"g",3);
		Ingredient dish2ing5 = new Ingredient("Italian seasoning",0.3,"g",3);
		Ingredient dish2ing6 = new Ingredient("dried oregano",0.3,"g",3);
		Ingredient dish2ing7 = new Ingredient("crushed red pepper flakes",0.6,"g",3);
		Ingredient dish2ing8 = new Ingredient("Worcestershire sauce",16,"ml",7);
		Ingredient dish2ing9 = new Ingredient("milk",20,"ml",4);
		Ingredient dish2ing10 = new Ingredient("grated Parmesan cheese",5,"g",8);
		Ingredient dish2ing11 = new Ingredient("seasoned bread crumbs",115,"g",4);
		dish2.addIngredient(dish2ing1);
		dish2.addIngredient(dish2ing2);
		dish2.addIngredient(dish2ing3);
		dish2.addIngredient(dish2ing4);
		dish2.addIngredient(dish2ing5);
		dish2.addIngredient(dish2ing6);
		dish2.addIngredient(dish2ing7);
		dish2.addIngredient(dish2ing8);
		dish2.addIngredient(dish2ing9);
		dish2.addIngredient(dish2ing10);
		dish2.addIngredient(dish2ing11);
		dishes.add(dish2);

		Dish dish3 = new Dish("Meat ballerinos",Dish.MAIN,"meatballs.jpg","Preheat an oven to 400 degrees F (200 degrees C). Place the beef into a mixing bowl, and season with salt, onion, garlic salt, Italian seasoning, oregano, red pepper flakes, hot pepper sauce, and Worcestershire sauce; mix well. Add the milk, Parmesan cheese, and bread crumbs. Mix until evenly blended, then form into 1 1/2-inch meatballs, and place onto a baking sheet. Bake in the preheated oven until no longer pink in the center, 20 to 25 minutes.", R.drawable.meatballs);
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
		dishes.add(dish3);

		dishes.add(newDish("aa3", 3));
		dishes.add(newDish("aa4", 3));
		dishes.add(newDish("aa5", 3));
		dishes.add(newDish("aa6", 3));
		dishes.add(newDish("aa7", 3));
		dishes.add(newDish("aa8", 3));
		dishes.add(newDish("aa9", 3));
		dishes.add(newDish("aa10", 3));
		dishes.add(newDish("aa11", 3));
		dishes.add(newDish("aa12", 3));

		dishes.add(newDish("aa3", 2));
		dishes.add(newDish("aa4", 2));
		dishes.add(newDish("aa5", 2));
		dishes.add(newDish("aa6", 2));
		dishes.add(newDish("aa7", 2));
		dishes.add(newDish("aa8", 2));
		dishes.add(newDish("aa9", 2));
		dishes.add(newDish("aa10", 2));
		dishes.add(newDish("aa11", 2));
		dishes.add(newDish("aa12", 2));


	}

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

	@Override
	public float getTotalMenuPrice() {
		int sum = 0;
		Set<Ingredient> ing = getAllIngredients();
		for(Ingredient i : ing) {
			sum += i.getPrice();

		}
		return sum;
	}

	public ArrayList<Dish> getSelected() {
			ArrayList<Dish> result = new ArrayList<>();
			for(Dish d : dishes){
				if(d.marked){
					result.add(d);
				}
			}

		return result;
	}

	@Override
	public void addDishToMenu(Dish dish) {
        dishes.add(dish);
	}

	@Override
	public void removeDishFromMenu(Dish dish) {
        dishes.remove(dish);
	}
}
