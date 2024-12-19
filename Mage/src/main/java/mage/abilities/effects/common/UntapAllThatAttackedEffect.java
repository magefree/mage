package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.Set;

/**
 * @author LevelX2
 */
public class UntapAllThatAttackedEffect extends OneShotEffect {

    public UntapAllThatAttackedEffect() {
        super(Outcome.Benefit);
        staticText = "Untap all creatures that attacked this turn";
    }

    protected UntapAllThatAttackedEffect(final UntapAllThatAttackedEffect effect) {
        super(effect);
    }

    @Override
    public UntapAllThatAttackedEffect copy() {
        return new UntapAllThatAttackedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher != null) {
            Set<MageObjectReference> attackedThisTurn = watcher.getAttackedThisTurnCreatures();
            for (MageObjectReference mor : attackedThisTurn) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null && permanent.isCreature(game)) {
                    permanent.untap(game);
                }
            }
            return true;
        }
        return false;
    }

}
