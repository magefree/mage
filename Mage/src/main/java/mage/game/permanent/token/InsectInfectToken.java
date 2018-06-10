

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;

/**
 *
 * @author nantuko
 */
public final class InsectInfectToken extends TokenImpl {

    public InsectInfectToken() {
        super("Insect", "1/1 green Insect creature token with infect");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(InfectAbility.getInstance());
        setOriginalExpansionSetCode("SOM");
    }

    public InsectInfectToken(final InsectInfectToken token) {
        super(token);
    }

    public InsectInfectToken copy() {
        return new InsectInfectToken(this);
    }
}