
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public class BoostPairedEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;

    public BoostPairedEffect(int power, int toughness) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.staticText = "As long as {this} is paired with another creature, each of those creatures gets +"
                + power + "/+" + toughness;
    }

    protected BoostPairedEffect(final BoostPairedEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public BoostPairedEffect copy() {
        return new BoostPairedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && permanent.getPairedMOR() != null) {
            Permanent paired = permanent.getPairedMOR().getPermanent(game);
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
