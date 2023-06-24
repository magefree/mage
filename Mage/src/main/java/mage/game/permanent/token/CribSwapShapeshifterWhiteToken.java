package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class CribSwapShapeshifterWhiteToken extends TokenImpl {

    public CribSwapShapeshifterWhiteToken() {
        super("Shapeshifter Token", "1/1 colorless Shapeshifter creature token with changeling");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHAPESHIFTER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new ChangelingAbility());
    }

    public CribSwapShapeshifterWhiteToken(final CribSwapShapeshifterWhiteToken token) {
        super(token);
    }

    public CribSwapShapeshifterWhiteToken copy() {
        return new CribSwapShapeshifterWhiteToken(this);
    }
}
