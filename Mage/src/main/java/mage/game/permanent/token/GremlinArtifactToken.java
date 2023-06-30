package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GremlinArtifactToken extends TokenImpl {

    public GremlinArtifactToken() {
        super("Gremlin Token", "0/0 red Gremlin artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GREMLIN);
        color.setRed(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    public GremlinArtifactToken(final GremlinArtifactToken token) {
        super(token);
    }

    public GremlinArtifactToken copy() {
        return new GremlinArtifactToken(this);
    }
}
