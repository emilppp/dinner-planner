package se.kth.csc.iprog.dinnerplanner.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Dish implements Serializable {
	
	public static final int STARTER = 1;
	public static final int MAIN = 2;
	public static final int DESERT = 3;



	String name;
	int type; // starter (1), main (2) or desert (3)  
	String image;
	String description;
	int imageId;
	public boolean marked = false;
	String id;
	public boolean hasFetched = false;
	Bitmap bmp;

	Set<Ingredient> ingredients = new HashSet<Ingredient>();

	public Dish(String name, int type, String image, String description, int imageId, String id) {
		this.name = name;
		this.type = type;
		this.image = image;
		this.description = description;
		this.imageId = imageId;
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public boolean hasFetched() { return hasFetched; }
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public String getTypeName() {
		int type = getType();

		if(type == STARTER) return "Starter";
		if(type == MAIN) return "Main";

		return "Dessert";

	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImage() {
		return image;
	}
	public int getImageId() { return imageId; }
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<Ingredient> getIngredients(){
		return ingredients;
	}
	
	public void addIngredient(Ingredient ing){
		ingredients.add(ing);
	}
	
	public void removeIngredient(Ingredient ing){
		ingredients.remove(ing);
	}
	
	public boolean contains(String filter){
		if(name.toLowerCase().contains(filter.toLowerCase())){
			return true;
		}
		for(Ingredient i : ingredients){
			if(i.getName().toLowerCase().contains(filter.toLowerCase())){
				return true;
			}
		}
		return false;
	}

	public int getCost() {
		int sum = 0;
		Set<Ingredient> ing = getIngredients();
		for(Ingredient i : ing) {
			sum += i.getPrice();

		}
		return sum;
	}

	public void setImageBitmap(Bitmap bmp) {
		this.bmp = bmp;
	}

	public Bitmap getBitmap() {
		return Bitmap.createScaledBitmap(bmp, 180, 180, false);
	}

}
