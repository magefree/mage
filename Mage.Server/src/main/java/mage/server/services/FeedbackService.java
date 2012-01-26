package mage.server.services;

/**
 * Responsible for gathering feedback from users and storing them in DB.
 *
 * @author noxx
 */
public interface FeedbackService {

    /**
     * Saves feedback.
     */
    void feedback(String username, String title, String type, String message, String email, String host);
}
