import java.sql.*;

/**
 * Back end implementation for login. Includes SQl.
 */
public class Login {
    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validLogin() {
        try {
            boolean result = false;
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement stmt = db.createStatement();
            String selectUsers = "SELECT username, password FROM chefs WHERE username=\'" + username + "\'";
            ResultSet rs;
            rs = stmt.executeQuery(selectUsers);
            if(rs.next()) {
                if(rs.getString("username").equals(username)) {

                    result = rs.getString("password").equals(password);
                }
            }

            db.close();
            return result;
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Start Login Validation..." + args.length);
        System.out.println("username: " + args[0]);
        System.out.println("password: " + args[1]);
        if(args.length == 2) {
            Login login = new Login(args[0], args[1]);
            System.out.println(login.validLogin());
        }
        System.out.println("Login sequence complete.");
    }
}
