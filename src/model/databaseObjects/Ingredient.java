package model.databaseObjects;

/**
 * Helper class to allow the table view to store information easier
 *
 * Class represents a type of ingredient that a user can hold in their pantry with its quantity
 */
public class Ingredient {
    private final String name;
    private int quantity;

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void addIngredientQuantity(int amount) {
        this.quantity += amount;
    }

    public void removeIngredientQuantity(int amount) {
        this.quantity -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass().equals(Ingredient.class)) {
            return ((Ingredient) o).name.equals(name) && ((Ingredient) o).quantity == quantity;
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
