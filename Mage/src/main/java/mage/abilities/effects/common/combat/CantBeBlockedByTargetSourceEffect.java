package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */

public class CantBeBlockedByTargetSourceEffect extends EvasionEffect {

    /**
     * Target creature(s) cant block the source creature
     *
     * @param duration
     */

    public CantBeBlockedByTargetSourceEffect(Duration duration) {
        super(duration);
        // TODO: this is not great hint. Better than nothing?
        this.staticCantBeBlockedMessage =
            new StringBuilder("can't be blocked by the [targetted creatures]")
                .append((duration == Duration.EndOfTurn ? " this turn" : ""))
                .toString();
    }

    protected CantBeBlockedByTargetSourceEffect(final CantBeBlockedByTargetSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return source.getSourceId().equals(permanent.getId());
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return this.getTargetPointer().getTargets(game, source).contains(blocker.getId());
    }

    @Override
    public CantBeBlockedByTargetSourceEffect copy() {
        return new CantBeBlockedByTargetSourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "it"))
            .append(" can't block {this}")
            .append((duration == Duration.EndOfTurn ? " this turn" : ""))
            .toString();
    }
}
