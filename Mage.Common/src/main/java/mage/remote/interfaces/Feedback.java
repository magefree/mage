
package mage.remote.interfaces;

/**
 * @author noxx
 */
public interface Feedback {
    boolean sendFeedback(String title, String type, String message, String email);
}
