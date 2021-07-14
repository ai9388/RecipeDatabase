import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EditRecipeDifficulty {
    private final int recipeID;
    private final String recipeDifficulty;

    public EditRecipeDifficulty(int recipeID, String recipeDifficulty) {
        this.recipeID = recipeID;
        this.recipeDifficulty = recipeDifficulty;
    }

    public boolean editDifficulty() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String updateDifficulty = "UPDATE recipe SET difficulty='" + recipeDifficulty + "' WHERE recipe_id='" + recipeID + "'";
            stmt.executeUpdate(updateDifficulty);
            db.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Start Editing difficulty to recipe...");
        if (args.length == 2) {
            System.out.println("Recipe ID: " + args[0]);
            System.out.println("Recipe Difficulty: " + args[1]);
            EditRecipeDifficulty editRecipeDifficulty = new EditRecipeDifficulty(Integer.parseInt(args[0]), args[1]);
            if (editRecipeDifficulty.editDifficulty()) {
                System.out.println("Recipe difficulty was edited.");
            }
        }
    }
}
