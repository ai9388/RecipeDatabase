package gui;

// imports
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.LabelSkin;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import model.databaseObjects.*;
import java.util.ArrayList;


/**
 * @author Team 3: UnderCooked
 *          Nicholas Deary
 *          Benson Yan
 *          Alex Iacob
 *
 * @filename RecipeGUI.java
 *
 * File runs a javaFx application implementation of recipe database project.
 * File uses model classes to connect to given database and display data accordingly
 */
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


    /**
     * initializing username and password to null to get them later on from the user
     */
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


    /**
     * Helper button to return the user to their home page
     *
     * @return button that sends the user home
     */
    public Button backToHomeButton(){
        Button backToHome = new Button();
        backToHome.setText("<-- Return to Home Page");
        backToHome.setOnAction(e -> homePage(stage));
        return backToHome;
    }


    /**
     * Sets the stage size to an appropriate amount
     *
     * @param stage current stage
     */
    private void changeStageSize(Stage stage) {
        stage.minHeightProperty().set(600);
        stage.maxHeightProperty().set(600);
        stage.minWidthProperty().set(800);
        stage.maxWidthProperty().set(800);
    }


    /**
     * Shows the user the index page
     * Page contains two buttons that lead to the sign in and register pages
     *
     * @param stage current stage information
     * @return stage information
     */
    public Stage indexPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        if (username == null) {
            VBox signInOptions = new VBox();
            // making sign in button and giving it design
            Button signIn = new Button();
            signIn.setText("Sign In");
            signIn.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
            signIn.setTextFill(Color.web(textColor));
            signIn.setMinSize(200, 75);
            signIn.setFont(new Font("Arial", 18));
            signIn.setOnAction(event -> signInPage(stage));

            // making register button and giving it design
            Button register = new Button();
            register.setText("Register");
            register.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
            register.setTextFill(Color.web(textColor));
            register.setMinSize(200, 75);
            register.setFont(new Font("Arial", 18));
            register.setOnAction(event -> registerPage(stage));

            // adding both buttons to the page
            signInOptions.getChildren().add(signIn);
            signInOptions.getChildren().add(register);
            signInOptions.setAlignment(Pos.CENTER);
            borderPane.setCenter(signInOptions);
            borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
            Scene scene = new Scene(borderPane);

            // setting the scene for the application
            stage.setScene(scene);
            stage.setTitle("UnderCooked");
            changeStageSize(stage);
            stage.show();
            return stage;
        }
        return null;
    }


    /**
     * Shows the user the sign in page for an existing account
     * Page contains two text fields and two buttons
     * Signing in checks if the user name exists and gets the account information
     *
     * @param stage current stage information
     */
    public void signInPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        FlowPane top = new FlowPane();

        // creating the title
        Label label = new Label("Sign-in Page");
        label.setFont(new Font("Arial", 14));
        label.setAlignment(Pos.TOP_CENTER);
        top.getChildren().add(label);
        top.setAlignment(Pos.TOP_CENTER);

        // creating the buttons and adding to the gridpane
        borderPane.setTop(top);
        GridPane gridPane = new GridPane();
        Label iD = new Label("Username");
        Label pwd = new Label("Password");
        TextField username = new TextField();
        TextField password = new TextField();
        gridPane.addRow(0, iD, username);
        gridPane.addRow(1, pwd, password);

        // making login button
        Button loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        loginButton.setPrefSize(60, 15);
        loginButton.setTextFill(Color.web(textColor));
        loginButton.setAlignment(Pos.CENTER);

        //making cancel button
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        cancelButton.setPrefSize(60, 15);
        cancelButton.setTextFill(Color.web(textColor));
        cancelButton.setAlignment(Pos.CENTER);
        cancelButton.setOnAction(event -> indexPage(stage));

        // adding to grid pane
        gridPane.addRow(2, loginButton);
        gridPane.addRow(3, cancelButton);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));

        // setting action for login button to log the user in
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

        // setting the stage
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Sign-in Page");
    }


    /**
     * Shows the user the register page for a NONexisting account
     * Page contains two text field and two buttons
     * Registering first checks if there is already an existing account, if there is no
     * account with the same username, then it calls a model class to create an account
     *
     * @param stage current stage information
     * @return stage information
     */
    public Stage registerPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        FlowPane top = new FlowPane();

        // creating the title
        Label label = new Label("Register Page");
        label.setAlignment(Pos.TOP_CENTER);
        label.setFont(new Font("Arial", 14));
        top.getChildren().add(label);
        top.setAlignment(Pos.TOP_CENTER);

        // creating the buttons and adding to the gridpane
        borderPane.setTop(top);
        GridPane gridPane = new GridPane();
        Label iD = new Label("Username");
        Label pwd = new Label("Password");
        TextField username = new TextField();
        username.setPromptText("Enter Username");
        TextField password = new TextField();
        gridPane.addRow(0, iD, username);
        gridPane.addRow(1, pwd, password);

        // creating the register button and settings its action
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

        // creating cancel cutton and setting its action
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(event -> indexPage(stage));

        // adding to grid pane
        gridPane.addRow(2, registerButton);
        gridPane.addRow(3, cancelButton);
        borderPane.setCenter(gridPane);

        // setting the stage
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Register Page");
        changeStageSize(stage);
        //stage.show();
        return stage;
    }


    /**
     * Shows the user their pantry, recipes, and created categories
     * On this page, the user can also add to their pantry
     *
     * @param stage current stage information
     * @return the stage information
     */
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
        Button ingredients = new Button();
        ingredients.setBackground(new Background(new BackgroundFill(Color.web(accentColor1), new CornerRadii(1), new Insets(1))));
        ingredients.setTextFill(Color.web(textColor));
        ingredients.setText("Search by Ingredients");
        ingredients.setOnAction(event -> {
            searchRecipeByIngredientsPage(stage);
        });
        HBox searchTab = new HBox();
        searchTab.getChildren().addAll(searchBox, name, category, ingredients);
        searchTab.setAlignment(Pos.CENTER);

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

        // creating button for user to add an ingredient to their pantry
        GridPane pantryGridPane = new GridPane();
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
        pantryGridPane.addRow(0, ingredientLabel, ingredientToAdd);
        pantryGridPane.addRow(1, quantityLabel, quantityToAdd);
        pantryGridPane.addRow(2, addIngredientButton);

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

        // adding children to the vbox
        userPantry.getChildren().addAll(pantryLabel, pantryGridPane, table);

        ////////////USER RECIPE PAGE/////////////////////
        // on the user recipe page, it should display as
        //          Recipes
        // _____ [edit button]
        // _____ [delete button]
        //      [create button]
        // [table view with all user recipes]
        VBox userRecipe = new VBox();
        pane.setCenter(userRecipe);
        GridPane recipeGridPane = new GridPane();
        // creating title
        Label recipeLabel = new Label("Recipes");

        // creating edit button
        Label editLabel = new Label("Edit a recipe: ");
        TextField recipeToEdit = new TextField();
        Button editRecipeButton = new Button();
        editRecipeButton.setText("Edit");
