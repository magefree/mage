package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.InsectToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IridescentHornbeetle extends CardImpl {

    public IridescentHornbeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, create a 1/1 green Insect creature token for each +1/+1 counter you've put on creatures under your control this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(
                new InsectToken(), IridescentHornbeetleValue.instance
        ), TargetController.YOU, false), new IridescentHornbeetleWatcher());
    }

    private IridescentHornbeetle(final IridescentHornbeetle card) {
        super(card);
    }

    @Override
    public IridescentHornbeetle copy() {
        return new IridescentHornbeetle(this);
    }
}

enum IridescentHornbeetleValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        IridescentHornbeetleWatcher watcher = game.getState().getWatcher(IridescentHornbeetleWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return watcher.getCounterCount(sourceAbility.getControllerId());
    }

    @Override
    public IridescentHornbeetleValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "+1/+1 counter you've put on creatures under your control this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class IridescentHornbeetleWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    IridescentHornbeetleWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER_ADDED
                || !event.getData().equals(CounterType.P1P1.getName())) {
            return;
        }
        playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    int getCounterCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }
}
