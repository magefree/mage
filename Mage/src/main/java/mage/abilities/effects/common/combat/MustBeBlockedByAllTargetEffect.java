

package mage.abilities.effects.common.combat;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.AbilityType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 * @author LevelX2
 */
public class MustBeBlockedByAllTargetEffect extends RequirementEffect {

    public MustBeBlockedByAllTargetEffect(Duration duration) {
        super(duration);
        staticText = "All creatures able to block target creature " +
                (this.getDuration() == Duration.EndOfTurn ? "this turn " : "") +
                "do so";
    }

    protected MustBeBlockedByAllTargetEffect(final MustBeBlockedByAllTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attackingCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (attackingCreature != null && attackingCreature.isAttacking()) {
            if (source.getAbilityType() != AbilityType.STATIC) {
                BlockedAttackerWatcher blockedAttackerWatcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
                if (blockedAttackerWatcher != null && blockedAttackerWatcher.creatureHasBlockedAttacker(attackingCreature, permanent, game)) {
                    // has already blocked this turn, so no need to do again
                    return false;
                }
            }
            return permanent.canBlock(this.getTargetPointer().getFirst(game, source), game);
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return this.getTargetPointer().getFirst(game, source);
    }

    @Override
    public MustBeBlockedByAllTargetEffect copy() {
        return new MustBeBlockedByAllTargetEffect(this);
    }

}
