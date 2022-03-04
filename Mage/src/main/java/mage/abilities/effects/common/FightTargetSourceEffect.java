package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 * @author LevelX2
 */
public class FightTargetSourceEffect extends OneShotEffect {

    public FightTargetSourceEffect() {
        super(Outcome.Damage);
    }

    public FightTargetSourceEffect(final FightTargetSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
            Permanent creature1 = game.getPermanent(getTargetPointer().getFirst(game, source));
            // 20110930 - 701.10
            if (creature1 != null && sourcePermanent != null) {
                if (creature1.isCreature(game) && sourcePermanent.isCreature(game)) {
                    return sourcePermanent.fight(creature1, source, game);
                }
            }
            if (!game.isSimulation()) {
                game.informPlayers(sourceObject.getLogName() + ": Fighting effect has been fizzled.");
            }
        }
        return false;
    }

    @Override
    public FightTargetSourceEffect copy() {
        return new FightTargetSourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        Target target = mode.getTargets().get(0);
        StringBuilder sb = new StringBuilder("{this} fights ");
        if (target.getMinNumberOfTargets() == 0 && target.getMaxNumberOfTargets() == 1) {
            sb.append("up to one ");
        }
        if (!target.getTargetName().contains("other")) {
            sb.append("target ");
        }
        sb.append(target.getTargetName());
        return sb.toString();
    }
}
