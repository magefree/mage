package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.keyword.MentorAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counters;
import mage.counters.Counter;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.watchers.common.CountersAddedFirstTimeWatcher;

/**
 *
 * @author padfoothelix
 */
public final class DannyPink extends CardImpl {

    public DannyPink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Mentor
        this.addAbility(new MentorAbility());
        
        // Creatures you control have "Whenever one or more counters are put on this creature for the first time each turn, draw a card."
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        new DannyPinkTriggeredAbility(), 
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES
        )));

    }

    private DannyPink(final DannyPink card) {
        super(card);
    }

    @Override
    public DannyPink copy() {  
        return new DannyPink(this);
    }
}

class DannyPinkTriggeredAbility extends TriggeredAbilityImpl {

    DannyPinkTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        this.setTriggerPhrase("Whenever one or more counters are put on this creature for the first time each turn, ");
        this.addWatcher(new CountersAddedFirstTimeWatcher());
    }
    
    private DannyPinkTriggeredAbility(final DannyPinkTriggeredAbility ability) {
        super(ability);
    }

    // Non-token creatures entering with counters do not see the COUNTERS_ADDED event,
    // so we check for etb event too.
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED
                || (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!this.getSourceId().equals(event.getTargetId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
           return false;
        }
        // a non-token creature entering with counters does not see the COUNTERS_ADDED event.
        // therefore, we return true in either of two cases :
        // 1. a non-token creature enters with counters on it (no need to check the watcher)
        // 2. a COUNTERS_ADDED event occurs and the watcher is valid
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Counters counters = permanent.getCounters(game);
            return !counters.values().stream().mapToInt(Counter::getCount).noneMatch(x -> x > 0)
                    && !(permanent instanceof PermanentToken);
        }
        if (event.getType() == GameEvent.EventType.COUNTERS_ADDED) {
            return CountersAddedFirstTimeWatcher.checkEvent(event, permanent, game, 0);
        }
        return false;
    }
    
    @Override
    public DannyPinkTriggeredAbility copy() {
        return new DannyPinkTriggeredAbility(this);
    }
}
