package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
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
    }

    public SorinLordOfInnistradVampireToken(final SorinLordOfInnistradVampireToken token) {
        super(token);
    }

    public SorinLordOfInnistradVampireToken copy() {
        return new SorinLordOfInnistradVampireToken(this);
    }
}
