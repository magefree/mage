package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantAttackTargetEffect extends RestrictionEffect {

    public CantAttackTargetEffect(Duration duration) {
        super(duration);
    }

    protected CantAttackTargetEffect(final CantAttackTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(targetPointer.getFirst(game, source));
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantAttackTargetEffect copy() {
        return new CantAttackTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it") +
                " can't attack" +
                (duration == Duration.EndOfTurn ? " this turn" : "" ) +
                (duration == Duration.UntilYourNextTurn ? " until your next turn" : "" );
    }
}
