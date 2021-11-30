package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThalisseReverentMedium extends CardImpl {

    private static final Hint hint = new ValueHint(
            "The number of tokens you created this turn",
            ThalisseReverentMediumValue.instance
    );

    public ThalisseReverentMedium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of each end step, create X 1/1 white Spirit creature tokens with flying, where X is the number of tokens you created this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(
                new SpiritWhiteToken(), ThalisseReverentMediumValue.instance
        ), TargetController.ANY, false).addHint(hint), new ThalisseReverentMediumWatcher());
    }

    private ThalisseReverentMedium(final ThalisseReverentMedium card) {
        super(card);
    }

    @Override
    public ThalisseReverentMedium copy() {
        return new ThalisseReverentMedium(this);
    }
}

enum ThalisseReverentMediumValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ThalisseReverentMediumWatcher watcher = game.getState().getWatcher(ThalisseReverentMediumWatcher.class);
        return watcher == null ? 0 : watcher.getTokenCount(sourceAbility.getControllerId());
    }

    @Override
    public ThalisseReverentMediumValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the number of tokens you created this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class ThalisseReverentMediumWatcher extends Watcher {

    private final Map<UUID, Integer> tokenMap = new HashMap<>();

    ThalisseReverentMediumWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CREATED_TOKEN) {
            return;
        }
        tokenMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        tokenMap.clear();
        super.reset();
    }

    int getTokenCount(UUID playerId) {
        return tokenMap.getOrDefault(playerId, 0);
    }
}
