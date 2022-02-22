package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class SliverWhiteToken extends TokenImpl {

    public SliverWhiteToken(int power_val, int toughness_val) {
        super("SliverWhite", power_val + "/" + toughness_val + " white Sliver creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SLIVER );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public SliverWhiteToken() {
        this(2, 2);
    }

    public SliverWhiteToken(final SliverWhiteToken token) {
        super(token);
    }

    public SliverWhiteToken copy() {
        return new SliverWhiteToken(this);
    }
}
