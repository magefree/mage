

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class RhonassLastStandToken extends TokenImpl {

    public RhonassLastStandToken() {
        super("Snake Token", "5/4 green Snake creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SNAKE);
        power = new MageInt(5);
        toughness = new MageInt(4);
    }

    public RhonassLastStandToken(final RhonassLastStandToken token) {
        super(token);
    }

    public RhonassLastStandToken copy() {
        return new RhonassLastStandToken(this);
    }
}
