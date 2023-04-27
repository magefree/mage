
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class SorinLordOfInnistradVampireToken extends TokenImpl {

    public SorinLordOfInnistradVampireToken() {
        super("Vampire Token", "1/1 black Vampire creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());

        availableImageSetCodes = Arrays.asList("DKA");
    }

    public SorinLordOfInnistradVampireToken(final SorinLordOfInnistradVampireToken token) {
        super(token);
    }

    public SorinLordOfInnistradVampireToken copy() {
        return new SorinLordOfInnistradVampireToken(this);
    }
}
