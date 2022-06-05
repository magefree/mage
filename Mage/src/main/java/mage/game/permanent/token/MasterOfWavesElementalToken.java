

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class MasterOfWavesElementalToken extends TokenImpl {

    public MasterOfWavesElementalToken() {
        super("Elemental Token", "1/0 blue Elemental creature");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);

        this.color.setBlue(true);

        this.power = new MageInt(1);
        this.toughness = new MageInt(0);

        availableImageSetCodes = Arrays.asList("THS", "DDT");
    }

    public MasterOfWavesElementalToken(final MasterOfWavesElementalToken token) {
        super(token);
    }

    public MasterOfWavesElementalToken copy() {
        return new MasterOfWavesElementalToken(this);
    }
}
