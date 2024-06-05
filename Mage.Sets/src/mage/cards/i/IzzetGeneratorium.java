package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class IzzetGeneratorium extends CardImpl {

    private static final Condition condition = new IzzetGeneratoriumCondition();
    private static final Hint hint = new ValueHint("{E} paid or lost this turn", IzzetGeneratoriumValue.instance);

    public IzzetGeneratorium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}{R}");

        // If you would get one or more {E}, you get that many plus one {E} instead.
        this.addAbility(new SimpleStaticAbility(new IzzetGeneratoriumReplacementEffect()));

        // {T}: Draw a card. Activate only if you've paid or lost four or more {E} this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapSourceCost(),
                condition
        ).addHint(hint), new IzzetGeneratoriumWatcher());
    }

    private IzzetGeneratorium(final IzzetGeneratorium card) {
        super(card);
    }

    @Override
    public IzzetGeneratorium copy() {
        return new IzzetGeneratorium(this);
    }
}

class IzzetGeneratoriumReplacementEffect extends ReplacementEffectImpl {

    IzzetGeneratoriumReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would get one or more {E}, you get that many plus one {E} instead";
    }

    private IzzetGeneratoriumReplacementEffect(final IzzetGeneratoriumReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowInc(event.getAmount(), 1), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getTargetId())
                && CounterType.ENERGY.getName().equals(event.getData())
                && event.getAmount() > 0;
    }

    @Override
    public IzzetGeneratoriumReplacementEffect copy() {
        return new IzzetGeneratoriumReplacementEffect(this);
    }
}

class IzzetGeneratoriumCondition extends IntCompareCondition {

    IzzetGeneratoriumCondition() {
        super(ComparisonType.OR_GREATER, 4);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return IzzetGeneratoriumValue.instance.calculate(game, source, null);
    }

    @Override
    public String toString() {
        return "if you've paid or lost four or more {E} this turn";
    }
}

enum IzzetGeneratoriumValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return IzzetGeneratoriumWatcher.getAmountEnergyLostOrSpentThisTurn(game, sourceAbility.getControllerId());
    }

    @Override
    public IzzetGeneratoriumValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "{E} spent or lost this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class IzzetGeneratoriumWatcher extends Watcher {

    // player -> amount of energy spent or lost this turn
    private final Map<UUID, Integer> energyLostOrSpent = new HashMap<>();

    IzzetGeneratoriumWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTERS_REMOVED) {
            return;
        }
        if (!event.getData().equals(CounterType.ENERGY.getName())) {
            return;
        }
        int amount = event.getAmount();
        if (amount <= 0) {
            return;
        }
        energyLostOrSpent.compute(event.getTargetId(), (k, i) -> i == null ? amount : Integer.sum(i, amount));
    }

    @Override
    public void reset() {
        super.reset();
        energyLostOrSpent.clear();
    }

    public static int getAmountEnergyLostOrSpentThisTurn(Game game, UUID playerId) {
        IzzetGeneratoriumWatcher watcher = game.getState().getWatcher(IzzetGeneratoriumWatcher.class);
        return watcher == null ? 0 : watcher.energyLostOrSpent.getOrDefault(playerId, 0);
    }
}