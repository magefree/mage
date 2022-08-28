package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;

/**
 * Sets the base power and toughness for a creature. E.g. Molten Spray, Primal Clay
 * @author Alex-Vasile
 */
public class SetBasePowerToughnessSourceEffect extends ContinuousEffectImpl {

    private int power;
    private int toughness;

    public SetBasePowerToughnessSourceEffect(int power, int toughness) {
        this(power, toughness, Duration.WhileOnBattlefield, SubLayer.CharacteristicDefining_7a);
    }

    public SetBasePowerToughnessSourceEffect(int power, int toughness, Duration duration, SubLayer subLayer) {
        super(duration, Layer.PTChangingEffects_7, subLayer, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
    }

    private SetBasePowerToughnessSourceEffect(final SetBasePowerToughnessSourceEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getPermanent(source.getSourceId());
        }
        if (mageObject == null) {
            return false;
        }

        mageObject.getPower().setModifiedBaseValue(power);
        mageObject.getToughness().setModifiedBaseValue(toughness);
        return true;
    }

    @Override
    public ContinuousEffect copy() {
        return new SetBasePowerToughnessSourceEffect(this);
    }
}
