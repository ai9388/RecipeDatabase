package model;

import model.databaseObjects.Ingredient;
import model.databaseObjects.Recipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchRecipesByIngredients {
    private final String username;

    public SearchRecipesByIngredients(String username) {
        this.username = username;
    }

    public ArrayList<Recipe> getRecipes() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String selectRecipes = "SELECT * FROM recipe WHERE recipe_id NOT IN (SELECT r.recipe_id FROM made_of AS r INNER JOIN " +
                    "(SELECT * FROM made_of " +
                    "EXCEPT " +
                    "(SELECT * FROM made_of WHERE item_id IN " +
                    "(SELECT item_id FROM owns WHERE username='" + username + "'))) AS s ON (r.recipe_id = s.recipe_id))";
            ResultSet rs;
            rs = stmt.executeQuery(selectRecipes);
            ArrayList<Recipe> recipeArrayList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("recipe_id");
                String name = rs.getString("recipe_name");
                String description = rs.getString("description");
                float servings = rs.getFloat("servings");
                int cook_time = rs.getInt("cook_time");
                String difficulty = rs.getString("difficulty");
                float rating = rs.getFloat("rating");
                String steps = rs.getString("steps");
                GetRecipesIngredients search = new GetRecipesIngredients(rs.getInt("recipe_id"));
                ArrayList<Ingredient> ingredients = search.getIngredients();
                recipeArrayList.add(new Recipe(id, name, description, servings, cook_time, difficulty, rating, steps, ingredients));
            }
            db.close();
            return recipeArrayList;
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Start Recipe Search...");
        if (args.length == 1) {
            SearchRecipesByIngredients search = new SearchRecipesByIngredients(args[0]);
            ArrayList<Recipe> recipes = search.getRecipes();
            System.out.print(recipes);
        }
        System.out.println("Search complete.");
    }
}
