# RecipeDatabase
Authors:
* Nicholas Deary
* Alex Iacob
* Benson Yan
## Description
This project was created using PostgreSQL and DataGrip for the back-end database, Java to create classes
that would manipulate the data, and JavaFX to create a front-end display. The data that we used came from
[this database](https://www.kaggle.com/shuyangli94/food-com-recipes-and-user-interactions) from kaggle.com.

In order to run this project, run to ```src/gui/RecipeGUI.java```, and you can register or login.
From here, you can view your pantry, view your recipes, and view your recipe categories.

Inside your pantry, you can add any quantity of ingredients that are in the database.

Inside your recipe page, you can create, edit, delete, and make any quantity of recipes. Creating a 
recipe will remove the necessary ingredients from your pantry.

Inside your category page, you can view any recipe that belongs to any created category.