package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KithkinGreenWhiteToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Kinbinding extends CardImpl {

    public Kinbinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // Creatures you control get +X/+X, where X is the number of creatures that entered the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                KinbindingValue.instance, KinbindingValue.instance, Duration.WhileOnBattlefield
        )).addHint(KinbindingValue.getHint()), new KinbindingWatcher());

        // At the beginning of combat on your turn, create a 1/1 green and white Kithkin creature token.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new CreateTokenEffect(new KithkinGreenWhiteToken())));
    }

    private Kinbinding(final Kinbinding card) {
        super(card);
    }

    @Override
    public Kinbinding copy() {
        return new Kinbinding(this);
    }
}

enum KinbindingValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Creatures that entered under your control this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return KinbindingWatcher.getCount(game, sourceAbility);
    }

    @Override
    public KinbindingValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of creatures that entered the battlefield under your control this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class KinbindingWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    KinbindingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            map.compute(permanent.getControllerId(), CardUtil::setOrIncrementValue);
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
                .getWatcher(KinbindingWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0);
    }
}
