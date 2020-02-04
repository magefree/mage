

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;

/**
 *
 * @author spjspj
 */
public final class DeadlyGrubToken extends TokenImpl {

    public DeadlyGrubToken() {
        super("Insect", "6/1 green Insect creature token with shroud");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(6);
        toughness = new MageInt(1);
        this.addAbility(ShroudAbility.getInstance());
    }

    public DeadlyGrubToken(final DeadlyGrubToken token) {
        super(token);
    }

    public DeadlyGrubToken copy() {
        return new DeadlyGrubToken(this);
    }
}