//        editRecipeButton.setOnAction(event -> {
//            editRecipe(stage);
//        });

        // creating delete button
        Label deleteLabel = new Label("Delete a recipe: ");
        TextField recipeToDelete = new TextField();
        Button deleteRecipeButton = new Button();
        deleteRecipeButton.setText("Delete");
//        deleteRecipeButton.setOnAction(event -> {
//            deleteRecipe(stage);
//        });

        // creating create button
        Button createRecipeButton = new Button();
        createRecipeButton.setText("Create new recipe");
        createRecipeButton.setOnAction(e -> makeRecipePage(stage));

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


    /**
     * Shows the user an individual recipe
     *
     * @param stage current stage information
     * @param recipe the recipe that the user searched for
     * @return the stage information
     */
    public Stage viewIndividualRecipePage(Stage stage, Recipe recipe) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        VBox recipeInformation = new VBox();

        Label nameAndRating = new Label(recipe.getName() + " (Rating: " + recipe.getRating() + ")");
        nameAndRating.setFont(new Font("Arial", 24));
        nameAndRating.setTextFill(Color.web(accentColor1));

        Label descriptionLabel = new Label("Description: ");
        descriptionLabel.setFont(new Font("Arial", 18));
        descriptionLabel.setTextFill(Color.web(accentColor1));
        Text descriptionAndDifficulty = new Text(recipe.getDescription() + "\n\nDifficulty: " + recipe.getDifficulty());
        descriptionAndDifficulty.setWrappingWidth(300);

        Label ingredientsAndServings = new Label("Ingredients:");
        ingredientsAndServings.setFont(new Font("Arial", 18));
        ingredientsAndServings.setTextFill(Color.web(accentColor1));

        Label stepsAndCookTime = new Label("Steps:");
        stepsAndCookTime.setFont(new Font("Arial", 18));
        stepsAndCookTime.setTextFill(Color.web(accentColor1));

        Text stepsAndStuff = new Text("Cook Time: " + recipe.getCookTime() + " minutes\n\n" + recipe.getSteps());
        stepsAndStuff.setWrappingWidth(300);
        stepsAndStuff.setFont(new Font("Arial", 12));

        recipeInformation.getChildren().addAll(nameAndRating, descriptionAndDifficulty, ingredientsAndServings, stepsAndCookTime, stepsAndStuff);
        recipeInformation.setAlignment(Pos.CENTER);
        borderPane.setCenter(recipeInformation);

        // setting the stage
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        return stage;
    }


    /**
     * Shows the user a table view of all of the recipes that were found via their search
     *
     * @param stage current stage information
     * @param search the item name that is being searched
     * @return the stage information
     */
    public Stage searchRecipeByNamePage(Stage stage, String search) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        FlowPane center = new FlowPane();
        title = new Label("Search results for recipe names including: " + search);
        title.setFont(new Font("Ariel", 14));
        borderPane.setTop(title);
        Button backToHome = backToHomeButton();
        FlowPane top = new FlowPane();
        top.getChildren().addAll(backToHome, title);
        borderPane.setTop(top);

        // creating search arraylist
        SearchRecipesByName searchBoi = new SearchRecipesByName(search);
        ArrayList<Recipe> result = searchBoi.getRecipes();

        // creating table columns
        TableColumn<Recipe, String> recipeName = new TableColumn<>("Name");
        recipeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Recipe, Float> recipeRating = new TableColumn<>("Rating");
        recipeRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // creating the tableview
        TableView<Recipe> searchResultsTable = new TableView<>();
        ObservableList<Recipe> recipeObservableList = FXCollections.observableArrayList();
        recipeObservableList.addAll(result);
        searchResultsTable.setItems(recipeObservableList);
        searchResultsTable.getColumns().addAll(recipeName, recipeRating);

        // add buttons to the table
        TableColumn<Recipe, Void> buttonCol = new TableColumn<>("See Recipe");
        Callback<TableColumn<Recipe, Void>, TableCell<Recipe, Void>> cellFactory = new Callback<TableColumn<Recipe, Void>, TableCell<Recipe, Void>>() {
            @Override
            public TableCell<Recipe, Void> call(final TableColumn<Recipe, Void> param) {
                final TableCell<Recipe, Void> cell = new TableCell<Recipe, Void>() {

                    private final Button btn = new Button("Go to recipe ->");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recipe recipe = getTableView().getItems().get(getIndex());
                            viewIndividualRecipePage(stage, recipe);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        buttonCol.setCellFactory(cellFactory);
        // adding columns
        searchResultsTable.getColumns().add(buttonCol);
        searchResultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        searchResultsTable.setMinSize(650, 500);
        center.getChildren().add(searchResultsTable);
        borderPane.setCenter(center);

        // setting the stage
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Search Recipe By Name");
        changeStageSize(stage);
        //stage.show();
        return stage;
    }


    /**
     * Shows the user a table view of all of the recipes that were found via their category search
     *
     * @param stage current stage information
     * @param search the category name that is being searched
     * @return the stage information
     */
    public Stage searchRecipeByCategoryPage(Stage stage, String search) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        FlowPane center = new FlowPane();
        title = new Label("Search results for recipe names including: " + search);
        title.setFont(new Font("Ariel", 14));
        borderPane.setTop(title);
        Button backToHome = backToHomeButton();
        FlowPane top = new FlowPane();
        top.getChildren().addAll(backToHome, title);
        borderPane.setTop(top);

        // creating search arraylist
        SearchRecipesByCategory searchBoi = new SearchRecipesByCategory(search);
        ArrayList<Recipe> result = searchBoi.getRecipes();

        // creating table columns
        TableColumn<Recipe, String> recipeName = new TableColumn<>("Name");
        recipeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Recipe, Float> recipeRating = new TableColumn<>("Rating");
        recipeRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // creating the tableview
        TableView<Recipe> searchResultsTable = new TableView<>();
        ObservableList<Recipe> recipeObservableList = FXCollections.observableArrayList();
        recipeObservableList.addAll(result);
        searchResultsTable.setItems(recipeObservableList);
        searchResultsTable.getColumns().addAll(recipeName, recipeRating);

        // add buttons to the table
        TableColumn<Recipe, Void> buttonCol = new TableColumn<>("See Recipe");
        Callback<TableColumn<Recipe, Void>, TableCell<Recipe, Void>> cellFactory = new Callback<TableColumn<Recipe, Void>, TableCell<Recipe, Void>>() {
            @Override
            public TableCell<Recipe, Void> call(final TableColumn<Recipe, Void> param) {
                final TableCell<Recipe, Void> cell = new TableCell<Recipe, Void>() {

                    private final Button btn = new Button("Go to recipe ->");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recipe recipe = getTableView().getItems().get(getIndex());
                            viewIndividualRecipePage(stage, recipe);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        buttonCol.setCellFactory(cellFactory);
        // adding columns
        searchResultsTable.getColumns().add(buttonCol);
        searchResultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        searchResultsTable.setMinSize(650, 500);
        center.getChildren().add(searchResultsTable);
        borderPane.setCenter(center);

        // setting the stage
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Search Recipe By Name");
        changeStageSize(stage);
        //stage.show();
        return stage;
    }


    /**
     * Shows the user a table view of all of the recipes that were found via ingredient search
     *
     * @param stage current stage information
     * @return the stage information
     */
    public Stage searchRecipeByIngredientsPage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        FlowPane center = new FlowPane();
        title = new Label("Search results for recipes " + this.username + " can make:");
        title.setFont(new Font("Ariel", 14));
        borderPane.setTop(title);
        Button backToHome = backToHomeButton();
        FlowPane top = new FlowPane();
        top.getChildren().addAll(backToHome, title);
        borderPane.setTop(top);

        // creating search arraylist
        SearchRecipesByIngredients searchBoi = new SearchRecipesByIngredients(this.username);
        ArrayList<Recipe> result = searchBoi.getRecipes();

        // creating table columns
        TableColumn<Recipe, String> recipeName = new TableColumn<>("Name");
        recipeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Recipe, Float> recipeRating = new TableColumn<>("Rating");
        recipeRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // creating the tableview
        TableView<Recipe> searchResultsTable = new TableView<>();
        ObservableList<Recipe> recipeObservableList = FXCollections.observableArrayList();
        recipeObservableList.addAll(result);
        searchResultsTable.setItems(recipeObservableList);
        searchResultsTable.getColumns().addAll(recipeName, recipeRating);

        // add buttons to the table
        TableColumn<Recipe, Void> buttonCol = new TableColumn<>("See Recipe");
        Callback<TableColumn<Recipe, Void>, TableCell<Recipe, Void>> cellFactory = new Callback<TableColumn<Recipe, Void>, TableCell<Recipe, Void>>() {
            @Override
            public TableCell<Recipe, Void> call(final TableColumn<Recipe, Void> param) {
                final TableCell<Recipe, Void> cell = new TableCell<Recipe, Void>() {

                    private final Button btn = new Button("Go to recipe ->");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recipe recipe = getTableView().getItems().get(getIndex());
                            viewIndividualRecipePage(stage, recipe);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        buttonCol.setCellFactory(cellFactory);
        // adding columns
        searchResultsTable.getColumns().add(buttonCol);
        searchResultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        searchResultsTable.setMinSize(650, 500);
        center.getChildren().add(searchResultsTable);
        borderPane.setCenter(center);

        // setting the stage
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Search Recipe By Name");
        changeStageSize(stage);
        //stage.show();
        return stage;
    }


    /**
     * User can create a unique recipe that is then inserted into the database
     *
     * @param stage current stage information
     * @return the stage information
     */
    public Stage makeRecipePage(Stage stage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(800, 600);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), new CornerRadii(1), new Insets(1))));
        GridPane gridPane = new GridPane();

        // creating recipe name
        Label recipeNameLabel = new Label("Recipe Name:");
        TextField recipeName = new TextField();

        // creating the description
        Label descriptionLabel = new Label("Description:");
        TextArea description = new TextArea();

        // creating the servings
        Label servingsLabel = new Label("Servings:");
        TextField servings = new TextField();

        // creating the cook time
        Label cookTimeLabel = new Label("Cook Time:");
        TextField cookTime = new TextField();

        // creating the difficulty
        Label difficultyLabel = new Label("Difficulty:");
        String difficulties[] = {"Easy", "Easy-Medium", "Medium", "Medium-Hard", "Hard"};
        ComboBox difficulty = new ComboBox(FXCollections.observableArrayList(difficulties));
        difficulty.getItems().addAll();

        // creating the steps
        Label stepsLabel = new Label("Steps:");
        TextArea steps = new TextArea();

        // creating ingredients
        Label ingredients = new Label("Ingredients (Name, Quantity):");
        VBox fields = new VBox();
        for(int i = 0; i < 5; i++) {
            HBox ingredient = new HBox();
            TextField name = new TextField();
            TextField quantity = new TextField();
            ingredient.getChildren().addAll(name, quantity);
            fields.getChildren().add(ingredient);
        }
        Button add = new Button("+");
        add.setOnAction(event -> {
            int size = fields.getChildren().size();
            HBox ingredient = new HBox();
            TextField name = new TextField();
            TextField quantity = new TextField();
            ingredient.getChildren().addAll(name, quantity);
            fields.getChildren().add(size - 2, ingredient);
        });
        Button minus = new Button("-");
        minus.setOnAction(event -> {
            int size = fields.getChildren().size();
            if(size > 2) {
                fields.getChildren().remove(size - 2);
            }
        });
        HBox addAndMinus = new HBox();
        addAndMinus.getChildren().addAll(add, minus);
        fields.getChildren().add(addAndMinus);

        // adding all of the labels and text field to a grid pane
        gridPane.addRow(0,recipeNameLabel,recipeName);
        gridPane.addRow(1,descriptionLabel,description);
        gridPane.addRow(2,servingsLabel, servings);
        gridPane.addRow(3,cookTimeLabel,cookTime);
        gridPane.addRow(4, difficultyLabel, difficulty);
        gridPane.addRow(5, ingredients, fields);
        gridPane.addRow(6,stepsLabel,steps);

        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("New Recipe");
        changeStageSize(stage);
        return stage;
    }


    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println("Why did you use program arguments.");
        } else {
            Application.launch(args);
        }
    }
}
