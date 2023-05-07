package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PhyrexianSaprolingToken extends TokenImpl {

    public PhyrexianSaprolingToken() {
        super("Phyrexian Saproling Token", "1/1 green Phyrexian Saproling creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.SAPROLING);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public PhyrexianSaprolingToken(final PhyrexianSaprolingToken token) {
        super(token);
    }

    public PhyrexianSaprolingToken copy() {
        return new PhyrexianSaprolingToken(this);
    }
}
