package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ZombieBerserkerToken extends TokenImpl {

    public ZombieBerserkerToken() {
        super("Zombie Berserker Token", "2/2 black Zombie Berserker creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.BERSERKER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private ZombieBerserkerToken(final ZombieBerserkerToken token) {
        super(token);
    }

    public ZombieBerserkerToken copy() {
        return new ZombieBerserkerToken(this);
    }
}
