package mage.game.permanent.token;

import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class ScrapToken extends TokenImpl {

    public ScrapToken() {
        super("Scrap", "colorless artifact token named Scrap");
        cardType.add(CardType.ARTIFACT);

        availableImageSetCodes.add("BRC");
    }

    public ScrapToken(final ScrapToken token) {
        super(token);
    }

    public ScrapToken copy() {
        return new ScrapToken(this);
    }
}
