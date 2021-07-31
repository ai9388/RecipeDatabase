package model;

import java.sql.*;
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
            Statement getItemID = db.createStatement();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateAndTime = dtf.format(now);
            String date = dateAndTime.substring(0, 10);
            String expirationDate = "" + (Integer.parseInt(date.substring(0, 4)) + 4) + date.substring(4, 10);
            ResultSet getItemIDResult = getItemID.executeQuery("SELECT item_id FROM item WHERE item_name='" + ingredient + "'");
            if (getItemIDResult.next()) {
                int itemID = getItemIDResult.getInt("item_id");
                Statement doesExist = db.createStatement();
                ResultSet doesExistRS = doesExist.executeQuery("SELECT * FROM owns WHERE item_id=" + itemID + " AND username='" + username + "'");
                if (doesExistRS.next()) {
                    stmt.executeUpdate("UPDATE owns SET current_quantity=" + quantity + " WHERE username='" + username + "' AND item_id=" + itemID + "");
                } else {
                    String existedIngredient = "INSERT INTO owns(purchase_date, expiration_date, quantity_bought, current_quantity, username, item_id) " +
                                            "VALUES ('" + date + "', '" + expirationDate + "', " + quantity + ", " + quantity + ", '" + username + "', " + itemID + ")";
                    stmt.executeUpdate(existedIngredient);
                }
                return true;
            }
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return false;
    }
//        //Select the existed Ingredient
//            //insert data into database
//
//            //Select the specific user and insert new ingredient along with the
//            // purchase date (Creation date), expiration date, quantity default as 1,
//            // current quantity default as 1. (Generate item_id?)
//
//            //EXPIRATION DATE ALGORITHM:
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            String dateAndTime = dtf.format(now);
//            int expiration = Integer.parseInt(dateAndTime.substring(0,4)) + 5;
//            String expirationDate = expiration + dateAndTime.substring(5,10);
//            System.out.println("Expiration: " + expirationDate);
//
//            String ingredientToAdd = "";
//            stmt.executeUpdate(ingredientToAdd);
//            db.close();
//            return true;
//        } catch (Exception e){
//            System.err.println("Error loading postgres driver.");
//            e.printStackTrace();
//        }
//        return false;
//     }

    public static void main(String[] args) {
        System.out.println("Adding an ingredient");
        if (args.length == 3) {
            System.out.println("Ingredient: " + args[0]);
            AddIngredients addIngredients = new AddIngredients(args[0], args[1]);
            if (addIngredients.addIngredient()){
                System.out.println("Ingredient is added");
            }
        }
    }
}
