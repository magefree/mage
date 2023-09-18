package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.watchers.common.DamageDoneWatcher;

/**
 *
 * @author weirddan455
 */
public final class BoardedWindow extends CardImpl {

    private static final BoardedWindowFilter filter = new BoardedWindowFilter();

    public BoardedWindow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Creatures attacking you get -1/-0.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(-1, 0, Duration.WhileOnBattlefield, filter, false)));

        // At the beginning of each end step, if you were dealt 4 or more damage this turn, exile Boarded Window.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new ExileSourceEffect(), TargetController.ANY, BoardedWindowCondition.instance, false
        ), new DamageDoneWatcher());
    }

    private BoardedWindow(final BoardedWindow card) {
        super(card);
    }

    @Override
    public BoardedWindow copy() {
        return new BoardedWindow(this);
    }
}

class BoardedWindowFilter extends FilterAttackingCreature {

    public BoardedWindowFilter() {
        super("creatures attacking you");
    }

    private BoardedWindowFilter(final BoardedWindowFilter filter) {
        super(filter);
    }

    @Override
    public BoardedWindowFilter copy() {
        return new BoardedWindowFilter(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID playerId, Ability source, Game game) {
        if (!super.match(permanent, playerId, source, game)) {
            return false;
        }

        for (CombatGroup group : game.getCombat().getGroups()) {
            for (UUID attacker : group.getAttackers()) {
                if (attacker.equals(permanent.getId())) {
                    UUID defenderId = group.getDefenderId();
                    if (defenderId.equals(playerId)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

enum BoardedWindowCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        DamageDoneWatcher watcher = game.getState().getWatcher(DamageDoneWatcher.class);
        return watcher != null && watcher.damageDoneTo(source.getControllerId(), 0, game) >= 4;
    }

    @Override
    public String toString() {
        return "you were dealt 4 or more damage this turn";
    }
}
