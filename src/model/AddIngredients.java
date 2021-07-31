package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddIngredients {
    private final String ingredient;
    private final String username;

    public AddIngredients(String username, String ingredient){
        this.ingredient = ingredient;
        this.username = username;
    }

    public boolean addIngredient(){
        try {
            //connect to database
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();

            //Select the existed Ingredient
            String existedIngredient = "SELECT * FROM owns WHERE username='" + username + "' AND item_id='"  + "'";
            ResultSet existanceTable = stmt.executeQuery(existedIngredient);
            if (existanceTable.next()){
                //Quantity plus one
                String updateQuantity = "";
                stmt.executeUpdate(updateQuantity);
                db.close();
                return true;
            }
            //insert data into database

            //Select the specific user and insert new ingredient along with the
            // purchase date (Creation date), expiration date, quantity default as 1,
            // current quantity default as 1. (Generate item_id?)

            //EXPIRATION DATE ALGORITHM:
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateAndTime = dtf.format(now);
            int expiration = Integer.parseInt(dateAndTime.substring(0,4)) + 5;
            String expirationDate = expiration + dateAndTime.substring(5,10);
            System.out.println("Expiration: " + expirationDate);

            String ingredientToAdd = "";
            stmt.executeUpdate(ingredientToAdd);
            db.close();
            return true;
        } catch (Exception e){
            System.err.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Adding an ingredient");
        if (args.length == 2) {
            System.out.println("Ingredient: " + args[0]);
            AddIngredients addIngredients = new AddIngredients(args[0], args[1]);
            if (addIngredients.addIngredient()){
                System.out.println("Ingredient is added");
            }
        }
    }
}
