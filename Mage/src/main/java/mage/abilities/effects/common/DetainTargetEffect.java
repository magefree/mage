package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
//
//    701.26. Detain
//
//    701.26a Certain spells and abilities can detain a permanent. Until the next
//    turn of the controller of that spell or ability, that permanent can't attack
//    or block and its activated abilities can't be activated.
//
public class DetainTargetEffect extends OneShotEffect {

    public DetainTargetEffect() {
        super(Outcome.LoseAbility);
    }

    public DetainTargetEffect(String ruleText) {
        super(Outcome.LoseAbility);
        staticText = ruleText;
    }

    public DetainTargetEffect(final DetainTargetEffect effect) {
        super(effect);
    }

    @Override
    public DetainTargetEffect copy() {
        return new DetainTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.isSimulation()) {
            for (UUID target : this.getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    game.informPlayers("Detained permanent: " + permanent.getName());
                }
            }
        }
        DetainRestrictionEffect effect = new DetainRestrictionEffect();
        game.addEffect(effect, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);

        if (target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
            if (target.getMaxNumberOfTargets() == 1) {
                sb.append("detain target ").append(target.getTargetName());
            } else {
                sb.append("detain ").append(target.getMaxNumberOfTargets()).append(" target ").append(target.getTargetName());
            }
        } else {
            sb.append("detain up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName());
        }
        sb.append(". <i>(Until your next turn, ");
        boolean plural = target.getMaxNumberOfTargets() > 1;
        sb.append(plural ? "those " : "that ");
        sb.append(target.getTargetName().contains("creature") ? "creature" : "permanent");
        if (plural) {
            sb.append('s');
        }
        sb.append(" can't attack or block and ");
        sb.append(plural ? "their" : "its");
        sb.append(" activated abilities can't be activated.)</i>");
        return sb.toString();
    }
}

class DetainRestrictionEffect extends RestrictionEffect {

    public DetainRestrictionEffect() {
        super(Duration.Custom);
        staticText = "";
    }

    public DetainRestrictionEffect(final DetainRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addInfo("detain" + getId(), CardUtil.addToolTipMarkTags("Detained"), game);
            }
        }
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE) {
            if (game.isActivePlayer(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.addInfo("detain" + getId(), "", game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.targetPointer.getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public DetainRestrictionEffect copy() {
        return new DetainRestrictionEffect(this);
    }

}
