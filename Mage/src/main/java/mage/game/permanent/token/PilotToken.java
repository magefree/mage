package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CrewIncreasedPowerAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PilotToken extends TokenImpl {

    public PilotToken() {
        super("Pilot Token", "1/1 colorless Pilot creature token with \"This creature crews Vehicles as though its power were 2 greater.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PILOT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new CrewIncreasedPowerAbility("this creature"));

        availableImageSetCodes = Arrays.asList("NEO");
    }

    public PilotToken(final PilotToken token) {
        super(token);
    }

    public PilotToken copy() {
        return new PilotToken(this);
    }
}
