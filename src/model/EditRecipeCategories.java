package model;

import model.databaseObjects.Ingredient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EditRecipeCategories {
    int recipe_id;
    public ArrayList<String> categories;

    public EditRecipeCategories(int recipe_id, ArrayList<String> categories) {
        this.recipe_id = recipe_id;
        this.categories = categories;
    }

    public boolean editCategories() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String deleteOldCategories = "DELETE FROM part_of WHERE recipe_id=" + recipe_id;
            stmt.executeUpdate(deleteOldCategories);
            for(String category : categories) {
                // INSERT INTO part_of(recipe_id, category_name) VALUES (recipe_id, category)
                String getCategoryID = "INSERT INTO part_of(recipe_id, category_name) VALUES (" + recipe_id + ", '"  + category + "')";
                stmt.executeUpdate(getCategoryID);
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
