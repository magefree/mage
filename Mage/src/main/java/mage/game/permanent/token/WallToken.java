

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;

/**
 *
 * @author spjspj
 */
public final class WallToken extends TokenImpl {

    public WallToken() {
        super("", "2/6 white wall creature with defender");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.WALL);
        power = new MageInt(2);
        toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
    }

    public WallToken(final WallToken token) {
        super(token);
    }

    public WallToken copy() {
        return new WallToken(this);
    }
}
