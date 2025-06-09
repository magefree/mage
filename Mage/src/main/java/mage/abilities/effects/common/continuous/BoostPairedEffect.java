
package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author noxx
 */
public class BoostPairedEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;

    public BoostPairedEffect(int power, int toughness, String rule) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        staticText = rule;
    }

    protected BoostPairedEffect(final BoostPairedEffect effect) {
        super(effect);
        power = effect.power;
        toughness = effect.toughness;
    }

    @Override
    public BoostPairedEffect copy() {
        return new BoostPairedEffect(this);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getPairedCard() != null) {
            Permanent paired = permanent.getPairedCard().getPermanent(game);
            if (paired != null) {
                affectedObjects.add(permanent);
                affectedObjects.add(paired);
            }
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.addPower(power);
            permanent.addToughness(toughness);
        }
    }
}
