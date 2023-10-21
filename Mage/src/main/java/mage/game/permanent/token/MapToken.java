package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class MapToken extends TokenImpl {

    public MapToken() {
        super("Map Token", "Map token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.MAP);

        // TODO: implement once we know what a Map Token is.
    }

    protected MapToken(final MapToken token) {
        super(token);
    }

    public MapToken copy() {
        return new MapToken(this);
    }
}
