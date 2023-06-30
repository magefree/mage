package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class SliverToken extends TokenImpl {

    public SliverToken() {
        super("Sliver Token", "1/1 colorless Sliver creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SLIVER);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SliverToken(final SliverToken token) {
        super(token);
    }

    public SliverToken copy() {
        return new SliverToken(this);
    }
}
