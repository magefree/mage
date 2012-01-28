package mage.db;

import mage.db.model.Feedback;
import mage.db.model.Log;

import java.util.Date;
import java.util.List;

/**
 *  Storage interface for saving and fetching entities.
 *  @author noxx
 */
public interface Storage {
    void insertLog(String key, Date date, String... args) throws Exception;
    List<Log> getAllLogs();
    void insertFeedback(String username, String title, String type, String message, String email, String host, java.util.Date created) throws Exception;
    List<Feedback> getAllFeedbacks();
}
