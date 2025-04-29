package mage.cards.o;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverclockedElectromancer extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature, if that creature was dealt excess damage this turn");

    static {
        filter.add(OverclockedElectromancerPredicate.instance);
    }

    public OverclockedElectromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may pay {E}{E}{E}. If you do, put a +1/+1 counter on Overclocked Electromancer.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new PayEnergyCost(3)
        )));

        // Whenever Overclocked Electromancer attacks, double its power until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                SourcePermanentPowerValue.ALLOW_NEGATIVE, StaticValue.get(0), Duration.EndOfTurn
        ).setText("double its power until end of turn")));

        // Whenever Overclocked Electromancer deals combat damage to a creature, if that creature was dealt excess damage this turn, you get X {E}, where X is that excess damage.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(
                new GetEnergyCountersControllerEffect(OverclockedElectromancerValue.instance)
                        .setText("you get X {E}, where X is that excess damage"),
                true, false, true, filter
        ), new OverclockedElectromancerWatcher());
    }

    private OverclockedElectromancer(final OverclockedElectromancer card) {
        super(card);
    }

    @Override
    public OverclockedElectromancer copy() {
        return new OverclockedElectromancer(this);
    }
}

enum OverclockedElectromancerPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return OverclockedElectromancerWatcher.getAmount(input, game) > 0;
    }
}

enum OverclockedElectromancerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return OverclockedElectromancerWatcher.getAmount((Permanent) effect.getValue("damagedCreature"), game);
    }

    @Override
    public OverclockedElectromancerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class OverclockedElectromancerWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> map = new HashMap<>();

    OverclockedElectromancerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT) {
            return;
        }
        int excess = ((DamagedPermanentEvent) event).getExcess();
        if (excess > 0) {
            map.compute(
                    new MageObjectReference(event.getTargetId(), game),
                    (mor, i) -> i == null ? excess : Integer.sum(i, excess)
            );
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    public static int getAmount(Permanent permanent, Game game) {
        return permanent != null ?
                game.getState()
                        .getWatcher(OverclockedElectromancerWatcher.class)
                        .map
                        .getOrDefault(new MageObjectReference(permanent, game), 0)
                : 0;
    }
}
