package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulWarchief extends CardImpl {

    public VengefulWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you lose life for the first time each turn, put a +1/+1 counter on Vengeful Warchief.
        this.addAbility(new VengefulWarchiefTriggeredAbility(), new VengefulWarchiefWatcher());
    }

    private VengefulWarchief(final VengefulWarchief card) {
        super(card);
    }

    @Override
    public VengefulWarchief copy() {
        return new VengefulWarchief(this);
    }
}

class VengefulWarchiefTriggeredAbility extends TriggeredAbilityImpl {

    VengefulWarchiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private VengefulWarchiefTriggeredAbility(final VengefulWarchiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        VengefulWarchiefWatcher watcher = game.getState().getWatcher(VengefulWarchiefWatcher.class);
        return watcher != null && watcher.timesLostLifeThisTurn(event.getTargetId()) < 2;
    }

    @Override
    public VengefulWarchiefTriggeredAbility copy() {
        return new VengefulWarchiefTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you lose life for the first time each turn, " + super.getRule();
    }
}

class VengefulWarchiefWatcher extends Watcher {

    private final Map<UUID, Integer> playersLostLife = new HashMap<>();

    VengefulWarchiefWatcher() {
        super(WatcherScope.GAME);
    }

    private VengefulWarchiefWatcher(final VengefulWarchiefWatcher watcher) {
        super(watcher);
        this.playersLostLife.putAll(watcher.playersLostLife);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_LIFE) {
            int timesLifeLost = playersLostLife.getOrDefault(event.getTargetId(), 0);
            timesLifeLost++;
            playersLostLife.put(event.getTargetId(), timesLifeLost);
        }
    }

    @Override
    public VengefulWarchiefWatcher copy() {
        return new VengefulWarchiefWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        playersLostLife.clear();
    }

    int timesLostLifeThisTurn(UUID playerId) {
        return playersLostLife.getOrDefault(playerId, 0);
    }
}
