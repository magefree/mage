package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EldraziHorrorToken extends TokenImpl {

    public EldraziHorrorToken() {
        super("Eldrazi Horror Token", "3/2 colorless Eldrazi Horror creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        subtype.add(SubType.HORROR);
        power = new MageInt(3);
        toughness = new MageInt(2);
    }

    public EldraziHorrorToken(final EldraziHorrorToken token) {
        super(token);
    }

    public EldraziHorrorToken copy() {
        return new EldraziHorrorToken(this);
    }
}