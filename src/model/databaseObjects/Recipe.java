package model.databaseObjects;
import java.util.ArrayList;

/**
 * Helper class to allow the table view to store information easier
 *
 * Class represents a recipe that hold a name, description, servings, cook time, difficulty, rating, steps, and ingredients
 */
public class Recipe {
    private String name;
    private String description;
    private float servings;
    private int cookTime;
    private String difficulty;
    private float rating;
    private String steps;
    private ArrayList<Ingredient> ingredients;

    public Recipe(String name, String description, float servings, int cookTime, String difficulty, float rating, String steps, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.servings = servings;
        this.cookTime = cookTime;
        this.difficulty = difficulty;
        this.rating = rating;
        this.steps = steps;
        this.ingredients = ingredients;
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

    @Override
    public String toString() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
