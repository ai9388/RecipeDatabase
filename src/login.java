import java.sql.*;

/**
 * Back end implementation for login. Includes SQl.
 */
public class login {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        try {
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT recipe_name FROM recipe");
            while (rs.next()) {
                System.out.println(rs.getString("recipe_name"));
            }
            db.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
