
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public final class TirelessTracker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Clue");

    static {
        filter.add(SubType.CLUE.getPredicate());
    }

    public TirelessTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a land enters the battlefield under your control, investigate. <i>(Create a colorless Clue artifact token with "{2}, Sacrifice this artifact: Draw a card.")</i>
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new InvestigateEffect(), new FilterControlledLandPermanent("a land"), false, null, true));

        // Whenever you sacrifice a Clue, put a +1/+1 counter on Tireless Tracker.
        this.addAbility(new TirelessTrackerTriggeredAbility());
    }

    private TirelessTracker(final TirelessTracker card) {
        super(card);
    }

    @Override
    public TirelessTracker copy() {
        return new TirelessTracker(this);
    }
}

class TirelessTrackerTriggeredAbility extends TriggeredAbilityImpl {

    public TirelessTrackerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a Clue, ");
    }

    public TirelessTrackerTriggeredAbility(final TirelessTrackerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TirelessTrackerTriggeredAbility copy() {
        return new TirelessTrackerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).hasSubtype(SubType.CLUE, game);
    }
}
