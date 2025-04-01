package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 * @author padfoothelix
 */
public final class AlienRhinoToken extends TokenImpl {

    public AlienRhinoToken() {
        super("Alien Rhino Token", "4/4 white Alien Rhino creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ALIEN);
        subtype.add(SubType.RHINO);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private AlienRhinoToken(final AlienRhinoToken token) {
        super(token);
    }

    @Override
    public AlienRhinoToken copy() {
        return new AlienRhinoToken(this);
    }
}
