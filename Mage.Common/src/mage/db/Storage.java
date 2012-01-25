package mage.db;

import mage.db.model.Log;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface Storage {
    void insertLog(String key, Date date, String... args) throws Exception;
    List<Log> getAllLogs();
}
