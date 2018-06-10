

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class RapidHybridizationToken extends TokenImpl {

    public RapidHybridizationToken() {
        super("Frog Lizard", "3/3 green Frog Lizard creature token");
        this.setOriginalExpansionSetCode("GTC");
        cardType.add(CardType.CREATURE);

        color.setGreen(true);

        subtype.add(SubType.FROG);
        subtype.add(SubType.LIZARD);

        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public RapidHybridizationToken(final RapidHybridizationToken token) {
        super(token);
    }

    public RapidHybridizationToken copy() {
        return new RapidHybridizationToken(this);
    }
}
