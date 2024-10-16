import views.Welcome;

import java.sql.SQLException;

public class Check {
    public static void main(String[] args) throws SQLException {
        Welcome w = new Welcome();
        do{
            w.welcomeScreen();
        }while (true);

    }
}
