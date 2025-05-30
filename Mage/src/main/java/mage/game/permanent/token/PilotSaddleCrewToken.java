package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CrewSaddleIncreasedPowerAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PilotSaddleCrewToken extends TokenImpl {

    public PilotSaddleCrewToken() {
        super("Pilot Token", "1/1 colorless Pilot creature token with \"This token saddles Mounts and crews Vehicles as though its power were 2 greater.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PILOT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new CrewSaddleIncreasedPowerAbility());
    }

    private PilotSaddleCrewToken(final PilotSaddleCrewToken token) {
        super(token);
    }

    public PilotSaddleCrewToken copy() {
        return new PilotSaddleCrewToken(this);
    }
}
