package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class CantBeBlockedTargetEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public CantBeBlockedTargetEffect() {
        this(Duration.EndOfTurn);
    }

    public CantBeBlockedTargetEffect(Duration duration) {
        this(StaticFilters.FILTER_PERMANENT_CREATURE, duration);
    }

    public CantBeBlockedTargetEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration, Outcome.Benefit);
        this.filter = filter;
    }

    protected CantBeBlockedTargetEffect(final CantBeBlockedTargetEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public CantBeBlockedTargetEffect copy() {
        return new CantBeBlockedTargetEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !filter.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that creature")
                + " can't be blocked"
                + (duration == Duration.EndOfTurn ? " this turn" : "");
    }
}
