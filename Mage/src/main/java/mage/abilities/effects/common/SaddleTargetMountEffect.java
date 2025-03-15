package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Jmlundeen
 */
public class SaddleTargetMountEffect extends OneShotEffect {

    public SaddleTargetMountEffect() {
        super(Outcome.Benefit);
        staticText = "Target Mount you control becomes saddled until end of turn";
    }

    public SaddleTargetMountEffect(String rule) {
        super(Outcome.Benefit);
        staticText = rule;
    }

    protected SaddleTargetMountEffect(final SaddleTargetMountEffect effect) {
        super(effect);
    }

    @Override
    public SaddleTargetMountEffect copy() {
        return new SaddleTargetMountEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return SaddleAbility.applySaddle(game.getPermanent(getTargetPointer().getFirst(game, source)), game);
    }

}
