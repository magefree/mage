package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class VarmintToken extends TokenImpl {

    public VarmintToken() {
        super("Varmint Token", "2/1 green Varmint creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.VARMINT);
        power = new MageInt(2);
        toughness = new MageInt(1);
    }

    private VarmintToken(final VarmintToken token) {
        super(token);
    }

    @Override
    public VarmintToken copy() {
        return new VarmintToken(this);
    }
}
