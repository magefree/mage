package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import static mage.constants.Duration.EndOfTurn;

/**
 * @author LevelX2
 */
public class CantBeBlockedByAllTargetEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filterBlockedBy;

    public CantBeBlockedByAllTargetEffect(FilterCreaturePermanent filterBlockedBy, Duration duration) {
        super(duration);
        this.filterBlockedBy = filterBlockedBy;
        staticText = "target creature"
                + " can't be blocked "
                + (duration == EndOfTurn ? "this turn " : "")
                + (filterBlockedBy.getMessage().startsWith("except by") ? "" : "by ")
                + filterBlockedBy.getMessage();
    }

    public CantBeBlockedByAllTargetEffect(final CantBeBlockedByAllTargetEffect effect) {
        super(effect);
        this.filterBlockedBy = effect.filterBlockedBy;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !filterBlockedBy.match(blocker, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public CantBeBlockedByAllTargetEffect copy() {
        return new CantBeBlockedByAllTargetEffect(this);
    }
}
