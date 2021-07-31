package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.*;
import model.databaseObjects.*;

import java.util.ArrayList;


public class RecipeGUI extends Application {
    private Stage stage;
    private String username;
    private String password;
    private Login user;
    private Label error;
    private Label title;
    private final String backgroundColor = "#e8e8e8";
    private final String accentColor1 = "#277582";
    private final String accentColor2 = "#277582";
    private final String textColor = "#e8e8e8";

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
        if (username == null) {
            VBox signInOptions = new VBox();
            Button signIn = new Button();
            signIn.setText("Sign In");
            signIn.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
            signIn.setTextFill(Color.web(textColor));
            signIn.setMinSize(200, 75);
            signIn.setFont(new Font("Arial", 18));
            signIn.setOnAction(event -> {
                signInPage(stage);
            });
            Button register = new Button();
            register.setText("Register");
            register.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
            register.setTextFill(Color.web(textColor));
            register.setMinSize(200, 75);
            register.setFont(new Font("Arial", 18));
            register.setOnAction(event -> {
                registerPage(stage);
            });
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
        label.setAlignment(Pos.TOP_CENTER);
        top.getChildren().add(label);
        top.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(top);
        GridPane gridPane = new GridPane();
        Label iD = new Label("Username");
        Label pwd = new Label("Password");
        TextField username = new TextField();
        TextField password = new TextField();
        gridPane.addRow(0, iD, username);
        gridPane.addRow(1, pwd, password);

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
        cancelButton.setOnAction(event -> {
            indexPage(stage);
        });
        gridPane.addRow(2, loginButton);
        gridPane.addRow(3, cancelButton);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        loginButton.setOnAction(event -> {
            this.username = username.getText();
            this.password = password.getText();
            user = new Login(this.username, this.password);
            boolean isValidLogin = user.validLogin();
            if (!isValidLogin) {
//                username.clear();
//                password.clear();
//                error = new Label("This username is not recognized.");
//                error.setTextFill(Color.RED);
//                gridPane.addRow(4,error);
                signInPage(stage);
            } else {
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
        label.setAlignment(Pos.TOP_CENTER);
        label.setFont(new Font("Arial", 14));
        top.getChildren().add(label);
        top.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(top);
        GridPane gridPane = new GridPane();
        Label iD = new Label("Username");
        Label pwd = new Label("Password");
        TextField username = new TextField();
        username.setPromptText("Enter Username");
        TextField password = new TextField();
        gridPane.addRow(0, iD, username);
        gridPane.addRow(1, pwd, password);

        Button registerButton = new Button();
        registerButton.setText("Register");
        registerButton.setOnAction(event -> {
            this.username = username.getText();
            this.password = password.getText();
            Register register = new Register(this.username, this.password);
            boolean isValidRegister = register.validLogin();
            if (!isValidRegister) {
                registerPage(stage);
//                error = new Label("This username is taken.");
//                error.setTextFill(Color.RED);
//                gridPane.addRow(4,error);
            } else {
                homePage(stage);
            }
        });

        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(event -> {
            indexPage(stage);
        });
        gridPane.addRow(2, registerButton);
        gridPane.addRow(3, cancelButton);
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Register Page");
        changeStageSize(stage);
        //stage.show();
        return stage;
    }

    public Stage homePage(Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setMinSize(800, 600);
        pane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));

        ////////////USER INTRO PAGE/////////////////////
        // on the user intro, it should display as
        //          Recipes
        //  Welcome back [username]
        //  Account was created on [CreationDate]
        //  Last Login was on [LastLoginTime]
        VBox userIntro = new VBox();
        pane.setTop(userIntro);
        // creating title
        Label titleLabel = new Label("UnderCooked");
        titleLabel.setTextFill(Color.web(accentColor2));
        titleLabel.setFont(new Font("Ariel", 18));

        // creating welcome statement
        String welcome = "Welcome back " + this.username;
        Label welcomeLabel = new Label(welcome);
        welcomeLabel.setTextFill(Color.web(accentColor1));

        // creating the account creation time and date
        String creationDateAndTime = user.getCreationDate() + " at " + user.getCreationTime();
        String creation = "Account was created on " + creationDateAndTime;
        Label creationLabel = new Label(creation);
        creationLabel.setTextFill(Color.web(accentColor1));

        // creating the last accessed time for the user
        String lastAccessDateAndTime = user.getLastAccessDate() + " at " + user.getLastAccessTime();
        String lastLogIn = "Last Login was on " + lastAccessDateAndTime;
        Label lastLogInLabel = new Label(lastLogIn);
        lastLogInLabel.setTextFill(Color.web(accentColor1));

        // search options
        Label searchLabel = new Label();
        searchLabel.setText("Search for Recipes:");
        TextField searchBox = new TextField();
        Button name = new Button();
        name.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        name.setTextFill(Color.web(textColor));
        name.setText("Search by Name");
        name.setOnAction(event -> {
            String search = searchBox.getText();
            searchRecipeByNamePage(stage, search);
        });
        Button category = new Button();
        category.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        category.setTextFill(Color.web(textColor));
        category.setText("Search by Category");
        category.setOnAction(event -> {
            String search = searchBox.getText();
            searchRecipeByCategoryPage(stage, search);
        });
        HBox searchTab = new HBox();
        searchTab.getChildren().addAll(searchBox, name, category);
        searchTab.setAlignment(Pos.TOP_RIGHT);

        // adding everything to the vbox
        userIntro.getChildren().addAll(titleLabel, welcomeLabel, creationLabel, lastLogInLabel);
        userIntro.setAlignment(Pos.CENTER);
        FlowPane topPart = new FlowPane();
        topPart.getChildren().addAll(userIntro, searchTab);
        topPart.setAlignment(Pos.CENTER);
        pane.setTop(topPart);

        ////////////USER PANTRY PAGE/////////////////////
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

        // creating button for user to view all ingredients
        Button showAllIngredient = new Button();
        showAllIngredient.setText("View all ingredients");
        showAllIngredient.setOnAction(event -> {
            allIngredientsPage(stage);
        });

        // creating button for user to add an ingredient to their pantry
        GridPane gp = new GridPane();
        Label ingredientLabel = new Label("Ingredient: ");
        Label quantityLabel = new Label("Quantity: ");
        TextField ingredientToAdd = new TextField();
        TextField quantityToAdd = new TextField();
        Button addIngredientButton = new Button();
        addIngredientButton.setText("Add");
        addIngredientButton.setOnAction(event -> {
            int quantity = Integer.parseInt(quantityToAdd.getText());
            AddIngredients addIngred = new AddIngredients(username, ingredientToAdd.getText(), quantity);
            addIngred.addIngredient();
            homePage(stage);
        });
        gp.addRow(0, ingredientLabel, ingredientToAdd);
        gp.addRow(1, quantityLabel, quantityToAdd);
        gp.addRow(2, addIngredientButton);

        // creating table to hold values
        TableView<Ingredient> table = new TableView<Ingredient>();

        // creating the first column to hold ingredient name
        TableColumn<Ingredient, String> ingredientNameColumn = new TableColumn<Ingredient, String>("Name");
        ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));

