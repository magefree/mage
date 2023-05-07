package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RiseOfTheAntsInsectToken extends TokenImpl {

    public RiseOfTheAntsInsectToken() {
        super("Insect Token", "3/3 green Insect creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public RiseOfTheAntsInsectToken(final RiseOfTheAntsInsectToken token) {
        super(token);
    }

    public RiseOfTheAntsInsectToken copy() {
        return new RiseOfTheAntsInsectToken(this);
    }
}