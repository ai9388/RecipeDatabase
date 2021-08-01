package model;

import model.databaseObjects.Ingredient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class MakeRecipe {
    private String recipeName;
    private String description;
    private int servings;
    private int cookTime;
    private String difficulty;
    private String steps;
    private ArrayList<Ingredient> ingredients;

    public MakeRecipe(String recipeName, String description, int servings, int cookTime, String difficulty, String steps, ArrayList<Ingredient> ingredients) {
        this.recipeName = recipeName;
        this.description = description;
        this.servings = servings;
        this.cookTime = cookTime;
        if(difficulty == null) {
            this.difficulty = getDifficulty(cookTime);
        }
        else {
            this.difficulty = difficulty;
        }
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public String getDifficulty(int cookTime) {
        String difficulty;
        if (cookTime <= 15) {
            difficulty = "Easy";
        } else if (cookTime <= 30) {
            difficulty = "Easy-Medium";
        } else if (cookTime <= 45) {
            difficulty = "Medium";
        } else if (cookTime <= 60) {
            difficulty = "Medium-Hard";
        } else {
            difficulty = "Hard";
        }
        return difficulty;
    }

    public boolean createRecipe() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();

            //Generate random recipeID while keep tracking for duplicates.
            Random random = new Random();
            int recipeID = 10000 + random.nextInt(1000);
            String existedCheck = "SELECT recipe_id FROM recipe WHERE recipe_id='" + recipeID + "'";
            ResultSet existedID = stmt.executeQuery(existedCheck);
            while (existedID.next()) {
                recipeID = 10000 + random.nextInt(1000);
                existedCheck = "SELECT recipe_id FROM recipe WHERE recipe_id='" + recipeID + "'";
                existedID = stmt.executeQuery(existedCheck);
            }

            //Record current date and time for the database
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateAndTime = dtf.format(now);
            dateAndTime = dateAndTime.substring(0, 10) + "', '" + dateAndTime.substring(11, 19);

            //Insert data into database
            String selectRecipe = "INSERT INTO recipe(recipe_id, recipe_name, description, servings, cook_time, date_made, time_made, difficulty, rating, steps)" +
                    "VALUES (' " + recipeID + "', '" + recipeName + "', '" + description + "', '" + servings + "', '" + cookTime + "', '" + dateAndTime + "', '" + difficulty + "', '" + 0 + "', '" + steps + "');";
            stmt.executeUpdate(selectRecipe);
            for(Ingredient ingredient : ingredients) {
                String getIngredientID = "SELECT item_id FROM item WHERE item_name='" + ingredient.getName() + "'";
                ResultSet ingredientID = stmt.executeQuery(getIngredientID);
                if(ingredientID.next()) {
                    int item_id = ingredientID.getInt("item_id");
                    String addIngredient = "INSERT INTO made_of(item_id, recipe_id, quantity) VALUES (" + item_id + ", " + recipeID + ", " + ingredient.getQuantity() + ")";
                    stmt.executeUpdate(addIngredient);
                }
            }
            db.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Start Adding to recipe...");
        if (args.length == 5) {
            System.out.println("Recipe Name: " + args[0]);
            System.out.println("Description: " + args[1]);
            System.out.println("Servings: " + args[2]);
            System.out.println("Cook Time: " + args[3]);
            System.out.println("Steps: " + args[4]);
        }
        MakeRecipe newRecipe = new MakeRecipe(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), null, args[4], null);
        if (newRecipe.createRecipe()) {
            System.out.println("Recipe is created.");
        }
    }
}
