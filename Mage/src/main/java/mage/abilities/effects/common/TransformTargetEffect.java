/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class TransformTargetEffect extends OneShotEffect {

    private boolean withoutTrigger;

    public TransformTargetEffect() {
        this(true);
    }

    public TransformTargetEffect(boolean withoutTrigger) {
        super(Outcome.Transform);
        this.withoutTrigger = withoutTrigger;
    }

    public TransformTargetEffect(final TransformTargetEffect effect) {
        super(effect);
        this.withoutTrigger = effect.withoutTrigger;
    }

    @Override
    public TransformTargetEffect copy() {
        return new TransformTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (permanent.canTransform(source, game)) {
                // check not to transform twice the same side
                if (withoutTrigger) {
                    permanent.setTransformed(!permanent.isTransformed());
                } else {
                    permanent.transform(game);
                }
                if (!game.isSimulation()) {
                    if (permanent.isTransformed()) {
                        if (permanent.getSecondCardFace() != null) {
                            if (permanent instanceof PermanentCard) {
                                game.informPlayers(((PermanentCard) permanent).getCard().getLogName() + " transforms into " + permanent.getSecondCardFace().getLogName());
                            }
                        }
                    } else {
                        game.informPlayers(permanent.getSecondCardFace().getLogName() + " transforms into " + permanent.getLogName());
                    }
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().isEmpty()) {
            return "transform target";
        }
        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() > 1) {
            if (target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
                return "transform " + CardUtil.numberToText(target.getNumberOfTargets()) + " target " + target.getTargetName();
            } else {
                return "transform up to " + CardUtil.numberToText(target.getMaxNumberOfTargets()) + " target " + target.getTargetName();
            }
        } else {
            return "transform target " + mode.getTargets().get(0).getTargetName();
        }
    }
}
