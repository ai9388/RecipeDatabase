import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Back end implementation for deleting an existing recipe. Includes SQl.
 */
public class DeleteRecipe {
    private final String recipeID;

    public DeleteRecipe(String recipeID) {
        this.recipeID = recipeID;
    }

    public boolean getRecipe() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String deleteRecipe = "DELETE FROM recipe WHERE recipe_id='" + recipeID + "'";
            stmt.executeUpdate(deleteRecipe);
            db.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Start Recipe Search...");
        if(args.length == 1) {
            DeleteRecipe delete = new DeleteRecipe(args[0]);
            boolean recipes = delete.getRecipe();
            System.out.print(recipes);
        }
        System.out.println("Login sequence complete.");
    }
}
