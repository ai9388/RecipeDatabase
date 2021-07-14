import java.sql.*;

public class EditRecipeName {
    private final int recipeID;
    private final String recipeName;

    public EditRecipeName(int recipeID, String recipeName) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
    }

    public boolean editRecipeName() {
        try {
            boolean result = false;
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String updateName = "UPDATE recipe SET description='" + recipeName + "' WHERE recipe_id='" + recipeID + "'";
            stmt.executeUpdate(updateName);
            db.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Start Editing to recipe...");
        if (args.length == 2) {
            System.out.println("Recipe ID: " + args[0]);
            System.out.println("Recipe Name: " + args[1]);
        }
        EditRecipeName newRecipe = new EditRecipeName(Integer.parseInt(args[0]), args[1]);
        if (newRecipe.editRecipeName()) {
            System.out.println("Recipe is edited.");
        }
    }
}
