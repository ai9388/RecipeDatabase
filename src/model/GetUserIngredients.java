package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetUserIngredients {
    private final String username;

    public GetUserIngredients(String username) {
        this.username = username;
    }

    public String getIngredients() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String ingredientQuery = "SELECT item_name FROM item WHERE item_id IN (SELECT item_id FROM owns WHERE username='" + username + "')";
            ResultSet ingredients = stmt.executeQuery(ingredientQuery);
            StringBuilder result = new StringBuilder();
            while (ingredients.next()) {
                result.append(ingredients.getString("item_name")).append("\n");
            }
            db.close();
            return result.toString();
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println("Start Chef Ingredients Query...");
        System.out.println("username: " + args[0]);
        if (args.length == 1) {
            GetUserIngredients getUserIngredients = new GetUserIngredients(args[0]);
            System.out.println(getUserIngredients.getIngredients());
        }
        System.out.println("Ingredient Query Complete.");
    }
}
