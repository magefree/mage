

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class RebelToken extends TokenImpl {

    public RebelToken() {
        super("Rebel Token", "1/1 white Rebel creature token", 1, 1);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.REBEL);
    }

    public RebelToken(final RebelToken token) {
        super(token);
    }

    public RebelToken copy() {
        return new RebelToken(this);
    }
}
