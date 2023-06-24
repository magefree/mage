package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class DarettiConstructToken extends TokenImpl {

    public DarettiConstructToken() {
        super("Construct Token", "1/1 colorless Construct artifact creature token with defender");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(DefenderAbility.getInstance());
    }

    public DarettiConstructToken(final DarettiConstructToken token) {
        super(token);
    }

    public DarettiConstructToken copy() {
        return new DarettiConstructToken(this);
    }
}
