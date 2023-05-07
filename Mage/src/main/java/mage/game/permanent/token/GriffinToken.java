package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class GriffinToken extends TokenImpl {

    public GriffinToken() {
        super("Griffin Token", "2/2 white Griffin creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.GRIFFIN);
        power = new MageInt(2);
        toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    public GriffinToken(final GriffinToken token) {
        super(token);
    }

    public GriffinToken copy() {
        return new GriffinToken(this);
    }

}
