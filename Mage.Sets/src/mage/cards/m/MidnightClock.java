package mage.cards.m;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardIntoLibraryEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidnightClock extends CardImpl {

    public MidnightClock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {2}{U}: Put an hour counter on Midnight Clock.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.HOUR.createInstance()), new ManaCostsImpl<>("{2}{U}")
        ));

        // At the beginning of each upkeep, put an hour counter on Midnight Clock.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new AddCountersSourceEffect(CounterType.HOUR.createInstance()), false
        ));

        // When the twelfth hour counter is put on Midnight Clock, shuffle your hand and graveyard into your library, then draw seven cards. Exile Midnight Clock.
        this.addAbility(new MidnightClockTriggeredAbility());
    }

    private MidnightClock(final MidnightClock card) {
        super(card);
    }

    @Override
    public MidnightClock copy() {
        return new MidnightClock(this);
    }
}

class MidnightClockTriggeredAbility extends TriggeredAbilityImpl {

    MidnightClockTriggeredAbility() {
        super(Zone.ALL, new ShuffleHandGraveyardIntoLibraryEffect(), false);
        this.addEffect(new DrawCardSourceControllerEffect(7).concatBy(", then"));
        this.addEffect(new ExileSourceEffect());
        this.setTriggerPhrase("When the twelfth hour counter is put on {this}, ");
    }

    private MidnightClockTriggeredAbility(final MidnightClockTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId()) || !event.getData().equals(CounterType.HOUR.getName())) {
            return false;
        }
        int amountAdded = event.getAmount();
        Permanent sourcePermanent = Optional
                .ofNullable(game.getPermanent(getSourceId()))
                .orElse(game.getPermanentEntering(getSourceId()));
        int hourCounters;
        if (sourcePermanent != null) {
            hourCounters = sourcePermanent.getCounters(game).getCount(CounterType.HOUR);
        } else {
            hourCounters = amountAdded;
        }
        return hourCounters - amountAdded < 12 && 12 <= hourCounters;
    }

    @Override
    public MidnightClockTriggeredAbility copy() {
        return new MidnightClockTriggeredAbility(this);
    }
}
