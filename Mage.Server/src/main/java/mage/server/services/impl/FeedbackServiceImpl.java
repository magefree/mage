package mage.server.services.impl;

import mage.db.EntityManager;
import mage.server.services.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * @author noxx
 */
public enum FeedbackServiceImpl implements FeedbackService {
    instance;

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Override
    public void feedback(String username, String title, String type, String message, String email, String host) {
        Calendar cal = Calendar.getInstance();
        try {
            EntityManager.instance.insertFeedback(username, title, type, message, email, host, cal.getTime());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }
}
