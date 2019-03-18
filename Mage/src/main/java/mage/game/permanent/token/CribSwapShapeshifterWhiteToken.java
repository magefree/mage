

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;

/**
 *
 * @author spjspj
 */
public final class CribSwapShapeshifterWhiteToken extends TokenImpl {

    public CribSwapShapeshifterWhiteToken() {
        super("Shapeshifter", "1/1 colorless Shapeshifter creature token with changeling");
        this.setOriginalExpansionSetCode("LRW");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHAPESHIFTER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(ChangelingAbility.getInstance());
    }

    public CribSwapShapeshifterWhiteToken(final CribSwapShapeshifterWhiteToken token) {
        super(token);
    }

    public CribSwapShapeshifterWhiteToken copy() {
        return new CribSwapShapeshifterWhiteToken(this);
    }
}
