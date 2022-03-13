package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class DetainAllEffect extends OneShotEffect {

    private FilterPermanent filter = new FilterPermanent();

    public DetainAllEffect(FilterPermanent filter) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.staticText = "detain " + filter.getMessage();
    }

    public DetainAllEffect(final DetainAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public DetainAllEffect copy() {
        return new DetainAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<FixedTarget> detainedObjects = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (!game.isSimulation()) {
                game.informPlayers("Detained permanent: " + permanent.getName());
            }
            FixedTarget fixedTarget = new FixedTarget(permanent, game);
            detainedObjects.add(fixedTarget);
        }

        game.addEffect(new DetainAllRestrictionEffect(detainedObjects), source);
        return false;
    }
}

class DetainAllRestrictionEffect extends RestrictionEffect {

    private final List<FixedTarget> detainedObjects;

    public DetainAllRestrictionEffect(List<FixedTarget> detainedObjects) {
        super(Duration.Custom);
        this.detainedObjects = detainedObjects;
        staticText = "";
    }

    public DetainAllRestrictionEffect(final DetainAllRestrictionEffect effect) {
        super(effect);
        this.detainedObjects = effect.detainedObjects;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (FixedTarget fixedTarget : this.detainedObjects) {
            Permanent permanent = game.getPermanent(fixedTarget.getFirst(game, source));
            if (permanent != null) {
                permanent.addInfo("detain" + getId(), "[Detained]", game);
            }
        }
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE) {
            if (game.isActivePlayer(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                for (FixedTarget fixedTarget : this.detainedObjects) {
                    Permanent permanent = game.getPermanent(fixedTarget.getFirst(game, source));
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
        for (FixedTarget fixedTarget : this.detainedObjects) {
            UUID targetId = fixedTarget.getFirst(game, source);
            if (targetId != null && targetId.equals(permanent.getId())) {
                return true;
            }
        }
        return false;
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
    public DetainAllRestrictionEffect copy() {
        return new DetainAllRestrictionEffect(this);
    }

}
