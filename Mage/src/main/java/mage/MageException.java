

package mage;

/**
 * Root application exception.
 * 
 * @author BetaSteward_at_googlemail.com
 */
public class MageException extends Exception {

    private static final long serialVersionUID = 8340806803178193696L;

    public MageException(String message) {
        super(message);
    }

    public MageException(Throwable t) {
        super(t);
    }
}
