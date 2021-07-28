package model;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Back end implementation for login. Includes SQl.
 */
public class Login {
    private final String username;
    private final String password;

    private String lastAccessDate;
    private String lastAccessTime;
    private String creationDate;
    private String creationTime;

    public String getLastAccessDate() {
        return this.lastAccessDate;
    }

    public String getLastAccessTime() {
        return this.lastAccessTime;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public String getCreationTime() {
        return this.creationTime;
    }

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
            if (rs.next()) {
                if (rs.getString("username").equals(username)) {

                    result = rs.getString("password").equals(password);
                }
            }
            if (result) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String dateAndTime = dtf.format(now);
                String date = dateAndTime.substring(0, 10);
                String time = dateAndTime.substring(11, 19);

                String updateAccessDate = "UPDATE chefs SET last_access_date= '" + date + "' WHERE username = '" + username + "'";
                String updateAccessTime = "UPDATE chefs SET last_access_time='" + time + "' WHERE username='" + username + "'";

                stmt.executeUpdate(updateAccessDate);
                stmt.executeUpdate(updateAccessTime);

                String accessDate = "SELECT chefs SET last_access_date= '" + date + "' WHERE username = '" + username + "'";
                String accessTime = "SELECT chefs SET last_access_time='" + time + "' WHERE username='" + username + "'";
                String initCreationDate = "SELECT chefs SET creation_date= '" + date + "' WHERE username = '" + username + "'";
                String initCreationTime = "SELECT chefs SET creation_time='" + time + "' WHERE username='" + username + "'";

                ResultSet temp1 = stmt.executeQuery(accessDate);
                lastAccessDate = temp1.getString("last_access_date");

                ResultSet temp2 = stmt.executeQuery(accessTime);
                lastAccessTime = temp2.getString("last_access_time");

                ResultSet temp3 = stmt.executeQuery(initCreationDate);
                creationDate = temp3.getString("creation_date");

                ResultSet temp4 = stmt.executeQuery(initCreationTime);
                creationTime = temp4.getString("creation_time");

                System.out.println("Access date and time updated...");
            }
            db.close();
            return result;
        } catch (Exception e) {
            System.out.println("Error loading postgres driver.");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Start model.Login Validation...");
        System.out.println("username: " + args[0]);
        System.out.println("password: " + args[1]);
        if (args.length == 2) {
            Login login = new Login(args[0], args[1]);
            System.out.println(login.validLogin());
        }
        System.out.println("model.Login sequence complete.");
        // make random file and give it a random value
        FileWriter writer = new FileWriter("output.txt");
        writer.write("This is a test!random garbage gibberishasd;fklgjbhl;akhjnsgd;hlasgdnkl;jnsdga\n");
        writer.flush();
        writer.close();
    }
}
