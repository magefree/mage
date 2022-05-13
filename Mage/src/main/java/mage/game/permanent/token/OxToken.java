package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class OxToken extends TokenImpl {

    public OxToken() {
        super("Ox Token", "2/4 white Ox creature token");

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.OX);
        power = new MageInt(2);
        toughness = new MageInt(4);

    }

    public OxToken(final OxToken token) {
        super(token);
    }

    @Override
    public OxToken copy() {
        return new OxToken(this);
    }
}
