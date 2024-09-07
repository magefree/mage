package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GlimmerToken extends TokenImpl {

    public GlimmerToken() {
        super("Glimmer Token", "1/1 white Glimmer enchantment creature token");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.GLIMMER);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private GlimmerToken(final GlimmerToken token) {
        super(token);
    }

    @Override
    public GlimmerToken copy() {
        return new GlimmerToken(this);
    }
}
