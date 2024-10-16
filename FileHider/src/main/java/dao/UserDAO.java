package dao;

import db.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Period;

public class UserDAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection = MyConnection.getConn();
         String sql="select email from users";
        PreparedStatement ps= connection.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            String usremail = rs.getString(1);
            if(usremail.equals(email)){
                return  true;
            }
        }

        return false;
    }

    public static int saveUser(User user) throws SQLException{
        Connection connection = MyConnection.getConn();
        String sql="Insert into users values(default,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,user.getName());
        ps.setString(2, user.getEmail());
        return ps.executeUpdate();

    }


}


