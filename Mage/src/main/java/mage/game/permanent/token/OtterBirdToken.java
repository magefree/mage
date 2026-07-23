package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author ChesseTheWasp
 */
public final class OtterBirdToken extends TokenImpl {

    public OtterBirdToken() {
        super("Otter Bird Token", "1/1 Blue Otter Bird");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.OTTER);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private OtterBirdToken(final OtterBirdToken token) {
        super(token);
    }

    public OtterBirdToken copy() {
        return new OtterBirdToken(this);
    }
}