        // creating the second column to hold the ingredient quantity
        TableColumn<Ingredient, Integer> ingredientQuantityColumn = new TableColumn<Ingredient, Integer>("Quantity");
        ingredientQuantityColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("quantity"));

        // adding the columns to the table view
        table.getColumns().add(ingredientNameColumn);
        table.getColumns().add(ingredientQuantityColumn);

        // adjusting column size to look better
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // add all ingredients and their quantity to the table
        GetUserIngredients userIngredients = new GetUserIngredients(username);

        for (int i = 0; i < userIngredients.getIngredients().size(); i++) {
            table.getItems().add(userIngredients.getIngredients().get(i));
        }

        userPantry.getChildren().addAll(pantryLabel, showAllIngredient, gp, table);

        ////////////USER RECIPE PAGE/////////////////////
        // on the user recipe page, it should display as
        //          Recipes
        // [Create new recipe]
        // [some recipe] [Edit] [Delete]
        VBox userRecipe = new VBox();
        pane.setCenter(userRecipe);
        // creating title
        Label recipeLabel = new Label("Recipes");
        // creating button for user to create new recipe
        Button createRecipeButton = new Button();
        createRecipeButton.setText("Create new recipe");

        // adding to grid pane
        recipeGridPane.addRow(0, editLabel, recipeToEdit, editRecipeButton);
        recipeGridPane.addRow(1, deleteLabel, recipeToDelete, deleteRecipeButton);
        recipeGridPane.addRow(2, createRecipeButton);

        // creating table to hold values
        TableView<Recipe> userRecipeTable = new TableView<Recipe>();

        // creating the individual column
        TableColumn<Recipe, String> recipeNameColumn = new TableColumn<Recipe, String>("Recipe Name");
        recipeNameColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));

        // adding the columns to the table
        userRecipeTable.getColumns().add(recipeNameColumn);

        // making the table look nicer
        userRecipeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // filling in the table
        SearchRecipesByIngredients recipesByIngredients = new SearchRecipesByIngredients(username);

