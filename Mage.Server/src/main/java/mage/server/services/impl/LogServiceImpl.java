package mage.server.services.impl;

import mage.db.EntityManager;
import mage.server.services.LogService;
import org.apache.log4j.Logger;

import java.util.Calendar;

/**
 * @author noxx
 */
public enum LogServiceImpl implements LogService {
    instance;

    private static Logger log = Logger.getLogger(LogServiceImpl.class);

    @Override
    public void log(String key, String... args) {
        Calendar cal = Calendar.getInstance();
        try {
            EntityManager.instance.insertLog(key, cal.getTime(), args);
        } catch (Exception e) {
            log.fatal(e);
        }
    }

}
