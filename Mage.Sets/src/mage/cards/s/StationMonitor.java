package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DroneToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StationMonitor extends CardImpl {

    public StationMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast your second spell each turn, create a 1/1 colorless Drone artifact creature token with flying and "This token can block only creatures with flying."
        this.addAbility(new CastSecondSpellTriggeredAbility(new CreateTokenEffect(new DroneToken2())));
    }

    private StationMonitor(final StationMonitor card) {
        super(card);
    }

    @Override
    public StationMonitor copy() {
        return new StationMonitor(this);
    }
}
