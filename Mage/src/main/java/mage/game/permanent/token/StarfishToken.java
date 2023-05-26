package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spike
 */
public final class StarfishToken extends TokenImpl {

    public StarfishToken() {
        super("Starfish Token", "0/1 blue Starfish creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.STARFISH);
        color.setBlue(true);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    public StarfishToken(final StarfishToken token) {
        super(token);
    }

    @Override
    public StarfishToken copy() {
        return new StarfishToken(this);
    }
}