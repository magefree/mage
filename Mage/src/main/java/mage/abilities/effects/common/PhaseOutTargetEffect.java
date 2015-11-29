/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public class PhaseOutTargetEffect extends OneShotEffect {

    public PhaseOutTargetEffect() {
        super(Outcome.Detriment);
    }

    public PhaseOutTargetEffect(final PhaseOutTargetEffect effect) {
        super(effect);
    }

    @Override
    public PhaseOutTargetEffect copy() {
        return new PhaseOutTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                permanent.phaseOut(game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText.length() > 0) {
            return staticText + " phases out";
        }

        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() > 1) {
            if (target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
                return CardUtil.numberToText(target.getNumberOfTargets()) + " target " + target.getTargetName() + "s phase out";
            } else {
                return "up to " + CardUtil.numberToText(target.getMaxNumberOfTargets()) + " target " + target.getTargetName() + "s phase out";
            }
        } else if (target.getMaxNumberOfTargets() == 0){
            return "X target " + mode.getTargets().get(0).getTargetName() + " phase out";
        } else {
            return "target " + mode.getTargets().get(0).getTargetName() + " phase out";
        }
    }

}