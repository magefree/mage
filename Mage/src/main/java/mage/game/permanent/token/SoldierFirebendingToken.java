package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FirebendingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SoldierFirebendingToken extends TokenImpl {

    public SoldierFirebendingToken() {
        super("Soldier Token", "2/2 red Soldier creature token with firebending 1");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(new FirebendingAbility(1));
    }

    private SoldierFirebendingToken(final SoldierFirebendingToken token) {
        super(token);
    }

    public SoldierFirebendingToken copy() {
        return new SoldierFirebendingToken(this);
    }
}
