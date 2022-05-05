
package mage.game.permanent.token;

/**
 * @author nantuko
 */
public final class EmptyToken extends TokenImpl {

    public EmptyToken() {
        super(" Token", "");
    }

    public EmptyToken(final EmptyToken token) {
        super(token);
    }

    public EmptyToken copy() {
        return new EmptyToken(this);
    }
}
