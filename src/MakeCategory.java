import java.sql.*;

public class MakeCategory {
    private final String categoryName;

    public MakeCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean createCategory() {
        try {
            //connect to database
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();

            //insert data into database
            String selectCategory = "INSERT INTO recipe_category(category_name) VALUES ('" + categoryName + "')";

            System.out.println(selectCategory);
            stmt.executeUpdate(selectCategory);
            db.close();
            return true;
        }
        catch(Exception e){
            System.err.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("creating new category");
        if(args.length == 1){
            System.out.println("Category Name: " + args[0]);
            MakeCategory newCategory = new MakeCategory(args[0]);
            if (newCategory.createCategory()){
                System.out.println("Category is created.");
            }
        }
    }
}
