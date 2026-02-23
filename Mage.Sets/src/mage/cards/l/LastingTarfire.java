package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LastingTarfire extends CardImpl {

    public LastingTarfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of each end step, if you put a counter on a creature this turn, this enchantment deals 2 damage to each opponent.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new DamagePlayersEffect(2, TargetController.OPPONENT),
                false, LastingTarfireCondition.instance
        ).addHint(LastingTarfireCondition.getHint()), new LastingTarfireWatcher());
    }

    private LastingTarfire(final LastingTarfire card) {
        super(card);
    }

    @Override
    public LastingTarfire copy() {
        return new LastingTarfire(this);
    }
}

enum LastingTarfireCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return LastingTarfireWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you put a counter on a creature this turn";
    }
}

class LastingTarfireWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    LastingTarfireWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER_ADDED || event.getAmount() < 1) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            set.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(LastingTarfireWatcher.class)
                .set
                .contains(playerId);
    }
}
