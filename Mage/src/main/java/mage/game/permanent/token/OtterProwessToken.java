package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class OtterProwessToken extends TokenImpl {

    public OtterProwessToken() {
        super("Otter Token", "1/1 blue and red Otter creature token with prowess");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.OTTER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new ProwessAbility());
    }

    private OtterProwessToken(final OtterProwessToken token) {
        super(token);
    }

    public OtterProwessToken copy() {
        return new OtterProwessToken(this);
    }
}
