package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EditRecipeRating {
    private final int recipeID;
    private final int rating;

    public EditRecipeRating(int recipeID, int rating) {
        this.recipeID = recipeID;
        this.rating = rating;
    }

    public boolean editRecipeRating() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String updateRating = "UPDATE recipe SET rating='" + rating + "' WHERE recipe_id='" + recipeID + "'";
            stmt.executeUpdate(updateRating);
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
            EditRecipeRating newRating = new EditRecipeRating(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            if (newRating.editRecipeRating()) {
                System.out.println("Recipe Rating is edited.");
            }
        }

    }
}
