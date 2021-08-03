package model;

import model.databaseObjects.Ingredient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EditRecipeIngredients {
    int recipe_id;
    public ArrayList<Ingredient> ingredients;

    public EditRecipeIngredients(int recipe_id, ArrayList<Ingredient> ingredients) {
        this.recipe_id = recipe_id;
        this.ingredients = ingredients;
    }

    public boolean editIngredients() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String deleteOldIngredients = "DELETE FROM made_of WHERE recipe_id=" + recipe_id;
            stmt.executeUpdate(deleteOldIngredients);
            for(Ingredient ingredient : ingredients) {
                String getIngredientID = "SELECT item_id FROM item WHERE item_name='" + ingredient.getName() + "'";
                ResultSet ingredientID = stmt.executeQuery(getIngredientID);
                if(ingredientID.next()) {
                    int item_id = ingredientID.getInt("item_id");
                    String addIngredient = "INSERT INTO made_of(item_id, recipe_id, quantity) VALUES (" + item_id + ", " + recipe_id + ", " + ingredient.getQuantity() + ")";
                    stmt.executeUpdate(addIngredient);
                }
            }
            db.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }
}
