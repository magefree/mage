

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author LoneFox
 */
public final class PegasusToken extends TokenImpl {

    public PegasusToken() {
        super("Pegasus", "1/1 white Pegasus creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.PEGASUS);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        setOriginalExpansionSetCode("C14");
    }

    public PegasusToken(final PegasusToken token) {
        super(token);
    }

    public PegasusToken copy() {
        return new PegasusToken(this);
    }
}
