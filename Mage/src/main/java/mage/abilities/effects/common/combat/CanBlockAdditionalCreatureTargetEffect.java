package mage.abilities.effects.common.combat;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
import java.util.List;

/**
 * @author mzulch
 */
public class CanBlockAdditionalCreatureTargetEffect extends ContinuousEffectImpl {

    protected int amount;

    /**
     * Need to set text manually
     * Target can block an additional creature this turn
     */
    public CanBlockAdditionalCreatureTargetEffect() {
        this(Duration.EndOfTurn, 1);
    }

    /**
     * Need to set text manually
     * @param duration of effect
     * @param amount 0 = any number
     */
    public CanBlockAdditionalCreatureTargetEffect(Duration duration, int amount) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
    }

    protected CanBlockAdditionalCreatureTargetEffect(final CanBlockAdditionalCreatureTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CanBlockAdditionalCreatureTargetEffect copy() {
        return new CanBlockAdditionalCreatureTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            if (amount > 0) {
                if (permanent.getMaxBlocks() > 0) {
                    permanent.setMaxBlocks(permanent.getMaxBlocks() + amount);
                }
            } else {
                permanent.setMaxBlocks(0);
            }
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent target = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        return target != null ? Collections.singletonList(target) : Collections.emptyList();
    }
}
