package mage.db;

import org.apache.log4j.Logger;

import java.io.File;
import java.sql.*;

/**
 * @author noxx
 */
public class EntityManager {

    private static final Logger log = Logger.getLogger(EntityManager.class);

    private static final EntityManager instance;

    private static final String MAGE_JDBC_URL = "jdbc:sqlite:db/mage.db";

    static {
        instance = new EntityManager();
        try {
            init();
        } catch (Exception e) {
            log.fatal(e);
            e.printStackTrace();
        }
    }

    public static EntityManager getInstance() {
        return instance;
    }

    /**
     * Inits database. Creates tables if they don't exist.
     *
     * @throws Exception
     */
    protected static void init() throws Exception {
        Class.forName("org.sqlite.JDBC");
        checkDBFolderExistance();
        Connection conn = DriverManager.getConnection(MAGE_JDBC_URL);
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate("create table if not exists users (login, password, status);");
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                // swallow
            }
        }
    }

    /**
     * Reinits database. Drops all tables and then creates them from scratch.
     * BE CAREFUL! THIS METHOD WILL DESTROY ALL DATA.
     *
     * @throws Exception
     */
    public static void reinit() throws Exception {
        Class.forName("org.sqlite.JDBC");
        checkDBFolderExistance();
        Connection conn = DriverManager.getConnection(MAGE_JDBC_URL);
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate("drop table users;");
            stat.executeUpdate("create table users (login, password, status);");
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                // swallow
            }
        }
    }

    /**
     * Creates folders for Mage sqlite db.
     */
    protected static void checkDBFolderExistance() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * Tests DB with base operations.
     *
     * @throws Exception
     */
    public void testDB() throws Exception {

        Connection conn = DriverManager.getConnection(MAGE_JDBC_URL);

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select * from users where login = 'testtest';");

            if (rs.next()) {
                checkTestUser(conn, stat);
            } else {
                log.debug("[DBTest] creating test user...");
                PreparedStatement prep = conn.prepareStatement("insert into users values (?, ?, ?);");

                prep.setString(1, "testtest");
                prep.setString(2, "12345");
                prep.setString(3, "disabled");

                prep.execute();

                log.debug("[DBTest] creating test user [OK]");

                checkTestUser(conn, stat);
            }

            rs.close();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                // swallow
            }
        }
    }

    /**
     * Checks test user existence and its parameters.
     *
     * @param conn
     * @param stat
     * @throws Exception
     */
    private void checkTestUser(Connection conn, Statement stat) throws Exception {
        ResultSet rs = stat.executeQuery("select * from users where login = 'testtest';");
        if (rs.next()) {
            log.debug("[DBTest] checking test user [OK]");
            if (rs.getString("login").equals("testtest") && rs.getString("password").equals("12345") && rs.getString("status").equals("disabled")) {
                log.debug("[DBTest] checking test user parameters [OK]");
            } else {
                log.debug("[DBTest] checking test user parameters [ERROR]");
            }
        } else {
            log.debug("[DBTest] checking test user [ERROR]");
            log.debug("[DBTest] couldn't find test user");
        }

        while (rs.next()) {
            System.out.println("user = " + rs.getString("login"));
            System.out.println("password = " + rs.getString("password"));
        }
    }

    public static void main(String[] args) throws Exception {
        EntityManager.getInstance().testDB();
    }
}
