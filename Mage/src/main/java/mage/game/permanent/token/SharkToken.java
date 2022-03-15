package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SharkToken extends TokenImpl {

    public SharkToken() {
        this(0);
    }
    public SharkToken(int xValue) {
        super("Shark Token", "X/X blue Shark creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SHARK);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        addAbility(FlyingAbility.getInstance());
    }

    private SharkToken(final SharkToken token) {
        super(token);
    }

    @Override
    public SharkToken copy() {
        return new SharkToken(this);
    }
}
