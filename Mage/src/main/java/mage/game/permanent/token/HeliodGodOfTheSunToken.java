
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class HeliodGodOfTheSunToken extends TokenImpl {

    public HeliodGodOfTheSunToken() {
        super("Cleric Token", "2/1 white Cleric enchantment creature token");
        this.cardType.add(CardType.CREATURE);
        this.cardType.add(CardType.ENCHANTMENT);

        this.subtype.add(SubType.CLERIC);
        this.color.setWhite(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    public HeliodGodOfTheSunToken(final HeliodGodOfTheSunToken token) {
        super(token);
    }

    public HeliodGodOfTheSunToken copy() {
        return new HeliodGodOfTheSunToken(this);
    }
}
