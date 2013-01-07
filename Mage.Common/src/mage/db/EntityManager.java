package mage.db;

import mage.db.model.Feedback;
import mage.db.model.Log;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author noxx
 */
public enum EntityManager implements Storage {

    instance;

    private static final Logger log = Logger.getLogger(EntityManager.class);

    private static final String MAGE_JDBC_URL = "jdbc:sqlite:db/mage.db";

    private static final String MAGE_JDBC_URL_FEEDBACK_DB = "jdbc:sqlite:db/feedback.db";

    private static String QUERY_SAVE_LOG = "insert into logs values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static String QUERY_GET_ALL_LOGS = "select * from logs";

    private static String QUERY_SAVE_FEEDBACK = "insert into feedbacks values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static String QUERY_GET_ALL_FEEDBACKS = "select * from feedbacks";

    static {
        try {
            init();
            initFeedbackDB();
        } catch (Exception e) {
            log.fatal(e);
            e.printStackTrace();
        }
    }

    public static EntityManager getInstance() {
        return instance;
    }

    /**
     * Inserts log entry to DB.
     *
     * @param key
     * @param date
     * @param args
     * @throws Exception
     */
    public void insertLog(String key, java.util.Date date, String... args) throws SQLException {
        Connection conn = DriverManager.getConnection(MAGE_JDBC_URL);

        try {
            PreparedStatement prep = conn.prepareStatement(QUERY_SAVE_LOG);

            prep.setString(1, key);
            prep.setDate(2, new java.sql.Date(date.getTime()));

            int index = 3;
            for (String arg : args) {
                if (index > 8) break;
                prep.setString(index++, arg);
            }

            prep.execute();

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                // swallow
            }
        }
    }

    /**
     * Get all logs
     * @return
     */
    @Override
    public List<Log> getAllLogs() {
        List<Log> logs = new ArrayList<Log>();

        try {
            Connection conn = DriverManager.getConnection(MAGE_JDBC_URL);
            try {
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(QUERY_GET_ALL_LOGS);
                while (rs.next()) {
                    Log log = new Log(rs.getString(1), rs.getDate(2));
                    List<String> args = new ArrayList<String>();
                    for (int index = 0; index < 6; index++) {
                        String arg = rs.getString(3 + index);
                        if (arg == null) {
                            break;
                        }
                        args.add(arg);
                    }
                    log.setArguments(args);
                    logs.add(log);
                }
                rs.close();
            } finally {
                try {
                    if (conn != null) conn.close();
                } catch (Exception e) {
                    // swallow
                }
            }
        } catch (SQLException e) {
            log.fatal("SQL Exception: ", e);
        }

        return logs;
    }

    /**
     * Inserts feedback entry to DB.
     *
     *
     * @param username
     * @param title
     * @param type
     * @param message
     * @param email
     * @param host
     * @param created
     * @throws SQLException
     */
    public void insertFeedback(String username, String title, String type, String message, String email, String host, java.util.Date created) throws SQLException {
        Connection conn = DriverManager.getConnection(MAGE_JDBC_URL_FEEDBACK_DB);

        try {
            PreparedStatement prep = conn.prepareStatement(QUERY_SAVE_FEEDBACK);

            prep.setString(1, username);
            prep.setString(2, title);
            prep.setString(3, type);
            prep.setString(4, message);
            prep.setString(5, email);
            prep.setString(6, host);
            prep.setDate(7, new java.sql.Date(created.getTime()));
            prep.setString(8, "new");

            prep.execute();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                // swallow
            }
        }
    }

    /**
     * Get all feedbacks
     * @return
     */
    @Override
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<Feedback>();

        try {
            Connection conn = DriverManager.getConnection(MAGE_JDBC_URL_FEEDBACK_DB);
            try {
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(QUERY_GET_ALL_FEEDBACKS);
                while (rs.next()) {
                    Feedback feedback = new Feedback();

                    feedback.setUsername(rs.getString(1));
                    feedback.setTitle(rs.getString(2));
                    feedback.setType(rs.getString(3));
                    feedback.setMessage(rs.getString(4));
                    feedback.setEmail(rs.getString(5));
                    feedback.setHost(rs.getString(6));
                    feedback.setCreatedDate(rs.getDate(7));
                    feedback.setStatus(rs.getString(8));

                    feedbacks.add(feedback);
                }
                rs.close();
            } finally {
                try {
                    if (conn != null) conn.close();
                } catch (Exception e) {
                    // swallow
                }
            }
        } catch (SQLException e) {
            log.fatal("SQL Exception: ", e);
        }

        return feedbacks;
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
            stat.executeUpdate("create table if not exists logs (key, created_dt, arg0, arg1, arg2, arg3, arg4, arg5);");
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                // swallow
            }
        }
    }

    protected static void initFeedbackDB() throws Exception {
        Class.forName("org.sqlite.JDBC");
        checkDBFolderExistance();
        Connection conn = DriverManager.getConnection(MAGE_JDBC_URL_FEEDBACK_DB);
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate("create table if not exists feedbacks (username, title, type, message, email, host, created_dt, status);");
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
            init();
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
                if (conn != null) conn.close();
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
    }

    public static void main(String[] args) throws Exception {
        //EntityManager.getInstance().reinit();
        EntityManager.getInstance().testDB();
    }
}
