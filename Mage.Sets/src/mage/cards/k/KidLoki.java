package mage.cards.k;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

public final class KidLoki extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            "each creature you control that you've put one or more +1/+1 counters on this turn"
    );

    static {
        filter.add(CountersThisTurnPredicate.instance);
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
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )), new KidLokiWatcher());

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

class CountersThisTurnPredicate implements Predicate<Permanent> {
    static final CountersThisTurnPredicate instance = new CountersThisTurnPredicate();

    CountersThisTurnPredicate() {
    }

    @Override
    public boolean apply(Permanent permanent, Game game) {
        UUID playerId = permanent.getControllerId();
        return KidLokiWatcher.checkPermanentHasCountersAddedBy(playerId, permanent.getId(), game);
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

class KidLokiWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> map = new HashMap<>();

    KidLokiWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER_ADDED
                || !event.getData().equals(CounterType.P1P1.getName())) {
            return;
        }
        MageObjectReference ref = new MageObjectReference(event.getTargetId(), game);
        map.computeIfAbsent(ref, k -> new HashSet<>()).add(event.getPlayerId());
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPermanentHasCountersAddedBy(UUID playerId, UUID permanentId, Game game) {
        KidLokiWatcher watcher = game.getState().getWatcher(KidLokiWatcher.class);
        if (watcher == null) {
            return false;
        }
        Set<UUID> players = watcher.map.get(new MageObjectReference(permanentId, game));
        return players != null && players.contains(playerId);
    }
}
