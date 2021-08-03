package model;

import model.databaseObjects.Ingredient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CookRecipe {
    private final int recipeID;
    private final String username;
    private final float quantity;

    public CookRecipe(String username, int recipeID, float quantity) {
        this.recipeID = recipeID;
        this.username = username;
        this.quantity = quantity;
    }

    public String getUsername() {
        return this.username;
    }

    public float getQuantity() {
        return this.quantity;
    }

    public boolean cook(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String gottaCatchThemAll = "SELECT item_id, quantity FROM made_of WHERE recipe_id=" + recipeID;
            ResultSet testUserHasItAll = stmt.executeQuery(gottaCatchThemAll);
            while(testUserHasItAll.next()) {
                int item_id = testUserHasItAll.getInt("item_id");
                float item_quantity = testUserHasItAll.getInt("quantity") * quantity;
                Statement userIngredientQuantity = db.createStatement();
                String getUserIngredientQuantity = "SELECT current_quantity FROM owns WHERE username='" + username + "' AND item_id=" + item_id;
                ResultSet valid = userIngredientQuantity.executeQuery(getUserIngredientQuantity);
                if(valid.next()) {
                    float userQuantity = valid.getFloat("current_quantity");
                    if(userQuantity < item_quantity) {
                        db.close();
                        return false;
                    }
                }
                else {
                    db.close();
                    return false;
                }
            }
            ResultSet result = stmt.executeQuery(gottaCatchThemAll);
            while(result.next()) {
                Statement update = db.createStatement();
                int item_id = result.getInt("item_id");
                float item_quantity = result.getInt("quantity") * quantity;
                String updateUserIngredient = "UPDATE owns SET current_quantity=" +
                    "((SELECT current_quantity FROM owns WHERE username='" + username + "' AND item_id=" + item_id + ") - " + item_quantity + ") " +
                        "WHERE username='" + username + "' AND item_id=" + item_id;
                update.executeUpdate(updateUserIngredient);
            }
            String checkExists = "SELECT * FROM has_cooked WHERE username='" + username + "' AND recipe_id=" + recipeID;
            ResultSet exists = stmt.executeQuery(checkExists);
            if(!exists.next()) {
                String addCooked = "INSERT INTO has_cooked(username, recipe_id) VALUES ('" + username + "', " + recipeID +")";
                stmt.executeUpdate(addCooked);
            }
            db.close();
            return true;
        }
        catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }
}
