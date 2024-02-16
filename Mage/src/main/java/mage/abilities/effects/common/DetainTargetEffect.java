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

    protected DetainTargetEffect(final DetainTargetEffect effect) {
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
        game.addEffect(new DetainRestrictionEffect(), source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String description = getTargetPointer().describeTargets(mode.getTargets(), "that creature");
        boolean plural = getTargetPointer().isPlural(mode.getTargets());
        String reminder = ". <i>(Until your next turn, " + (plural ? "those " : "that ")
                + (description.contains("creature") ? "creature" : "permanent") + (plural ? "s" : "")
                + " can't attack or block and " + (plural ? "their" : "its")
                + " activated abilities can't be activated.)</i>";
        return "detain " + description + reminder;
    }
}

class DetainRestrictionEffect extends RestrictionEffect {

    DetainRestrictionEffect() {
        super(Duration.Custom);
        staticText = "";
    }

    private DetainRestrictionEffect(final DetainRestrictionEffect effect) {
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
