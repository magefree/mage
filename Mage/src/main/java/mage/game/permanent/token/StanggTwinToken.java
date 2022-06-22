

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 *
 * @author L_J
 */
public final class StanggTwinToken extends TokenImpl {

    public StanggTwinToken() {
        super("Stangg Twin", "Stangg Twin, a legendary 3/4 red and green Human Warrior creature token");
        this.cardType.add(CardType.CREATURE);
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        availableImageSetCodes = Arrays.asList("A25");
    }

    public StanggTwinToken(final StanggTwinToken token) {
        super(token);
    }

    public StanggTwinToken copy() {
        return new StanggTwinToken(this);
    }
}
