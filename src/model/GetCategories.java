package model;

import model.databaseObjects.Recipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GetCategories {
    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        try{
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String gottaCatchThemAll = "SELECT category_name FROM recipe_category";
            ResultSet result = stmt.executeQuery(gottaCatchThemAll);
            while(result.next()) {
                categories.add(result.getString("category_name"));
            }
            db.close();
        }
        catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return categories;
    }
    public static void main(String[] args) {
        System.out.println("Start printing all Categories...");
        GetCategories getCategories = new GetCategories();
        ArrayList<String> categories = getCategories.getCategories();
        for(int i = 0; i < categories.size(); i++){
            System.out.println(categories.get(i));
        }
        System.out.println("Search complete.");
    }
}
