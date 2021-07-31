package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SearchRecipesByIngredients {
    private final String username;

    public SearchRecipesByIngredients(String username) {
        this.username = username;
    }

    public String getRecipes() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String selectRecipes = "SELECT * FROM recipe WHERE recipe_id NOT IN (SELECT r.recipe_id FROM made_of AS r INNER JOIN " +
                    "(SELECT * FROM made_of " +
                    "EXCEPT " +
                    "(SELECT * FROM made_of WHERE item_id IN " +
                    "(SELECT item_id FROM owns WHERE username='" + username + "'))) AS s ON (r.recipe_id = s.recipe_id))";
            ResultSet rs;
            rs = stmt.executeQuery(selectRecipes);
            StringBuilder recipes = new StringBuilder();
            while (rs.next()) {
                recipes.append(rs.getString("recipe_name")).append("\n");
            }
            db.close();
            return recipes.toString();
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println("Start Recipe Search...");
        if (args.length == 1) {
            SearchRecipesByIngredients search = new SearchRecipesByIngredients(args[0]);
            ArrayList<Recipe> recipes = search.getRecipes();
            System.out.print(recipes);
        }
        System.out.println("Search complete.");
    }
}
