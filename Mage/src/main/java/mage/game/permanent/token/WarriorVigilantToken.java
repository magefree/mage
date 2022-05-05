

package mage.game.permanent.token;

import java.util.Arrays;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class WarriorVigilantToken extends TokenImpl {

    public WarriorVigilantToken() {
        super("Warrior Token", "1/1 white Warrior creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(VigilanceAbility.getInstance());
        availableImageSetCodes.addAll(Arrays.asList("AKH"));
    }

    public WarriorVigilantToken(final WarriorVigilantToken token) {
        super(token);
    }

    @Override
        public WarriorVigilantToken copy() {
        return new WarriorVigilantToken(this);
    }
}
