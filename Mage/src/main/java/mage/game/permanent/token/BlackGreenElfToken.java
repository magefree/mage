package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class BlackGreenElfToken extends TokenImpl {

    public BlackGreenElfToken() {
        super("Elf Token", "2/2 black and green Elf creature token");
        this.cardType.add(CardType.CREATURE);
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private BlackGreenElfToken(final BlackGreenElfToken token) {
        super(token);
    }

    public BlackGreenElfToken copy() {
        return new BlackGreenElfToken(this);
    }
}
