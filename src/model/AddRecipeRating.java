package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddRecipeRating {
    private final int recipeID;
    private final int rating;

    public AddRecipeRating(int recipeID, int rating) {
        this.recipeID = recipeID;
        this.rating = rating;
    }

    public boolean rateRecipe() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String getRating = "SELECT rating, number_of_ratings FROM recipe WHERE recipe_id=" + recipeID;
            ResultSet result = stmt.executeQuery(getRating);
            if(result.next()){
                float rating = result.getFloat("rating");
                int numRating = result.getInt("number_of_ratings");
                float newRating = ((rating * numRating) + this.rating) / (numRating + 1);
                String updateRating = "UPDATE recipe SET rating=" + newRating + " WHERE recipe_id=" + recipeID;
                String updateNumofRating = "UPDATE recipe SET number_of_ratings=" + (numRating+1) + " WHERE recipe_id=" + recipeID;
                stmt.executeUpdate(updateRating);
                stmt.executeUpdate(updateNumofRating);
            }
            db.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Start Editing to recipe ratings...");
        if (args.length == 2) {
            System.out.println("Recipe ID: " + args[0]);
            System.out.println("Recipe Rating: " + args[1]);
            AddRecipeRating newRating = new AddRecipeRating(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            if (newRating.rateRecipe()) {
                System.out.println("Recipe Rating is edited.");
            }
        }

    }
}
