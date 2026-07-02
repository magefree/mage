package mage.cards.k;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mage.MageObjectReference;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.constants.WatcherScope;

/**
 *
 * @author nandmp
 */
public final class KidLoki extends CardImpl {

    // Filter enforces the current board-state requirement; the watcher records "this turn" history
    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control that you've put one or more +1/+1 counters on this turn");

    static {
        filter.add(KidLokiPredicate.instance);
    }

    public KidLoki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.HERO);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Each creature you control that you've put one or more +1/+1 counters on this turn has hexproof.
        Ability ability = new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter
                )
        );
        this.addAbility(ability, new KidLokiP1P1CountersAddedByPlayerThisTurn());

        // Whenever you draw your second card each turn, put a +1/+1 counter on Kid Loki.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, 2
        ));
    }

    private KidLoki(final KidLoki card) {
        super(card);
    }

    @Override
    public KidLoki copy() {
        return new KidLoki(this);
    }
}

enum KidLokiPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return KidLokiP1P1CountersAddedByPlayerThisTurn.checkPermanent(input, input.getControllerId(), game);
    }
}

class KidLokiP1P1CountersAddedByPlayerThisTurn extends Watcher {

    // For each player, remember permanents that received +1/+1 counters this turn.
    // MageObjectReference is used so lookups stay correct across zone-change counters.
    private final Map<UUID, Set<MageObjectReference>> countersAddedByPlayer = new HashMap<>();

    KidLokiP1P1CountersAddedByPlayerThisTurn() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        UUID playerId;
        Permanent permanent;
        // If we observed the object while it was still entering, store with +1 offset
        // so the later battlefield lookup can still match this object.
        int offset = 0;

        if (event.getType() == GameEvent.EventType.COUNTER_ADDED) {
            // Normal case: explicit +1/+1 counter add event.
            if (!CounterType.P1P1.getName().equals(event.getData())
                    || event.getPlayerId() == null) {
                return;
            }
            playerId = event.getPlayerId();
            permanent = game.getPermanent(event.getTargetId());
            if (permanent == null) {
                // Some counter events happen during ETB processing.
                permanent = game.getPermanentEntering(event.getTargetId());
                offset = 1;
            }
            if (permanent == null || !permanent.isCreature(game)) {
                return;
            }
        } else if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            // ETB fallback: some creatures enter with counters but do not emit COUNTER_ADDED.
            permanent = game.getPermanent(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
                offset = 1;
            }
            if (permanent == null
                    || !permanent.isCreature(game)
                    || !permanent.getCounters(game).containsKey(CounterType.P1P1)) {
                return;
            }
            // For ETB events, controller is the player who put the counters as it entered.
            playerId = permanent.getControllerId();
        } else {
            return;
        }

        if (playerId == null) {
            return;
        }

        countersAddedByPlayer
                .computeIfAbsent(playerId, x -> new HashSet<>())
                .add(new MageObjectReference(permanent, game, offset));
    }

    @Override
    public void reset() {
        super.reset();
        // "This turn" memory must clear every turn.
        countersAddedByPlayer.clear();
    }

    static boolean checkPermanent(Permanent permanent, UUID playerId, Game game) {
        KidLokiP1P1CountersAddedByPlayerThisTurn watcher = game.getState().getWatcher(KidLokiP1P1CountersAddedByPlayerThisTurn.class);
        if (watcher == null || permanent == null || playerId == null) {
            return false;
        }

        // Rebuild a MOR at current state and check whether this player marked it this turn.
        return watcher.countersAddedByPlayer
                .getOrDefault(playerId, Collections.emptySet())
                .contains(new MageObjectReference(permanent, game));
    }
}
