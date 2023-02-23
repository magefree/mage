
package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;

/**
 * @author Susucr
 */
public class SetCardColorSourceEffect extends ContinuousEffectImpl {

    private ObjectColor setColor;

    public SetCardColorSourceEffect(ObjectColor setColor, Duration duration) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.setColor = setColor;
    }

    public SetCardColorSourceEffect(final SetCardColorSourceEffect effect) {
        super(effect);
        this.setColor = effect.setColor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            mageObject.getColor().setColor(this.setColor);
            return true;
        }

        return false;
    }

    @Override
    public SetCardColorSourceEffect copy() {
        return new SetCardColorSourceEffect(this);
    }
}
