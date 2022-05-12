package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class RhinoWarriorToken extends TokenImpl {

    public RhinoWarriorToken() {
        super("Rhino Warrior Token", "4/4 green Rhino Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.RHINO);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(4);

        availableImageSetCodes = Arrays.asList("SNC");
    }

    public RhinoWarriorToken(final RhinoWarriorToken token) {
        super(token);
    }

    public RhinoWarriorToken copy() {
        return new RhinoWarriorToken(this);
    }
}
