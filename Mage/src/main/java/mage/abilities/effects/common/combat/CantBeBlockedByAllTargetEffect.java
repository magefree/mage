package mage.abilities.effects.common.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.StaticHint;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantBeBlockedByAllTargetEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filterBlockedBy;

    public CantBeBlockedByAllTargetEffect(FilterCreaturePermanent filterBlockedBy, Duration duration) {
        super(duration);
        this.filterBlockedBy = filterBlockedBy;
    }

    protected CantBeBlockedByAllTargetEffect(final CantBeBlockedByAllTargetEffect effect) {
        super(effect);
        this.filterBlockedBy = effect.filterBlockedBy;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !filterBlockedBy.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public CantBeBlockedByAllTargetEffect copy() {
        return new CantBeBlockedByAllTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        return generateText(getTargetPointer().describeTargets(mode.getTargets(), "it"));
    }

    private String generateText(String targetText) {
        return targetText
                + " can't be blocked "
                + (duration == Duration.EndOfTurn ? "this turn " : "")
                + (filterBlockedBy.getMessage().startsWith("except by") ? "" : "by ")
                + filterBlockedBy.getMessage();
    }

    @Override
    public boolean hasHint() {
        return true;
    }

    @Override
    public List<Hint> getAffectedHints(Permanent permanent, Ability source, Game game) {
        if (!applies(permanent, source, game)) {
            return Collections.emptyList();
        }

        return Arrays.asList(new StaticHint(generateText("{this}")));
    }
}
