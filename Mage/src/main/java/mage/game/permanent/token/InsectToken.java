package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class InsectToken extends TokenImpl {

    public InsectToken() {
        this((String) null);
    }

    public InsectToken(String setCode) {
        super("Insect Token", "1/1 green Insect creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public InsectToken(final InsectToken token) {
        super(token);
    }

    public InsectToken copy() {
        return new InsectToken(this);
    }
}