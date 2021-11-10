

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class TuskenRaiderToken extends TokenImpl {

    public TuskenRaiderToken() {
        super("Tusken Raider Token", "white Tusken Raider creature token", 1, 1);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.TUSKEN);
        subtype.add(SubType.RAIDER);
    }

    public TuskenRaiderToken(final TuskenRaiderToken token) {
        super(token);
    }

    public TuskenRaiderToken copy() {
        return new TuskenRaiderToken(this);
    }
}
