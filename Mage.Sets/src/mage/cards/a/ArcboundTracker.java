package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author weirddan455
 */
public final class ArcboundTracker extends CardImpl {

    public ArcboundTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Modular 2
        this.addAbility(new ModularAbility(this, 2));

        // Whenever you cast a spell other than your first spell each turn, put a +1/+1 counter on Arcbound Tracker.
        this.addAbility(new ArcboundTrackerTriggeredAbility(), new SpellsCastWatcher());
    }

    private ArcboundTracker(final ArcboundTracker card) {
        super(card);
    }

    @Override
    public ArcboundTracker copy() {
        return new ArcboundTracker(this);
    }
}

class ArcboundTrackerTriggeredAbility extends TriggeredAbilityImpl {

    public ArcboundTrackerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setTriggerPhrase("Whenever you cast a spell other than your first spell each turn, ");
    }

    private ArcboundTrackerTriggeredAbility(final ArcboundTrackerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArcboundTrackerTriggeredAbility copy() {
        return new ArcboundTrackerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            return watcher != null && watcher.getSpellsCastThisTurn(event.getPlayerId()).size() > 1;
        }
        return false;
    }
}
