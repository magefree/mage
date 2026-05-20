package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Elemental33BlueRedToken extends TokenImpl {

    public Elemental33BlueRedToken() {
        super("Elemental Token", "3/3 blue and red Elemental creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setBlue(true);
        color.setRed(true);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(FlyingAbility.getInstance());
    }

    private Elemental33BlueRedToken(final Elemental33BlueRedToken token) {
        super(token);
    }

    public Elemental33BlueRedToken copy() {
        return new Elemental33BlueRedToken(this);
    }
}
