

package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MustBeBlockedByTargetSourceEffect extends RequirementEffect {

    public MustBeBlockedByTargetSourceEffect() {
        this(Duration.EndOfTurn);
    }

    public MustBeBlockedByTargetSourceEffect(Duration duration) {
        super(duration);
        staticText = "target creature blocks {this} this turn if able";
    }

    public MustBeBlockedByTargetSourceEffect(final MustBeBlockedByTargetSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(this.getTargetPointer().getFirst(game, source))) {
            Permanent blocker = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (blocker != null && blocker.canBlock(source.getSourceId(), game)) {              
                Permanent attacker = source.getSourcePermanentIfItStillExists(game);
                if (attacker != null) {
                    BlockedAttackerWatcher blockedAttackerWatcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
                    if (blockedAttackerWatcher != null && blockedAttackerWatcher.creatureHasBlockedAttacker(attacker, blocker, game)) {
                        // has already blocked this turn, so no need to do again
                        return false;
                    }                
                    return true;
                } else {
                    discard();
                }
            }
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
        return source.getSourceId();
    }

    @Override
    public MustBeBlockedByTargetSourceEffect copy() {
        return new MustBeBlockedByTargetSourceEffect(this);
    }

}
