package mage.cards.b;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class BaneclawMarauder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("a creature blocking {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
        filter2.add(BaneclawMarauderPredicate.instance);
    }

    public BaneclawMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Whenever Baneclaw Marauder becomes blocked, each creature blocking it gets -1/-1 until end of turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        ).setText("each creature blocking it gets -1/-1 until end of turn"), false));

        // Whenever a creature blocking Baneclaw Marauder dies, its controller loses 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new LoseLifeTargetControllerEffect(1)
                        .setText("that creature's controller loses 1 life"),
                false, filter2, true
        ), new BaneclawMarauderWatcher());

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private BaneclawMarauder(final BaneclawMarauder card) {
        super(card);
    }

    @Override
    public BaneclawMarauder copy() {
        return new BaneclawMarauder(this);
    }
}

enum BaneclawMarauderPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return BaneclawMarauderWatcher.check(input.getSourceId(), input.getObject(), game);
    }
}

class BaneclawMarauderWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> blockerMap = new HashMap<>();

    BaneclawMarauderWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BLOCKER_DECLARED:
                blockerMap
                        .computeIfAbsent(new MageObjectReference(event.getTargetId(), game), x -> new HashSet<>())
                        .add(new MageObjectReference(event.getSourceId(), game));
                return;
            case END_COMBAT_STEP_POST:
                blockerMap.clear();
                return;
            case REMOVED_FROM_COMBAT:
                blockerMap
                        .values()
                        .stream()
                        .forEach(set -> set.removeIf(mor -> mor.refersTo(event.getTargetId(), game)));
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockerMap.clear();
    }

    static boolean check(UUID sourceId, Permanent blocker, Game game) {
        return game.getState()
                .getWatcher(BaneclawMarauderWatcher.class)
                .blockerMap
                .getOrDefault(new MageObjectReference(sourceId, game), Collections.emptySet())
                .stream()
                .anyMatch(mor -> mor.refersTo(blocker, game));
    }
}