//        for (int i = 0; i < recipesByIngredients.getRecipes().size(); i++) {
//            userRecipeTable.getItems().add(recipesByIngredients.getRecipes().get(i));
//        }

        // adding to the vbox
        userRecipe.getChildren().addAll(recipeLabel, recipeGridPane, userRecipeTable);

        ////////////USER CATEGORY PAGE/////////////////////
        // showing all the recipes

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
        if (ingredients.getIngredients().equals("")) {
            System.out.println("User has no ingredients");
        } else {
            ArrayList<Ingredient> allIngredients = ingredients.getIngredients();
            System.out.println(allIngredients);
        }
        Button back = new Button();
        back.setText("Return");
        gridPane.addRow(0, back);
        back.setOnAction(event -> {
            homePage(stage);
        });
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("User Ingredients");
        changeStageSize(stage);
        //stage.show();
        return stage;
    }

    public Stage searchRecipeByNamePage(Stage stage, String search) {
        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        GridPane gridPane = new GridPane();
        title = new Label("Please insert recipe name for searching");
        title.setFont(new Font("Ariel", 14));
        borderPane.setTop(title);
        Label name = new Label("Recipe Name");
        name.setFont(new Font("Ariel", 14));
        TextField searchQuery = new TextField();
        gridPane.addRow(0, name, searchQuery);
        Button executeSearch = new Button();
        executeSearch.setText("Search");
        executeSearch.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        executeSearch.setTextFill(Color.web(textColor));
        executeSearch.setOnAction(event -> {
            SearchRecipesByName searchRecipesByName = new SearchRecipesByName(searchQuery.getText());
            ArrayList<Recipe> result = searchRecipesByName.getRecipes();
        });
        //// ADD TABLE THING HERE

        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Search Recipe By Name");
        changeStageSize(stage);
        //stage.show();
        return stage;
    }

    public Stage searchRecipeByCategoryPage(Stage stage, String search) {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        title = new Label("PLease insert category name for searching");
        title.setFont(new Font("Ariel", 14));
        borderPane.setTop(title);
        Label name = new Label("Category Name");
        name.setFont(new Font("Ariel", 14));
        TextField categoryName = new TextField();
        gridPane.addRow(0, name, categoryName);
        Button executeSearch = new Button();
        executeSearch.setText("Search");
        executeSearch.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        executeSearch.setTextFill(Color.web(textColor));
        executeSearch.setOnAction(event -> {
            SearchRecipesByCategory byCategory = new SearchRecipesByCategory(categoryName.getText());
            System.out.println(byCategory.getRecipes());
        });
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Search Recipe By Category");
        changeStageSize(stage);
        //stage.show();
        return stage;
    }

    public Stage searchRecipeByIngredientsPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        title = new Label("Recipe(s) available according to your existing ingredient(s)");
        title.setFont(new Font("Ariel", 14));
        borderPane.setTop(title);
        // creating table to hold values
        TableView<Recipe> recipeTable = new TableView<Recipe>();

        // creating the individual column
        TableColumn<Recipe, String> recipeNameColumn = new TableColumn<Recipe, String>("Recipe Name");
        recipeNameColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));

        // making the table look nicer
        recipeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // adding
        SearchRecipesByIngredients recipesByIngredients = new SearchRecipesByIngredients(username);

        for (int i = 0; i < recipesByIngredients.getRecipes().size(); i++) {
            recipeTable.getItems().add(recipesByIngredients.getRecipes().get(i));
        }

        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Search Recipe By Ingredients");
        changeStageSize(stage);
        return stage;
    }

    public Stage makeRecipe(Stage stage) {
        return null;
    }

    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println("Why did you use program arguments.");
        } else {
            Application.launch(args);
        }
    }
}
