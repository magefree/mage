

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class PenumbraBobcatToken extends TokenImpl {

    public PenumbraBobcatToken() {
        super("Cat Token", "2/1 black Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.CAT);
        power = new MageInt(2);
        toughness = new MageInt(1);

        setOriginalExpansionSetCode("DMR");
        setTokenType(1);
    }

    public PenumbraBobcatToken(final PenumbraBobcatToken token) {
        super(token);
    }

    public PenumbraBobcatToken copy() {
        return new PenumbraBobcatToken(this);
    }
}
