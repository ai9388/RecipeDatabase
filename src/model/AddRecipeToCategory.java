package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Back end implementation for adding a recipe to a category. Includes SQl.
 */
public class AddRecipeToCategory {
    private final String recipeID;
    private final String categoryName;

    public AddRecipeToCategory(String recipeID, String categoryName) {
        this.recipeID = recipeID;
        this.categoryName = categoryName;
    }

    public boolean addToCategory() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String recipeCategoryUpdate = "INSERT INTO part_of(recipe_id, category_name) " +
                    "VALUES (" + recipeID + ", '" + categoryName + "');";
            stmt.executeUpdate(recipeCategoryUpdate);
            db.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Start Category Addition Query...");
        System.out.println("recipe id: " + args[0]);
        System.out.println("category name: " + args[1]);
        if (args.length == 2) {
            AddRecipeToCategory addRecipeToCategory = new AddRecipeToCategory(args[0], args[1]);
            System.out.println(addRecipeToCategory.addToCategory());
        }
        System.out.println("Addition Complete.");
    }
}
