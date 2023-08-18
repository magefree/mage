package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class MetallicSliverToken extends TokenImpl {

    public MetallicSliverToken() {
        super("Metallic Sliver", "1/1 colorless Sliver artifact creature token named Metallic Sliver");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SLIVER);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    protected MetallicSliverToken(final MetallicSliverToken token) {
        super(token);
    }

    public MetallicSliverToken copy() {
        return new MetallicSliverToken(this);
    }
}
