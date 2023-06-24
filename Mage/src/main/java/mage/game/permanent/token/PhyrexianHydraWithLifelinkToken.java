package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PhyrexianHydraWithLifelinkToken extends TokenImpl {

    public PhyrexianHydraWithLifelinkToken() {
        super("Phyrexian Hydra Token", "3/3 green and white Phyrexian Hydra creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.HYDRA);
        power = new MageInt(3);
        toughness = new MageInt(3);

        this.addAbility(LifelinkAbility.getInstance());
    }

    public PhyrexianHydraWithLifelinkToken(final PhyrexianHydraWithLifelinkToken token) {
        super(token);
    }

    public PhyrexianHydraWithLifelinkToken copy() {
        return new PhyrexianHydraWithLifelinkToken(this);
    }
}
