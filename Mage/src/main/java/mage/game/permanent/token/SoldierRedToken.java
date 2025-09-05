package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SoldierRedToken extends TokenImpl {

    public SoldierRedToken() {
        super("Soldier Token", "2/2 red Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private SoldierRedToken(final SoldierRedToken token) {
        super(token);
    }

    public SoldierRedToken copy() {
        return new SoldierRedToken(this);
    }
}
