

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author NinthWorld
 */
public final class TerranSoldierToken extends TokenImpl {

    public TerranSoldierToken() {
        super("Terran Soldier", "2/2 red and white Terran Soldier creature token", 2, 2);
        this.setOriginalExpansionSetCode("DDSC");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add(SubType.TERRAN);
        subtype.add(SubType.SOLDIER);
    }

    public TerranSoldierToken(final TerranSoldierToken token) {
        super(token);
    }

    @Override
    public TerranSoldierToken copy() {
        return new TerranSoldierToken(this);
    }
}
