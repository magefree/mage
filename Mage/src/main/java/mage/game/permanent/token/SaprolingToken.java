package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SaprolingToken extends TokenImpl {

    public SaprolingToken() {
        super("Saproling Token", "1/1 green Saproling creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SAPROLING);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SaprolingToken(final SaprolingToken token) {
        super(token);
    }

    public SaprolingToken copy() {
        return new SaprolingToken(this);
    }
}