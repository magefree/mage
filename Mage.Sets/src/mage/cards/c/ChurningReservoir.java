package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.PhyrexianGoblinToken;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChurningReservoir extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("another target nontoken artifact or creature you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public ChurningReservoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        // At the beginning of your upkeep, put an oil counter on another target nontoken artifact or creature you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.OIL.createInstance()),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {2}, {T}: Create a 1/1 red Phyrexian Goblin creature token. Activate only if an oil counter was removed from a permanent you controlled this turn or a permanent with an oil counter on it was put into a graveyard this turn.
        ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new PhyrexianGoblinToken()),
                new GenericManaCost(2), ChurningReservoirCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new ChurningReservoirWatcher());
    }

    private ChurningReservoir(final ChurningReservoir card) {
        super(card);
    }

    @Override
    public ChurningReservoir copy() {
        return new ChurningReservoir(this);
    }
}

enum ChurningReservoirCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ChurningReservoirWatcher.checkPlayer(source, game);
    }

    @Override
    public String toString() {
        return "an oil counter was removed from a permanent you controlled this turn " +
                "or a permanent with an oil counter on it was put into a graveyard this turn";
    }
}

class ChurningReservoirWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    ChurningReservoirWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.isDiesEvent() && zEvent.getTarget().getCounters(game).containsKey(CounterType.OIL)) {
                    set.add(zEvent.getTarget().getControllerId());
                }
                return;
            case COUNTER_REMOVED:
                if (event.getData().equals(CounterType.OIL.getName())) {
                    condition = true;
                }
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Ability source, Game game) {
        ChurningReservoirWatcher watcher = game.getState().getWatcher(ChurningReservoirWatcher.class);
        return watcher.condition || watcher.set.contains(source.getControllerId());
    }
}
