package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class FightTargetSourceEffect extends OneShotEffect {

    public FightTargetSourceEffect() {
        super(Outcome.Damage);
    }

    protected FightTargetSourceEffect(final FightTargetSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // 701.12
        Permanent sourceCreature = source.getSourcePermanentIfItStillExists(game);
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourceCreature == null || !sourceCreature.isCreature(game)
                || targetCreature == null || !targetCreature.isCreature(game)) {
            return false;
        }
        return sourceCreature.fight(targetCreature, source, game);
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
        return "{this} fights " + getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
