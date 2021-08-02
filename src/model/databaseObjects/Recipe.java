package model.databaseObjects;
import java.util.ArrayList;

/**
 * Helper class to allow the table view to store information easier
 *
 * Class represents a recipe that hold a name, description, servings, cook time, difficulty, rating, steps, and ingredients
 */
public class Recipe {
    private int id;
    private String name;
    private String description;
    private float servings;
    private int cookTime;
    private String difficulty;
    private float rating;
    private String steps;
    private ArrayList<Ingredient> ingredients;
    private String date;
    private String time;
    private ArrayList<String> categories;

    public Recipe(int id, String name, String description, float servings, int cookTime, String difficulty,
                  float rating, String steps, ArrayList<Ingredient> ingredients, String date, String time, ArrayList<String> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.servings = servings;
        this.cookTime = cookTime;
        this.difficulty = difficulty;
        this.rating = rating;
        this.steps = steps;
        this.ingredients = ingredients;
        this.date = date;
        this.time = time;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getServings() {
        return servings;
    }

    public int getCookTime() {
        return cookTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public float getRating() {
        return rating;
    }

    public String getSteps() {
        return steps;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return name;
    }
}
