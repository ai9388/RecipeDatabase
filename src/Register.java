import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Register {
    private final String username;
    private final String password;

    public Register(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validLogin() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection("jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32001f", "p32001f", "eeje5EiRoo9atha3ooLo");
            Statement exists = db.createStatement();
            String existenceCheck = "SELECT username FROM chefs WHERE username='" + username + "'";
            ResultSet existanceTable = exists.executeQuery(existenceCheck);
            if(existanceTable.next()) {
                db.close();
                return false;
            }
            Statement stmt = db.createStatement();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateAndTime = dtf.format(now);
            dateAndTime = dateAndTime.substring(0, 10) + "', '" + dateAndTime.substring(11, 19);
            String selectUsers = "INSERT INTO chefs(username, password, creation_date, creation_time, last_access_date, last_access_time) " +
                    "VALUES ('" + username + "', '" + password + "', '" + dateAndTime + "', '" + dateAndTime + "');";
            System.out.println(selectUsers);
            stmt.executeQuery(selectUsers);
            db.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Start Adding chef..." + args.length);
        if(args.length == 2) {
            System.out.println("username: " + args[0]);
            System.out.println("password: " + args[1]);
            Register register = new Register(args[0], args[1]);
            System.out.println(register.validLogin());
        }
        System.out.println("Register sequence complete.");
    }
}
