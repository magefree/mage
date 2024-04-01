package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SheepWhiteToken extends TokenImpl {

    public SheepWhiteToken() {
        super("Sheep Token", "1/1 white Sheep creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SHEEP);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private SheepWhiteToken(final SheepWhiteToken token) {
        super(token);
    }

    public SheepWhiteToken copy() {
        return new SheepWhiteToken(this);
    }
}
