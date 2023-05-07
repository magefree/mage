package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SoldierToken extends TokenImpl {

    public SoldierToken() {
        super("Soldier Token", "1/1 white Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SoldierToken(final SoldierToken token) {
        super(token);
    }

    @Override
    public SoldierToken copy() {
        return new SoldierToken(this);
    }
}
