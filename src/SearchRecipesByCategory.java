import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SearchRecipesByCategory {
    private final String search;

    public SearchRecipesByCategory(String search) {
        this.search = search;
    }

    public String getRecipes() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String selectRecipes = "SELECT recipe_name FROM recipe WHERE recipe_id IN " +
                    "(SELECT recipe_id FROM part_of WHERE category_name='" + search + "')";
            ResultSet rs;
            rs = stmt.executeQuery(selectRecipes);
            StringBuilder recipes = new StringBuilder();
            while(rs.next()) {
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
        if(args.length == 1) {
            SearchRecipesByCategory search = new SearchRecipesByCategory(args[0]);
            String recipes = search.getRecipes();
            System.out.print(recipes);
        }
        System.out.println("Search complete.");
    }
}
