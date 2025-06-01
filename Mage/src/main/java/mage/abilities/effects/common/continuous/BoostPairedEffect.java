
package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;

/**
 * @author noxx
 */
public class BoostPairedEffect extends ContinuousEffectImpl {

    private int power;
    private int toughness;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.addPower(power);
            permanent.addToughness(toughness);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Permanent paired = Optional.ofNullable(permanent.getPairedCard())
                    .map(pairedCard -> pairedCard.getPermanent(game))
                    .orElse(null);
            if (paired != null) {
                return Arrays.asList(permanent, paired);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public BoostPairedEffect copy() {
        return new BoostPairedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getPairedCard() != null) {
            Permanent paired = permanent.getPairedCard().getPermanent(game);
            if (paired != null) {
                permanent.addPower(power);
                permanent.addToughness(toughness);
                paired.addPower(power);
                paired.addToughness(toughness);
                return true;
            }
        }
        return false;
    }

}
