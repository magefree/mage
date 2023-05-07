package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class TheHollowSentinelToken extends TokenImpl {

    public TheHollowSentinelToken() {
        super("The Hollow Sentinel", "The Hollow Sentinel, a legendary 3/3 colorless Phyrexian Golem artifact creature token");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public TheHollowSentinelToken(final TheHollowSentinelToken token) {
        super(token);
    }

    public TheHollowSentinelToken copy() {
        return new TheHollowSentinelToken(this);
    }
}
