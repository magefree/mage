package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class PegasusToken extends TokenImpl {

    public PegasusToken() {
        super("Pegasus Token", "1/1 white Pegasus creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.PEGASUS);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    private PegasusToken(final PegasusToken token) {
        super(token);
    }

    public PegasusToken copy() {
        return new PegasusToken(this);
    }
}
