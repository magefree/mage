package mage.cards.u;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.abilities.keyword.StationAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.abilities.keyword.StationLevelAbility;
import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class USSEnterpriseDGalaxyClass extends CardImpl {

    public USSEnterpriseDGalaxyClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Whenever one or more charge counters are put on U.S.S. Enterprise-D for the first time each turn, exile the top card of your library. You may play that card this turn.
        this.addAbility(new USSEnterpriseDGalaxyClassTriggeredAbility());

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // Vigilance
        // 4/5
        this.addAbility(new StationLevelAbility(7)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(VigilanceAbility.getInstance())
            .withPT(4, 5));
    }

    private USSEnterpriseDGalaxyClass(final USSEnterpriseDGalaxyClass card) {
        super(card);
    }

    @Override
    public USSEnterpriseDGalaxyClass copy() {
        return new USSEnterpriseDGalaxyClass(this);
    }
}

class USSEnterpriseDGalaxyClassTriggeredAbility extends TriggeredAbilityImpl {

    USSEnterpriseDGalaxyClassTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn));
        this.setTriggerPhrase("Whenever one or more charge counters are put on {this} for the first time each turn, ");
        this.addWatcher(new ChargeCountersAddedFirstTimeWatcher());
    }

    private USSEnterpriseDGalaxyClassTriggeredAbility(final USSEnterpriseDGalaxyClassTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public USSEnterpriseDGalaxyClassTriggeredAbility copy() {
        return new USSEnterpriseDGalaxyClassTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && this.getSourceId().equals(event.getTargetId())
                && event.getData().equals(CounterType.CHARGE.getName())
                && ChargeCountersAddedFirstTimeWatcher.checkEvent(event, permanent, game, 0);
    }
}

class ChargeCountersAddedFirstTimeWatcher extends Watcher {

    private final Map<MageObjectReference, UUID> map = new HashMap<>();

    public ChargeCountersAddedFirstTimeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTERS_ADDED) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        int offset = 0;
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
            offset++;
        }
        if (permanent != null && event.getData().equals(CounterType.CHARGE.getName())) {
            map.putIfAbsent(new MageObjectReference(permanent, game, offset), event.getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    public static boolean checkEvent(GameEvent event, Permanent permanent, Game game, int offset) {
        return event
                .getId()
                .equals(game
                        .getState()
                        .getWatcher(ChargeCountersAddedFirstTimeWatcher.class)
                        .map
                        .getOrDefault(new MageObjectReference(permanent, game, offset), null));
    }
}
