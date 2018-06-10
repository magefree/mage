

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;

/**
 *
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
    }

    public RhinoToken(final RhinoToken token) {
        super(token);
    }

    public RhinoToken copy() {
        return new RhinoToken(this);
    }
}
