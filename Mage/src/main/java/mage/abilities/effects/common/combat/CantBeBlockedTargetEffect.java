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
import mage.target.Target;
import mage.util.CardUtil;

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
        this.staticText = null;
    }

    public CantBeBlockedTargetEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration, Outcome.Benefit);
        this.filter = filter;
        staticText = new StringBuilder("{this} can't be blocked ")
                .append(filter.getMessage().startsWith("except by") ? "" : "by ").append(filter.getMessage()).toString();
    }

    public CantBeBlockedTargetEffect(CantBeBlockedTargetEffect effect) {
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
        if (mode.getTargets().isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() != target.getNumberOfTargets()) {
            sb.append("up to ");
            if (target.getMaxNumberOfTargets() == 1) {
                sb.append("one ");
            }
        }
        if (target.getMaxNumberOfTargets() > 1) {
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
        }
        sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        if (target.getMaxNumberOfTargets() > 1) {
            sb.append("s can't be blocked");
        } else {
            sb.append(" can't be blocked");
        }

        if (Duration.EndOfTurn == this.duration) {
            sb.append(" this turn");
        }
        return sb.toString();
    }
}
