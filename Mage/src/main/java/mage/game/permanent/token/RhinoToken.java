package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class RhinoToken extends TokenImpl {

    public RhinoToken() {
        super("Rhino", "4/4 green Rhino creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.RHINO);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(TrampleAbility.getInstance());

        availableImageSetCodes = Arrays.asList("DGM", "RTR", "MH1", "C19", "MIC");
    }

    public RhinoToken(final RhinoToken token) {
        super(token);
    }

    public RhinoToken copy() {
        return new RhinoToken(this);
    }
}
