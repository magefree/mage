package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ZombieDruidToken extends TokenImpl {

    public ZombieDruidToken() {
        super("Zombie Druid Token", "2/2 black Zombie Druid creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.DRUID);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private ZombieDruidToken(final ZombieDruidToken token) {
        super(token);
    }

    @Override
    public ZombieDruidToken copy() {
        return new ZombieDruidToken(this);
    }
}
