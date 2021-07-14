import java.sql.*;

public class EditRecipeSteps {
    private final int recipeID;
    private final String steps;

    public EditRecipeSteps(int recipeID, String steps){
        this.recipeID = recipeID;
        this.steps = steps;
    }

    public boolean editRecipeSteps(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String updateSteps = "UPDATE recipe SET steps='" + steps + "' WHERE recipe_id='" + recipeID + "'";
            stmt.executeUpdate(updateSteps);
            db.close();
        }catch (Exception e) {
            System.err.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println("Start Editing to recipe steps...");
        if (args.length == 2) {
            System.out.println("Recipe ID: " + args[0]);
            System.out.println("Recipe steps: " + args[1]);
            EditRecipeSteps newRecipe = new EditRecipeSteps(Integer.parseInt(args[0]), args[1]);
            if (newRecipe.editRecipeSteps()) {
                System.out.println("Recipe step is edited.");
            }
        }

    }
}
