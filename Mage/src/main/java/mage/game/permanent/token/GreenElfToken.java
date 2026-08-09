package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class GreenElfToken extends TokenImpl {

    public GreenElfToken() {
        super("Elf Token", "1/1 green Elf creature token");
        this.cardType.add(CardType.CREATURE);
        this.color.setGreen(true);
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    private GreenElfToken(final GreenElfToken token) {
        super(token);
    }

    public GreenElfToken copy() {
        return new GreenElfToken(this);
    }
}
