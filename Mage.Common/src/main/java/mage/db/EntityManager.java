package mage.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import mage.cards.repository.DatabaseUtils;
import mage.db.model.Feedback;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author noxx, North
 */
public enum EntityManager {

    instance;

    private Dao<Feedback, Object> feedbackDao;

    EntityManager() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource feedbackConnectionSource = new JdbcConnectionSource(DatabaseUtils.prepareH2Connection(DatabaseUtils.DB_NAME_FEEDBACK, false));
            TableUtils.createTableIfNotExists(feedbackConnectionSource, Feedback.class);
            feedbackDao = DaoManager.createDao(feedbackConnectionSource, Feedback.class);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
