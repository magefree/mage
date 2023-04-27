package mage.game.permanent.token;

/**
 * Token container for copyable characteristics, don't put it to battlefield
 *
 * @author nantuko
 */
public final class EmptyToken extends TokenImpl {

    public EmptyToken() {
        this(false);
    }

    public EmptyToken(boolean withBackFace) {
        super(" Token", "");
        if (withBackFace) {
            this.backFace = new EmptyToken();
        }
    }

    public EmptyToken(final EmptyToken token) {
        super(token);
    }

    public EmptyToken copy() {
        return new EmptyToken(this);
    }
}
