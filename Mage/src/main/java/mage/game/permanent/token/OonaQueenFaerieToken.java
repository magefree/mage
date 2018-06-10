

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class OonaQueenFaerieToken extends TokenImpl {

    public OonaQueenFaerieToken() {
        super("Faerie Rogue", "1/1 blue and black Faerie Rogue creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setBlack(true);
        subtype.add(SubType.FAERIE);
        subtype.add(SubType.ROGUE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
    public OonaQueenFaerieToken(final OonaQueenFaerieToken token) {
        super(token);
    }

    public OonaQueenFaerieToken copy() {
        return new OonaQueenFaerieToken(this);
    }
}
