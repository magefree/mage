package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class FaerieBlueBlackToken extends TokenImpl {

    public FaerieBlueBlackToken() {
        super("Faerie Token", "1/1 blue and black Faerie creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setBlack(true);
        subtype.add(SubType.FAERIE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private FaerieBlueBlackToken(final FaerieBlueBlackToken token) {
        super(token);
    }

    public FaerieBlueBlackToken copy() {
        return new FaerieBlueBlackToken(this);
    }
}
