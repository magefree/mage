package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class StoneIdolToken extends TokenImpl {

    public StoneIdolToken() {
        super("Construct Token", "6/12 colorless Construct artifact creature token with trample");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(6);
        toughness = new MageInt(12);
        addAbility(TrampleAbility.getInstance());
    }

    protected StoneIdolToken(final StoneIdolToken token) {
        super(token);
    }

    public StoneIdolToken copy() {
        return new StoneIdolToken(this);
    }
}
