package mage.cards.m;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.PhyrexianGolemToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public final class MalcatorPurityOverseer extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Artifacts that entered under your control this turn", MalcatorPurityOverseerValue.instance
    );

    public MalcatorPurityOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Malcator, Purity Overseer enters the battlefield, create a 3/3 colorless Phyrexian Golem artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PhyrexianGolemToken())));

        // At the beginning of your end step, if three or more artifacts entered the battlefield under your control this turn, create a 3/3 colorless Phyrexian Golem artifact creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new PhyrexianGolemToken()), TargetController.YOU,
                MalcatorPurityOverseerCondition.instance, false
        ), new MalcatorPurityOverseerWatcher());
    }

    private MalcatorPurityOverseer(final MalcatorPurityOverseer card) {
        super(card);
    }

    @Override
    public MalcatorPurityOverseer copy() {
        return new MalcatorPurityOverseer(this);
    }
}

enum MalcatorPurityOverseerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return MalcatorPurityOverseerWatcher.checkPlayer(source, game);
    }

    @Override
    public String toString() {
        return "three or more artifacts entered the battlefield under your control this turn";
    }
}

enum MalcatorPurityOverseerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return MalcatorPurityOverseerWatcher.getCount(sourceAbility, game);
    }

    @Override
    public MalcatorPurityOverseerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class MalcatorPurityOverseerWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    MalcatorPurityOverseerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && ((EntersTheBattlefieldEvent) event).getTarget().isArtifact(game)) {
            map.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPlayer(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(MalcatorPurityOverseerWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0) >= 3;
    }

    static int getCount(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(MalcatorPurityOverseerWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0);
    }
}
