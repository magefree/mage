package mage.db;

import java.sql.*;

/**
 * @author noxx
 */
public class EntityManager {

    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:mage.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists users;");
        stat.executeUpdate("create table users (login, password);");

        PreparedStatement prep = conn.prepareStatement("insert into users values (?, ?);");

        prep.setString(1, "TestUser");
        prep.setString(2, "123");
        prep.execute();

        prep.setString(1, "TestUser2");
        prep.setString(2, "12345");
        prep.execute();

        ResultSet rs = stat.executeQuery("select * from users;");
        while (rs.next()) {
            System.out.println("user = " + rs.getString("login"));
            System.out.println("password = " + rs.getString("password"));
        }
        rs.close();
        conn.close();
    }
}
