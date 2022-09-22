package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class ResourcefulDefense extends CardImpl {

    public static final FilterControlledPermanent filter2 = new FilterControlledPermanent("another permanent");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public ResourcefulDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever a permanent you control leaves the battlefield, if it had counters on it, put those counters on target permanent you control.
        Ability ltbAbility = new ResourcefulDefenseTriggeredAbility();
        ltbAbility.addTarget(new TargetControlledPermanent(filter2));
        this.addAbility(ltbAbility);

        // {4}{W}: Move any number of counters from target permanent you control to another target permanent you control.
        Ability ability = new SimpleActivatedAbility(new ResourcefulDefenseMoveCounterEffect(), new ManaCostsImpl<>("{4}{W}"));

        Target fromTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT);
        fromTarget.setTargetTag(1);

        Target toTarget = new TargetPermanent(filter2);
        toTarget.setTargetTag(2);
        ability.addTarget(fromTarget);
        ability.addTarget(toTarget);

        this.addAbility(ability);
    }

    private ResourcefulDefense(final ResourcefulDefense card) {
        super(card);
    }

    @Override
    public ResourcefulDefense copy() {
        return new ResourcefulDefense(this);
    }
}

class ResourcefulDefenseMoveCounterEffect extends OneShotEffect {

    ResourcefulDefenseMoveCounterEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Move any number of counters from target permanent you control to another target permanent you control";
    }

    private ResourcefulDefenseMoveCounterEffect(final ResourcefulDefenseMoveCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent fromPermanent = game.getPermanent(source.getFirstTarget());
        Permanent toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if(controller == null || fromPermanent == null || toPermanent == null) {
            return false;
        }

        // Counter name and how many to move
        Map<String, Integer> counterMap = new HashMap<>();
        for (Map.Entry<String, Counter> entry : fromPermanent.getCounters(game).entrySet()) {
            int num = controller.getAmount(
                    0,
                    entry.getValue().getCount(),
                    "Choose how many " + entry.getKey() +
                            " counters to remove from " + fromPermanent.getLogName(),
                    game);
            int newAmount = num + counterMap.getOrDefault(entry.getKey(), 0);
            counterMap.put(entry.getKey(), newAmount);
        }

        // Move the counters
        for (String counterName : counterMap.keySet()) {
            toPermanent.addCounters(
                    CounterType.findByName(counterName).createInstance(counterMap.get(counterName)),
                    source,
                    game);
            fromPermanent.removeCounters(counterName, counterMap.get(counterName), source, game);
            game.informPlayers(
                    controller.getLogName() + "moved " +
                    counterMap.get(counterName) + " " +
                    counterName + "counter" + (counterMap.get(counterName) > 1 ? "s" : "") +
                    "from " + fromPermanent.getLogName() +
                    "to " + toPermanent.getLogName() + "."
            );
        }

        return true;
    }

    @Override
    public Effect copy() {
        return new ResourcefulDefenseMoveCounterEffect(this);
    }
}

class ResourcefulDefenseTriggeredAbility extends LeavesBattlefieldAllTriggeredAbility {

    ResourcefulDefenseTriggeredAbility() {
        super(new ResourcefulDefenseLeaveEffect(), StaticFilters.FILTER_CONTROLLED_PERMANENT);
        setTriggerPhrase("Whenever a creature you control leaves the battlefield, if it had counters on it, ");
    }

    private ResourcefulDefenseTriggeredAbility(final ResourcefulDefenseTriggeredAbility ability) {
        super(ability);
    }

    public ResourcefulDefenseTriggeredAbility copy() {
        return new ResourcefulDefenseTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }

        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(permanent.getControllerId());
        if (controller == null) {
            return false;
        }

        Counters counters = permanent.getCounters(game);
        if (counters.values().stream().mapToInt(Counter::getCount).noneMatch(x -> x > 0)) {
            return false;
        }
        this.getEffects().setValue("counters", counters);
        return true;
    }
}

class ResourcefulDefenseLeaveEffect extends OneShotEffect {

    ResourcefulDefenseLeaveEffect() {
        super(Outcome.Benefit);
        staticText = "put those counters on target permanent you control";
    }

    private ResourcefulDefenseLeaveEffect(final ResourcefulDefenseLeaveEffect effect) {
        super(effect);
    }

    @Override
    public ResourcefulDefenseLeaveEffect copy() {
        return new ResourcefulDefenseLeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Counters counters = (Counters) this.getValue("counters");
        counters.values()
                .stream().filter(counter -> counter.getCount() > 0)
                .forEach(counter -> permanent.addCounters(counter, source.getControllerId(), source, game));
        return true;
    }
}