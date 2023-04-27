package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author nantuko
 */
public final class InsectInfectToken extends TokenImpl {

    public InsectInfectToken() {
        super("Phyrexian Insect Token", "1/1 green Phyrexian Insect creature token with infect");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(InfectAbility.getInstance());
        availableImageSetCodes = Arrays.asList("SOM", "ONC");
    }

    public InsectInfectToken(final InsectInfectToken token) {
        super(token);
    }

    public InsectInfectToken copy() {
        return new InsectInfectToken(this);
    }
}
