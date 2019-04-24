
package mage.abilities.effects.common;

import java.util.Set;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 * !!!! This effect needs the adding of the watcher in the using card class
 *
 * this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());
 *
 * @author LevelX2
 */
public class UntapAllThatAttackedEffect extends OneShotEffect {

    public UntapAllThatAttackedEffect() {
        super(Outcome.Benefit);
        staticText = "Untap all creatures that attacked this turn";
    }

    public UntapAllThatAttackedEffect(final UntapAllThatAttackedEffect effect) {
        super(effect);
    }

    @Override
    public UntapAllThatAttackedEffect copy() {
        return new UntapAllThatAttackedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
        if (watcher instanceof AttackedThisTurnWatcher) {
            Set<MageObjectReference> attackedThisTurn = ((AttackedThisTurnWatcher) watcher).getAttackedThisTurnCreatures();
            for (MageObjectReference mor : attackedThisTurn) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null && permanent.isCreature()) {
                    permanent.untap(game);
                }
            }
            return true;
        }
        return false;
    }

}
