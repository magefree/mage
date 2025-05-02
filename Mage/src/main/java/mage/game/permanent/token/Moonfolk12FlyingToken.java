package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class Moonfolk12FlyingToken extends TokenImpl {

    public Moonfolk12FlyingToken() {
        super("Moonfolk Token", "1/2 blue Moonfolk creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.MOONFOLK);
        power = new MageInt(1);
        toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
    }

    private Moonfolk12FlyingToken(final Moonfolk12FlyingToken token) {
        super(token);
    }

    @Override
    public Moonfolk12FlyingToken copy() {
        return new Moonfolk12FlyingToken(this);
    }
}
