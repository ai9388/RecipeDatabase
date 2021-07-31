package model;

import model.databaseObjects.Ingredient;
import model.databaseObjects.Recipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchRecipesByName {
    private final String search;

    public SearchRecipesByName(String search) {
        this.search = search;
    }

    public ArrayList<Recipe> getRecipes() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String selectRecipes = "SELECT recipe_name, description, servings, cook_time, difficulty, rating, steps, recipe_id FROM recipe WHERE recipe_name LIKE '%" + search + "%'";
            ResultSet rs;
            rs = stmt.executeQuery(selectRecipes);
            ArrayList<Recipe> recipes = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("recipe_name");
                String description = rs.getString("description");
                float servings = rs.getFloat("servings");
                int cook_time = rs.getInt("cook_time");
                String difficulty = rs.getString("difficulty");
                float rating = rs.getFloat("rating");
                String steps = rs.getString("steps");
                GetRecipesIngredients search = new GetRecipesIngredients(rs.getInt("recipe_id"));
                ArrayList<Ingredient> ingredients = search.getIngredients();
                recipes.add(new Recipe(name, description, servings, cook_time, difficulty, rating, steps, ingredients));
            }
            db.close();
            return recipes;
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        System.out.println("Start Recipe Search...");
        if (args.length == 1) {
            SearchRecipesByName search = new SearchRecipesByName(args[0]);
            ArrayList<Recipe> recipes = search.getRecipes();
            System.out.print(recipes);
        }
        System.out.println("Search complete.");
    }
}