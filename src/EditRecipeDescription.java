import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EditRecipeDescription {
    private final int recipeID;
    private final int recipeDescription;

    public EditRecipeDescription(int recipeID, int recipeDescription) {
        this.recipeID = recipeID;
        this.recipeDescription = recipeDescription;
    }

    public String editDescription() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String updateCookTime = "UPDATE recipe SET description='" + recipeDescription + "' WHERE recipe_id='" + recipeID + "'";
            stmt.executeUpdate(updateCookTime);

            db.close();
            return "Recipe Updated.";
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return "Error: Recipe Not Updated!";
    }

    public static void main(String[] args) {
        System.out.println("Start Cook Time Edit...");
        if (args.length == 2) {
            EditRecipeDescription editRecipeDescription = new EditRecipeDescription(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println(editRecipeDescription.editDescription());
        }
    }
}
