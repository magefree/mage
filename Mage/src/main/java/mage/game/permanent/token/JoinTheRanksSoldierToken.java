

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class JoinTheRanksSoldierToken extends TokenImpl {

    public JoinTheRanksSoldierToken() {
        super("Soldier Ally Token", "1/1 white Soldier Ally creature token");
        this.setOriginalExpansionSetCode("WWK");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SOLDIER);
        subtype.add(SubType.ALLY);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public JoinTheRanksSoldierToken(final JoinTheRanksSoldierToken token) {
        super(token);
    }

    public JoinTheRanksSoldierToken copy() {
        return new JoinTheRanksSoldierToken(this);
    }
}
