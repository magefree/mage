package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class InscriptionOfInsightToken extends TokenImpl {

    public InscriptionOfInsightToken(int xValue) {
        super("Illusion", "X/X blue Illusion creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public InscriptionOfInsightToken(final InscriptionOfInsightToken token) {
        super(token);
    }

    public InscriptionOfInsightToken copy() {
        return new InscriptionOfInsightToken(this);
    }
}
