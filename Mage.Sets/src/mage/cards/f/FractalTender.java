package mage.cards.f;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FractalToken;
import mage.watchers.Watcher;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.keyword.IncrementAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FractalTender extends CardImpl {

    public FractalTender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Increment
        this.addAbility(new IncrementAbility());

        // At the beginning of each end step, if you put a counter on this creature this turn, create a 0/0 green and blue Fractal creature token and put three +1/+1 counters on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.ANY,
            FractalToken.getEffect(StaticValue.get(3), " and put three +1/+1 counters on it"),
            false,
            FractalTenderCondition.instance
        ).addHint(FractalTenderCondition.getHint()), new FractalTenderWatcher());
    }

    private FractalTender(final FractalTender card) {
        super(card);
    }

    @Override
    public FractalTender copy() {
        return new FractalTender(this);
    }
}

enum FractalTenderCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return FractalTenderWatcher.check(source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you put a counter on this creature this turn";
    }
}

class FractalTenderWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> map = new HashMap<>();

    FractalTenderWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER_ADDED || event.getAmount() < 1) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            map.computeIfAbsent(new MageObjectReference(permanent, game), k -> new HashSet<>()).add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean check(UUID permanentId, UUID playerId, Game game) {
        FractalTenderWatcher watcher = game.getState().getWatcher(FractalTenderWatcher.class);
        if (watcher == null) {
            return false;
        }
        return watcher.map.getOrDefault(new MageObjectReference(permanentId, game), Collections.emptySet()).contains(playerId);
    }
}
