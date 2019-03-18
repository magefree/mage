

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class RhysTheRedeemedToken extends TokenImpl {

    public RhysTheRedeemedToken() {
        super("Elf Warrior", "1/1 green and white Elf Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.ELF);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public RhysTheRedeemedToken(final RhysTheRedeemedToken token) {
        super(token);
    }

    public RhysTheRedeemedToken copy() {
        return new RhysTheRedeemedToken(this);
    }
}
