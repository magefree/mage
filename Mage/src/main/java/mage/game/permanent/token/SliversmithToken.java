

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class SliversmithToken extends TokenImpl {

    public SliversmithToken() {
        super("Metallic Sliver", "1/1 colorless Sliver creature token named Metallic Sliver");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.SLIVER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.setOriginalExpansionSetCode("FUT");
    }

    public SliversmithToken(final SliversmithToken token) {
        super(token);
    }

    public SliversmithToken copy() {
        return new SliversmithToken(this);
    }
}
