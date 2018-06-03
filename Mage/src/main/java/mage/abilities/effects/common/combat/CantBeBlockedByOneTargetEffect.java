
package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public class CantBeBlockedByOneTargetEffect extends ContinuousEffectImpl {

    protected int amount;

    public CantBeBlockedByOneTargetEffect(int amount) {
        this(amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByOneTargetEffect(int amount, Duration duration) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        staticText = "Target creature can't be blocked except by " + amount + " or more creatures";
    }

    public CantBeBlockedByOneTargetEffect(final CantBeBlockedByOneTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CantBeBlockedByOneTargetEffect copy() {
        return new CantBeBlockedByOneTargetEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for(UUID targetId: getTargetPointer().getTargets(game, source)) {
            Permanent perm = game.getPermanent(targetId);
            if (perm != null) {
                perm.setMinBlockedBy(amount);
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}