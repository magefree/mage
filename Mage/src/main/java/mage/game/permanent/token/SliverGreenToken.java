package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author mschatz
 */
public final class SliverGreenToken extends TokenImpl {

    public SliverGreenToken(int power_val, int toughness_val) {
        super("SliverGreen", power_val + "/" + toughness_val + " green Sliver creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SLIVER );
        power = new MageInt(power_val);
        toughness = new MageInt(toughness_val);
    }

    public SliverGreenToken() {
        this(2, 2);
    }

    public SliverGreenToken(final SliverGreenToken token) {
        super(token);
    }

    public SliverGreenToken copy() {
        return new SliverGreenToken(this);
    }
}
