package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PhyrexianHydraWithReachToken extends TokenImpl {

    public PhyrexianHydraWithReachToken() {
        super("Phyrexian Hydra Token", "3/3 green and white Phyrexian Hydra creature token with reach");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.HYDRA);
        power = new MageInt(3);
        toughness = new MageInt(3);

        this.addAbility(ReachAbility.getInstance());
    }

    public PhyrexianHydraWithReachToken(final PhyrexianHydraWithReachToken token) {
        super(token);
    }

    public PhyrexianHydraWithReachToken copy() {
        return new PhyrexianHydraWithReachToken(this);
    }
}
