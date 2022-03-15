package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PegasusToken2 extends TokenImpl {

    public PegasusToken2() {
        super("Pegasus Token", "2/2 white Pegasus creature token with flying");
        this.cardType.add(CardType.CREATURE);
        this.color.setWhite(true);
        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private PegasusToken2(final PegasusToken2 token) {
        super(token);
    }

    public PegasusToken2 copy() {
        return new PegasusToken2(this);
    }
}
