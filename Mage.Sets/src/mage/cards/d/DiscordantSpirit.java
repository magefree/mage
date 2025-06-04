package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiscordantSpirit extends CardImpl {

    public DiscordantSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each end step, if it's an opponent's turn, put a +1/+1 counter on this creature for each 1 damage dealt to you this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.OPPONENT,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), DiscordantSpiritValue.instance),
                false
        ).setTriggerPhrase("At the beginning of each end step, if it's an opponent's turn, ")
                .addHint(DiscordantSpiritValue.getHint()), new DiscordantSpiritWatcher());

        // At the beginning of your end step, remove all +1/+1 counters from this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.P1P1)));
    }

    private DiscordantSpirit(final DiscordantSpirit card) {
        super(card);
    }

    @Override
    public DiscordantSpirit copy() {
        return new DiscordantSpirit(this);
    }
}

enum DiscordantSpiritValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Damage dealt to you this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DiscordantSpiritWatcher.getCount(game, sourceAbility);
    }

    @Override
    public DiscordantSpiritValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "1 damage dealt to you this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class DiscordantSpiritWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    DiscordantSpiritWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            map.compute(event.getTargetId(), (u, i) -> i == null ? event.getAmount() : Integer.sum(i, event.getAmount()));
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getCount(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(DiscordantSpiritWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0);
    }
}
