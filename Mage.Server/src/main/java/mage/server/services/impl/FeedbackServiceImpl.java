package mage.server.services.impl;

import mage.db.EntityManager;
import mage.server.services.FeedbackService;
import org.apache.log4j.Logger;

import java.util.Calendar;

/**
 * @author noxx
 */
public enum FeedbackServiceImpl implements FeedbackService {
    instance;

    private static Logger log = Logger.getLogger(FeedbackServiceImpl.class);

    @Override
    public void feedback(String username, String title, String type, String message, String email, String host) {
        Calendar cal = Calendar.getInstance();
        try {
            EntityManager.instance.insertFeedback(username, title, type, message, email, host, cal.getTime());
        } catch (Exception e) {
            log.fatal(e);
        }
    }
}
