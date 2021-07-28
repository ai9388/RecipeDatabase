package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;

import java.util.Scanner;
import java.util.concurrent.atomic.LongAccumulator;


public class RecipeGUI extends Application {
    private Stage stage;
    private String username;
    private String password;

    public void init() {
        username = null;
        password = null;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        indexPage(stage);
    }

    private void changeStageSize() {
        this.stage.minHeightProperty().set(1080);
        this.stage.maxHeightProperty().set(1080);
        this.stage.minWidthProperty().set(1920);
        this.stage.maxWidthProperty().set(1920);
    }

    public Stage indexPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        if(username == null) {
            VBox signInOptions = new VBox();
            Button signIn = new Button();
            signIn.setText("Sign In");
            signIn.setOnAction(event -> signInPage(stage));
            Button register = new Button();
            register.setText("Register");
            register.setOnAction(event -> registerPage(stage));
            signInOptions.getChildren().add(signIn);
            signInOptions.getChildren().add(register);
            borderPane.setCenter(signInOptions);
            Scene scene = new Scene(borderPane);
            stage.setScene(scene);
            stage.setTitle("UnderCooked");
            stage.show();
            return stage;
        }
        return null;
    }

    public Stage signInPage(Stage stage) {
        BorderPane boardPane = new BorderPane();
        Label label = new Label("Sign-in Page");
        label.setFont(new Font("Arial", 14));
        label.setAlignment(Pos.CENTER);
        boardPane.setTop(label);
        GridPane gridPane = new GridPane();
        Label iD = new Label("Username");
        Label pwd = new Label("Password");
        TextField username = new TextField();
        TextField password = new TextField();
        gridPane.addRow(0,iD, username);
        gridPane.addRow(1, pwd, password);
        this.username = username.getText();
        this.password = password.getText();
        Login login = new Login(this.username, this.password);
        Button loginButton = new Button();
        loginButton.setText("Login");
        if (login.validLogin()){
            loginButton.setOnAction(event -> homePage(stage));
        }
        else{
            loginButton.setOnAction(event -> signInPage(stage));
        }
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(event -> indexPage(stage));
        gridPane.addRow(2,loginButton);
        gridPane.addRow(3,cancelButton);
        boardPane.setCenter(gridPane);
        Scene scene = new Scene(boardPane);
        stage.setScene(scene);
        stage.setTitle("Sign-in Page");
        stage.show();
        return stage;
    }

    public Stage registerPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        Label label = new Label("Register Page");
        label.setFont(new Font("Arial", 14));
        label.setAlignment(Pos.CENTER);
        borderPane.setTop(label);
        GridPane gridPane = new GridPane();
        Label iD = new Label("Username");
        Label pwd = new Label("Password");
        TextField username = new TextField();
        TextField password = new TextField();
        gridPane.addRow(0,iD, username);
        gridPane.addRow(1, pwd, password);
        this.username = username.getText();
        this.password = password.getText();
        Register register = new Register(this.username, this.password);
        Button registerButton = new Button();
        registerButton.setText("Register");
        if (register.validLogin()){
            registerButton.setOnAction(event -> homePage(stage));
        }
        else{
            registerButton.setOnAction(event -> registerPage(stage));
        }
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(event -> indexPage(stage));
        gridPane.addRow(2,registerButton);
        gridPane.addRow(3,cancelButton);
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Register Page");
        stage.show();
        return stage;
    }

    public Stage homePage(Stage stage){
        BorderPane pane = new BorderPane();
        // on the user intro, it should display as
        //          Recipes
        //  Welcome back [username]
        //  Account was created on [CreationDate]
        //  Last Login was on [LastLoginTime]
        VBox userIntro = new VBox();
        pane.setTop(userIntro);
        Label titleLabel = new Label("Recipes");
        String welcome = "Welcome back some username";
        Label welcomeLabel = new Label(welcome);
        String creation = "Account was created on some date";
        Label creationLabel = new Label(creation);
        String lastLogIn = "Last Login was on some date";
        Label lastLogInLabel = new Label(lastLogIn);
        userIntro.getChildren().addAll(titleLabel, welcomeLabel, creationLabel, lastLogInLabel);

        // on the user pantry page, it should display as
        //          Pantry
        // [button to add ingredient one at a time]
        // randomIngredient         2
        // somethingElse            5
        // randomThing              1
        // [button to open allIngredientsPage]
        VBox userPantry = new VBox();
        pane.setLeft(userPantry);
        Label pantryLabel = new Label("Pantry");


        // on the user recipe page, it should display as
        //          Recipes
        // [List of all recipes]
        VBox userRecipe = new VBox();
        pane.setCenter(userRecipe);


        VBox userCategories = new VBox();
        pane.setRight(userCategories);

        //Add get all ingredients button and direct it to allIngredientsPage. (It is ready to be tested)
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
        return stage;
    }

    public Stage allIngredientsPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        GetUserIngredients ingredients = new GetUserIngredients(this.username);
        if (ingredients.getIngredients().equals("")){
            System.out.println("User has no ingredients");
        }
        else{
            String allIngredients = ingredients.getIngredients();
            System.out.println(allIngredients);
        }
        Button back = new Button();
        back.setText("Return");
        gridPane.addRow(0,back);
        back.setOnAction(event -> homePage(stage));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("User Ingredients");
        stage.show();
        return stage;
    }

    public Stage searchRecipePage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        Label title = new Label("Please insert recipe name for searching");
        title.setFont(new Font("Ariel", 14));
        borderPane.setTop(title);
        Label name = new Label("Recipe Name");
        name.setFont(new Font("Ariel", 14));
        TextField recipeName = new TextField();
        gridPane.addRow(0,name,recipeName);
        SearchRecipes searchRecipes = new SearchRecipes(recipeName.getText());
        System.out.println(searchRecipes.getRecipes());
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Search Recipe By Name");
        stage.show();
        return stage;
    }
    public Stage searchRecipeByCategoryPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        Label title = new Label("PLease insert category name for searchinf");
        title.setFont(new Font("Ariel", 14));
        borderPane.setTop(title);
        Label name = new Label("Category Name");
        name.setFont(new Font("Ariel", 14));
        TextField categoryName = new TextField();
        gridPane.addRow(0,name,categoryName);
        SearchRecipesByCategory byCategory = new SearchRecipesByCategory(categoryName.getText());
        System.out.println(byCategory.getRecipes());
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Search Recipe By Category");
        stage.show();
        return stage;
    }
    public Stage searchRecipeByIngredientsPage(Stage stage) {
        return null;
    }

    public Stage makeRecipe(Stage stage) {
        return null;
    }

    

//    @Override
//    public void update() {
//
//    }

    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println("Why did you use program arguments.");
        } else {
            Application.launch(args);
        }
    }
}
