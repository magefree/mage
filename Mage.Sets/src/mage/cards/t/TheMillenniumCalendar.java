package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.StaticHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.UntappedBatchEvent;
import mage.game.events.UntappedEvent;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheMillenniumCalendar extends CardImpl {

    public TheMillenniumCalendar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever you untap one or more permanents during your untap step, put that many time counters on The Millennium Calendar.
        this.addAbility(new TheMillenniumCalendarTriggeredAbility());

        // {2}, {T}: Double the number of time counters on The Millennium Calendar.
        Ability ability = new SimpleActivatedAbility(
                new DoubleCountersSourceEffect(CounterType.TIME),
                new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // When there are 1,000 or more time counters on The Millennium Calendar, sacrifice it and each opponent loses 1,000 life.
        this.addAbility(new TheMillenniumCalendarStateTriggeredAbility());
    }

    private TheMillenniumCalendar(final TheMillenniumCalendar card) {
        super(card);
    }

    @Override
    public TheMillenniumCalendar copy() {
        return new TheMillenniumCalendar(this);
    }
}

class TheMillenniumCalendarTriggeredAbility extends TriggeredAbilityImpl {

    public TheMillenniumCalendarTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    protected TheMillenniumCalendarTriggeredAbility(final TheMillenniumCalendarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheMillenniumCalendarTriggeredAbility copy() {
        return new TheMillenniumCalendarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAPPED_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.isActivePlayer(getControllerId())) {
            return false;
        }
        UntappedBatchEvent batchEvent = (UntappedBatchEvent) event;
        int count = batchEvent
                .getEvents()
                .stream()
                .filter(UntappedEvent::isAnUntapStepEvent)
                .map(UntappedEvent::getTargetId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> p.getControllerId().equals(getControllerId()))
                .mapToInt(p -> 1)
                .sum();

        if (count <= 0) {
            return false;
        }

        this.getEffects().clear();
        this.addEffect(new AddCountersSourceEffect(CounterType.TIME.createInstance(count)));
        this.getHints().clear();
        this.addHint(new StaticHint("Number of untapped permanents: " + count));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you untap one or more permanents during your untap step, "
                + "put that many time counters on {this}.";
    }
}

/**
 * Inspired by {@link mage.cards.n.NineLives}
 */
class TheMillenniumCalendarStateTriggeredAbility extends StateTriggeredAbility {

    public TheMillenniumCalendarStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        withRuleTextReplacement(true);
        addEffect(new LoseLifeOpponentsEffect(1000).setText("and each opponent loses 1,000 life"));
        setTriggerPhrase("When there are 1,000 or more time counters on {this}, ");
    }

    private TheMillenniumCalendarStateTriggeredAbility(final TheMillenniumCalendarStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheMillenniumCalendarStateTriggeredAbility copy() {
        return new TheMillenniumCalendarStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.TIME) >= 1000;
    }
}
