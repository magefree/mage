

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class MysticGenesisOozeToken extends TokenImpl {

    public MysticGenesisOozeToken() {
        this(0);
    }
    public MysticGenesisOozeToken(int xValue) {
        super("Ooze Token", "X/X green Ooze creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.OOZE);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        setOriginalExpansionSetCode("RTR");
    }

    public MysticGenesisOozeToken(final MysticGenesisOozeToken token) {
        super(token);
    }

    public MysticGenesisOozeToken copy() {
        return new MysticGenesisOozeToken(this);
    }
}
