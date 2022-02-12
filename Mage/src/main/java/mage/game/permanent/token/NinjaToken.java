package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class NinjaToken extends TokenImpl {

    public NinjaToken() {
        super("Ninja Token", "1/1 blue Ninja creature token with \"This creature can't be blocked.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.NINJA);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(new CantBeBlockedSourceEffect().setText("this creature can't be blocked")));
        availableImageSetCodes = Arrays.asList("NEO");
    }

    private NinjaToken(final NinjaToken token) {
        super(token);
    }

    public NinjaToken copy() {
        return new NinjaToken(this);
    }
}
