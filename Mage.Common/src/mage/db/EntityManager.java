package mage.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import mage.db.model.Feedback;
import mage.db.model.Log;
import mage.utils.properties.PropertiesUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author noxx, North
 */
public enum EntityManager {

    instance;

    private Dao<Log, Object> logDao;
    private Dao<Feedback, Object> feedbackDao;

    private EntityManager() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource logConnectionSource = new JdbcConnectionSource(PropertiesUtil.getDBLogUrl());
            TableUtils.createTableIfNotExists(logConnectionSource, Log.class);
            logDao = DaoManager.createDao(logConnectionSource, Log.class);

            ConnectionSource feedbackConnectionSource = new JdbcConnectionSource(PropertiesUtil.getDBFeedbackUrl());
            TableUtils.createTableIfNotExists(feedbackConnectionSource, Feedback.class);
            feedbackDao = DaoManager.createDao(feedbackConnectionSource, Feedback.class);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertLog(String key, java.util.Date date, String... args) throws SQLException {
        Log logEntity = new Log(key, date);
        logEntity.setArguments(args);
        logDao.create(logEntity);
    }

    public List<Log> getAllLogs() {
        List<Log> logs = new ArrayList<>();
        try {
            logs = logDao.queryForAll();
        } catch (SQLException ex) {
        }

        return logs;
    }

    public void insertFeedback(String username, String title, String type, String message, String email, String host, java.util.Date created) throws SQLException {
        Feedback feedback = new Feedback(username, title, type, message, email, host, created, "new");
        feedbackDao.create(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            feedbacks = feedbackDao.queryForAll();
        } catch (SQLException ex) {
        }
        return feedbacks;
    }
}
