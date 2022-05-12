

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class CorruptedZendikonOozeToken extends TokenImpl {

    public CorruptedZendikonOozeToken() {
        super("Ooze Token", "3/3 black Ooze creature");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.OOZE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }
    public CorruptedZendikonOozeToken(final CorruptedZendikonOozeToken token) {
        super(token);
    }

    public CorruptedZendikonOozeToken copy() {
        return new CorruptedZendikonOozeToken(this);
    }
}
