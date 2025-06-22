package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.FilterOpponent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DiseasedVermin extends CardImpl {

    private static final FilterOpponent filter = new FilterOpponent("player previously dealt damage by {this}");

    static {
        filter.add(new DiseasedVerminPredicate());
    }

    public DiseasedVermin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Diseased Vermin deals combat damage to a player, put an infection counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(
                        CounterType.INFECTION.createInstance()),
                false));

        // At the beginning of your upkeep, Diseased Vermin deals X damage to target opponent previously dealt damage by it, where X is the number of infection counters on it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(new CountersSourceCount(CounterType.INFECTION))
                .setText("{this} deals X damage to target opponent previously dealt damage by it, where X is the number of infection counters on it"));
        ability.addTarget(new TargetPlayer(filter));
        ability.addWatcher(new DiseasedVerminWatcher());
        this.addAbility(ability);
    }

    private DiseasedVermin(final DiseasedVermin card) {
        super(card);
    }

    @Override
    public DiseasedVermin copy() {
        return new DiseasedVermin(this);
    }
}

class DiseasedVerminPredicate implements ObjectSourcePlayerPredicate<Player> {

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        DiseasedVerminWatcher watcher = game.getState().getWatcher(DiseasedVerminWatcher.class);
        if (watcher != null) {
            return watcher.hasSourceDoneDamage(input.getObject().getId(), game);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(Player previously dealt damage by {this})";
    }
}

class DiseasedVerminWatcher extends Watcher {

    // does not reset!!
    private final Set<UUID> damagedPlayers;

    public DiseasedVerminWatcher() {
        super(WatcherScope.GAME);
        damagedPlayers = new HashSet<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && event.getSourceId() == sourceId) {
            damagedPlayers.add(event.getTargetId());
        }
    }

    public boolean hasSourceDoneDamage(UUID playerId, Game game) {
        return damagedPlayers.contains(playerId);
    }
}
