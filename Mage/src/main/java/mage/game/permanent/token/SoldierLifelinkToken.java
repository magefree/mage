package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class SoldierLifelinkToken extends TokenImpl {

    public SoldierLifelinkToken() {
        super("Soldier Token", "1/1 white Soldier creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
    }

    public SoldierLifelinkToken(final SoldierLifelinkToken token) {
        super(token);
    }

    public SoldierLifelinkToken copy() {
        return new SoldierLifelinkToken(this);
    }

}
