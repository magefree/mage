package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.LanderToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BioengineeredFuture extends CardImpl {

    public BioengineeredFuture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // When this enchantment enters, create a Lander token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new LanderToken())));

        // Each creature you control enters with an additional +1/+1 counter on it for each land that entered the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(new BioengineeredFutureEffect())
                .addHint(BioengineeredFutureHint.instance), new BioengineeredFutureWatcher());
    }

    private BioengineeredFuture(final BioengineeredFuture card) {
        super(card);
    }

    @Override
    public BioengineeredFuture copy() {
        return new BioengineeredFuture(this);
    }
}

class BioengineeredFutureEffect extends ReplacementEffectImpl {

    BioengineeredFutureEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "each creature you control enters with an additional +1/+1 counter on it " +
                "for each land that entered the battlefield under your control this turn";
    }

    private BioengineeredFutureEffect(final BioengineeredFutureEffect effect) {
        super(effect);
    }

    @Override
    public BioengineeredFutureEffect copy() {
        return new BioengineeredFutureEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.isCreature(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        int count = BioengineeredFutureWatcher.getCount(game, source);
        if (creature != null && count > 0) {
            creature.addCounters(
                    CounterType.P1P1.createInstance(count), source.getControllerId(),
                    source, game, event.getAppliedEffects()
            );
        }
        return false;
    }
}

class BioengineeredFutureWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    BioengineeredFutureWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null && permanent.isLand(game)) {
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
                .getWatcher(BioengineeredFutureWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0);
    }
}

enum BioengineeredFutureHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        return "Lands that entered under your control this turn: " + BioengineeredFutureWatcher.getCount(game, ability);
    }

    @Override
    public Hint copy() {
        return this;
    }
}
