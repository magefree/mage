package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GiantOpportunityToken extends TokenImpl {

    public GiantOpportunityToken() {
        super("Giant Token", "7/7 green Giant creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GIANT);
        color.setGreen(true);
        power = new MageInt(7);
        toughness = new MageInt(7);
    }

    private GiantOpportunityToken(final GiantOpportunityToken token) {
        super(token);
    }

    public GiantOpportunityToken copy() {
        return new GiantOpportunityToken(this);
    }
}
