package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Arrays;

/**
 *
 * @author weirddan455
 */
public class SpiritClericToken extends TokenImpl {

    public SpiritClericToken() {
        super("Spirit Cleric Token", "white Spirit Cleric creature token with \"This creature's power and toughness are each equal to the number of Spirits you control.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        subtype.add(SubType.CLERIC);
        color.setWhite(true);

        power = new MageInt(0);
        toughness = new MageInt(0);

        // This creature’s power and toughness are each equal to the number of Spirits you control.
        this.addAbility(new SimpleStaticAbility(new SetBasePowerToughnessSourceEffect(SpiritClericTokenValue.instance, Duration.EndOfGame)));

        availableImageSetCodes = Arrays.asList("VOW");
    }

    private SpiritClericToken(final SpiritClericToken token) {
        super(token);
    }

    @Override
    public SpiritClericToken copy() {
        return new SpiritClericToken(this);
    }
}

enum SpiritClericTokenValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int spirits = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(sourceAbility.getControllerId())) {
            if (permanent.hasSubtype(SubType.SPIRIT, game)) {
                spirits++;
            }
        }
        return spirits;
    }

    @Override
    public SpiritClericTokenValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "Spirits you control";
    }
}
