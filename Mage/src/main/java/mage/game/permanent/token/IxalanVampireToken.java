
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;

/**
 *
 * @author TheElk801
 */
public final class IxalanVampireToken extends TokenImpl {

    public IxalanVampireToken() {
        super("Vampire Token", "1/1 white Vampire creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
    }

    public IxalanVampireToken(final IxalanVampireToken token) {
        super(token);
    }

    public IxalanVampireToken copy() {
        return new IxalanVampireToken(this);
    }
}
