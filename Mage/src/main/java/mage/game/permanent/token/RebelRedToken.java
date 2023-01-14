package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class RebelRedToken extends TokenImpl {

    public RebelRedToken() {
        super("Rebel Token", "2/2 red Rebel creature token");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.REBEL);
    }

    public RebelRedToken(final RebelRedToken token) {
        super(token);
    }

    public RebelRedToken copy() {
        return new RebelRedToken(this);
    }
}
