package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CrewIncreasedPowerAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PilotCrewToken extends TokenImpl {

    public PilotCrewToken() {
        super("Pilot Token", "1/1 colorless Pilot creature token with \"This token crews Vehicles as though its power were 2 greater.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PILOT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new CrewIncreasedPowerAbility());
    }

    private PilotCrewToken(final PilotCrewToken token) {
        super(token);
    }

    public PilotCrewToken copy() {
        return new PilotCrewToken(this);
    }
}
