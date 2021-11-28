package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class EdgarMarkovsCoffinToken extends TokenImpl {

    public EdgarMarkovsCoffinToken() {
        super("Vampire", "1/1 white and black Vampire creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(LifelinkAbility.getInstance());
    }

    public EdgarMarkovsCoffinToken(final EdgarMarkovsCoffinToken token) {
        super(token);
    }

    public EdgarMarkovsCoffinToken copy() {
        return new EdgarMarkovsCoffinToken(this);
    }
}
