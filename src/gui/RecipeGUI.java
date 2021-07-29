package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        changeStageSize(stage);
        indexPage(stage);
    }

    private void changeStageSize(Stage stage) {
        stage.minHeightProperty().set(600);
        stage.maxHeightProperty().set(600);
        stage.minWidthProperty().set(800);
        stage.maxWidthProperty().set(800);
    }

    public Stage indexPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        if(username == null) {
            VBox signInOptions = new VBox();
            Button signIn = new Button();
            signIn.setText("Sign In");
            signIn.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
            signIn.setTextFill(Color.web(textColor));
            signIn.setMinSize(200, 75);
            signIn.setFont(new Font("Arial", 18));
            signIn.setOnAction(event -> signInPage(stage));
            Button register = new Button();
            register.setText("Register");
            register.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
            register.setTextFill(Color.web(textColor));
            register.setMinSize(200, 75);
            register.setFont(new Font("Arial", 18));
            register.setOnAction(event -> registerPage(stage));
            signInOptions.getChildren().add(signIn);
            signInOptions.getChildren().add(register);
            signInOptions.setAlignment(Pos.CENTER);
            borderPane.setCenter(signInOptions);
            borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
            Scene scene = new Scene(borderPane);
            stage.setScene(scene);
            stage.setTitle("UnderCooked");
            changeStageSize(stage);
            stage.show();
            return stage;
        }
        return null;
    }

    public void signInPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        FlowPane top = new FlowPane();
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
        loginButton.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        loginButton.setPrefSize(60, 15);
        loginButton.setTextFill(Color.web(textColor));
        loginButton.setAlignment(Pos.CENTER);
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        cancelButton.setPrefSize(60, 15);
        cancelButton.setTextFill(Color.web(textColor));
        cancelButton.setAlignment(Pos.CENTER);
        cancelButton.setOnAction(event -> indexPage(stage));
        gridPane.addRow(2,loginButton);
        gridPane.addRow(3,cancelButton);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        loginButton.setOnAction(event -> {
            this.username = username.getText();
            this.password = password.getText();
            user = new Login(this.username, this.password);
            boolean isValidLogin = user.validLogin();
            if(!isValidLogin) {
//                username.clear();
//                password.clear();
//                error = new Label("This username is not recognized.");
//                error.setTextFill(Color.RED);
//                gridPane.addRow(4,error);
                signInPage(stage);
            }
            else     {
                homePage(stage);

            }
        });

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Sign-in Page");
        //stage.show();
//        return stage;
    }

    public Stage registerPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        FlowPane top = new FlowPane();
        Label label = new Label("Register Page");
        label.setFont(new Font("Arial", 14));
        top.getChildren().add(label);
        top.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(top);
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
        changeStageSize(stage);
        //stage.show();
        return stage;
    }

    public Stage homePage(Stage stage){
        BorderPane pane = new BorderPane();
        pane.setMinSize(800, 600);
        // on the user intro, it should display as
        //          Recipes
        //  Welcome back [username]
        //  Account was created on [CreationDate]
        //  Last Login was on [LastLoginTime]
        VBox userIntro = new VBox();
        pane.setTop(userIntro);
        // creating title
        Label titleLabel = new Label("Recipes");
        String welcome = "Welcome back some username";
        Label welcomeLabel = new Label(welcome);
        String creation = "Account was created on some date";
        Label creationLabel = new Label(creation);
        String lastLogIn = "Last Login was on some date";
        Label lastLogInLabel = new Label(lastLogIn);
        // adding everything to the vbox
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
        // creating title
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
        changeStageSize(stage);
        //stage.show();
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
        changeStageSize(stage);
        //stage.show();
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
        changeStageSize(stage);
        //stage.show();
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
        changeStageSize(stage);
        //stage.show();
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
