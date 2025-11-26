package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaleOfKataraAndToph extends CardImpl {

    public TaleOfKataraAndToph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Creatures you control have "Whenever this creature becomes tapped for the first time during each of your turns, put a +1/+1 counter on it."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new TaleOfKataraAndTophTriggeredAbility(),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES
        )), new TaleOfKataraAndTophWatcher());
    }

    private TaleOfKataraAndToph(final TaleOfKataraAndToph card) {
        super(card);
    }

    @Override
    public TaleOfKataraAndToph copy() {
        return new TaleOfKataraAndToph(this);
    }
}

class TaleOfKataraAndTophTriggeredAbility extends TriggeredAbilityImpl {

    TaleOfKataraAndTophTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"));
        setTriggerPhrase("Whenever this creature becomes tapped for the first time during each of your turns, ");
    }

    private TaleOfKataraAndTophTriggeredAbility(final TaleOfKataraAndTophTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TaleOfKataraAndTophTriggeredAbility copy() {
        return new TaleOfKataraAndTophTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId())
                && event.getTargetId().equals(getSourceId())
                && TaleOfKataraAndTophWatcher.checkEvent(game, this, event);
    }
}

class TaleOfKataraAndTophWatcher extends Watcher {

    private final Map<MageObjectReference, UUID> map = new HashMap<>();

    TaleOfKataraAndTophWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            map.putIfAbsent(new MageObjectReference(event.getTargetId(), game), event.getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkEvent(Game game, Ability source, GameEvent event) {
        return Objects.equals(
                game.getState()
                        .getWatcher(TaleOfKataraAndTophWatcher.class)
                        .map
                        .get(new MageObjectReference(source.getSourceObject(game), game)),
                event.getId()
        );
    }
}
