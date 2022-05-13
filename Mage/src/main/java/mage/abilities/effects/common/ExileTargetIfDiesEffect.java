package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.DiesReplacementEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class ExileTargetIfDiesEffect extends OneShotEffect {

    public ExileTargetIfDiesEffect() {
        this("creature");
    }

    public ExileTargetIfDiesEffect(String targetName) {
        super(Outcome.Damage);
        this.staticText = "If that " + targetName + " would die this turn, exile it instead";
    }

    public ExileTargetIfDiesEffect(final ExileTargetIfDiesEffect effect) {
        super(effect);
    }

    @Override
    public ExileTargetIfDiesEffect copy() {
        return new ExileTargetIfDiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            game.addEffect(new DiesReplacementEffect(new MageObjectReference(targetCreature, game), Duration.EndOfTurn), source);
        }
        return true;
    }
}
