
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class GremlinToken extends TokenImpl {

    public GremlinToken() {
        super("Gremlin Token", "2/2 red Gremlin creature token");
        cardType.add(CardType.CREATURE);
        this.setOriginalExpansionSetCode("AER");
        subtype.add(SubType.GREMLIN);
        color.setRed(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public GremlinToken(final GremlinToken token) {
        super(token);
    }

    public GremlinToken copy() {
        return new GremlinToken(this);
    }
}
