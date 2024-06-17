package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCountersFromSourceToTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class DiamondCity extends CardImpl {

    public DiamondCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Dapper Shieldmate enters the battlefield with a shield counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance(1)),
                "with a shield counter on it. <i>(If it would be dealt damage " +
                        "or destroyed, remove a shield counter from it instead.)</i>"
        ));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Move a shield counter from Diamond City onto target creature. Activate only if two or more creatures entered the battlefield under your control this turn.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new MoveCountersFromSourceToTargetEffect(CounterType.SHIELD),
                new TapSourceCost(), DiamondCityCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addHint(DiamondCityCondition.getHint());
        this.addAbility(ability, new DiamondCityWatcher());
    }

    private DiamondCity(final DiamondCity card) {
        super(card);
    }

    @Override
    public DiamondCity copy() {
        return new DiamondCity(this);
    }
}

//Based on Celebration Condition/Watcher
class DiamondCityWatcher extends Watcher {

    // playerId -> number of creatures entered the battlefield this turn under that player's control.
    private final Map<UUID, Integer> creaturesEnteredCounts = new HashMap<>();

    public DiamondCityWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }

        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            creaturesEnteredCounts.compute(permanent.getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    public boolean conditionActive(UUID playerId) {
        return creaturesEnteredCounts.getOrDefault(playerId, 0) >= 2;
    }

    @Override
    public void reset() {
        super.reset();
        creaturesEnteredCounts.clear();
    }
}

enum DiamondCityCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "You had two or more creatures enter this turn");

    @Override
    public boolean apply(Game game, Ability source) {
        DiamondCityWatcher watcher = game.getState().getWatcher(DiamondCityWatcher.class);
        return watcher != null && watcher.conditionActive(source.getControllerId());
    }

    @Override
    public String toString() {
        return "two or more creatures entered the battlefield under your control this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}
