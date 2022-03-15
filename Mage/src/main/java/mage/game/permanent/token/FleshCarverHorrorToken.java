

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class FleshCarverHorrorToken extends TokenImpl {

    public FleshCarverHorrorToken() {
        this(1);
    }

    public FleshCarverHorrorToken(int xValue) {
        super("Horror Token", "X/X black Horror creature token");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.HORROR);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public FleshCarverHorrorToken(final FleshCarverHorrorToken token) {
        super(token);
    }

    public FleshCarverHorrorToken copy() {
        return new FleshCarverHorrorToken(this);
    }
}
