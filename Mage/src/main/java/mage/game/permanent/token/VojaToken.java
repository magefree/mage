package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author spjspj
 */
public final class VojaToken extends TokenImpl {

    public VojaToken() {
        super("Voja", "Voja, a legendary 2/2 green and white Wolf creature token");
        this.cardType.add(CardType.CREATURE);
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    public VojaToken(final VojaToken token) {
        super(token);
    }

    public VojaToken copy() {
        return new VojaToken(this);
    }

}
