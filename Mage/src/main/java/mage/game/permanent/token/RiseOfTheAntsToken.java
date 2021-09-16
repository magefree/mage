package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RiseOfTheAntsToken extends TokenImpl {

    public RiseOfTheAntsToken() {
        super("Insect", "3/3 green Insect creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public RiseOfTheAntsToken(final RiseOfTheAntsToken token) {
        super(token);
    }

    public RiseOfTheAntsToken copy() {
        return new RiseOfTheAntsToken(this);
    }
}