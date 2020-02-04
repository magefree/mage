

package mage.game.permanent.token;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class SpoilsOfBloodHorrorToken extends TokenImpl {

    public SpoilsOfBloodHorrorToken() {
        this(1);
    }
    public SpoilsOfBloodHorrorToken(int xValue) {
        super("Horror", "X/X black Horror creature token");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HORROR);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public SpoilsOfBloodHorrorToken(final SpoilsOfBloodHorrorToken token) {
        super(token);
    }

    public SpoilsOfBloodHorrorToken copy() {
        return new SpoilsOfBloodHorrorToken(this);
    }
}
