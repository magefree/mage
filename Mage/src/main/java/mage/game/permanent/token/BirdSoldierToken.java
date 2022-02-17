

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class BirdSoldierToken extends TokenImpl {

    public BirdSoldierToken() {
        super("Bird Soldier", "1/1 white Bird Soldier creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);

        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    public BirdSoldierToken(final BirdSoldierToken token) {
        super(token);
    }

    public BirdSoldierToken copy() {
        return new BirdSoldierToken(this);
    }
}
