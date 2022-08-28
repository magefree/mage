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
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * Sets the base power and toughness for a creature. E.g. Molten Spray, Primal Clay
 * @author Alex-Vasile
 */
public class SetBasePowerSourceEffect extends ContinuousEffectImpl {

    private int power;
    private int toughness;

    public SetBasePowerSourceEffect(int power, int toughness) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
    }

    private SetBasePowerSourceEffect(final SetBasePowerSourceEffect effect) {
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
        return new SetBasePowerSourceEffect(this);
    }
}
