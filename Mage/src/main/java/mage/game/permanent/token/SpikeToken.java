

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class SpikeToken extends TokenImpl {

    public SpikeToken() {
        super("Spike Token", "1/1 green Spike creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SPIKE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SpikeToken(final SpikeToken token) {
        super(token);
    }

    public SpikeToken copy() {
        return new SpikeToken(this);
    }
}
