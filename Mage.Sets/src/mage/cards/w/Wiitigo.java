package mage.cards.w;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class Wiitigo extends CardImpl {

    public Wiitigo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");

        this.subtype.add(SubType.YETI);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Wiitigo enters the battlefield with six +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(6)), "with six +1/+1 counters on it"));

        // At the beginning of your upkeep, put a +1/+1 counter on Wiitigo if it has blocked or been blocked since your last upkeep. Otherwise, remove a +1/+1 counter from it.
        Ability triggeredAbility = new BeginningOfUpkeepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                        new RemoveCounterSourceEffect(CounterType.P1P1.createInstance(1)),
                        new BlockedOrBeenBlockedSinceYourLastUpkeepCondition(),
                        "put a +1/+1 counter on {this} if it has blocked or been blocked since your last "
                        + "upkeep. Otherwise, remove a +1/+1 counter from it"),
                TargetController.YOU,
                false);
        triggeredAbility.addWatcher(new BlockedOrBeenBlockedSinceYourLastUpkeepWatcher());
        this.addAbility(triggeredAbility);
    }

    private Wiitigo(final Wiitigo card) {
        super(card);
    }

    @Override
    public Wiitigo copy() {
        return new Wiitigo(this);
    }
}

class BlockedOrBeenBlockedSinceYourLastUpkeepCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent wiitigo = game.getBattlefield().getPermanent(source.getSourceId());
        BlockedOrBeenBlockedSinceYourLastUpkeepWatcher watcher = game.getState().getWatcher(
                BlockedOrBeenBlockedSinceYourLastUpkeepWatcher.class);
        if (wiitigo != null
                && watcher != null) {
            return watcher.blockedOrBeenBlockedSinceLastUpkeep(
                    new MageObjectReference(wiitigo.getId(),
                            wiitigo.getZoneChangeCounter(game),
                            game),
                    wiitigo.getControllerId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "it blocked or was blocked since your last upkeep";
    }
}

class BlockedOrBeenBlockedSinceYourLastUpkeepWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> blockedOrBeenBlockedCreatures = new HashMap<>();

    public BlockedOrBeenBlockedSinceYourLastUpkeepWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_POST) {
            blockedOrBeenBlockedCreatures.put(event.getPlayerId(), new HashSet<>()); // clear the watcher after upkeep
        } else if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            MageObjectReference morBlocker = new MageObjectReference(event.getSourceId(), game); // store blocker
            MageObjectReference morAttackerBlocked = new MageObjectReference(event.getTargetId(), game); // store attacker blocked
            for (UUID player : game.getPlayerList()) {
                if (!blockedOrBeenBlockedCreatures.containsKey(player)) {
                    blockedOrBeenBlockedCreatures.put(player, new HashSet<>());
                }
                blockedOrBeenBlockedCreatures.get(player).add(morBlocker);
                blockedOrBeenBlockedCreatures.get(player).add(morAttackerBlocked);
            }
        }
    }

    public boolean blockedOrBeenBlockedSinceLastUpkeep(MageObjectReference mor, UUID player) {
        return (blockedOrBeenBlockedCreatures.get(player) != null)
                && blockedOrBeenBlockedCreatures.get(player).contains(mor);
    }
}
