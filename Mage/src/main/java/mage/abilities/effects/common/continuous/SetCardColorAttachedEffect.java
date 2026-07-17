package mage.abilities.effects.common.continuous;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class SetCardColorAttachedEffect extends ContinuousEffectImpl {

    private final ObjectColor setColor;
    private final AttachmentType attachmentType;

    public SetCardColorAttachedEffect(ObjectColor setColor, Duration duration, AttachmentType attachmentType) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.setColor = setColor;
        this.attachmentType = attachmentType;
        this.staticText = attachmentType.verb() + " creature is " + setColor.getDescription();
    }

    protected SetCardColorAttachedEffect(final SetCardColorAttachedEffect effect) {
        super(effect);
        this.setColor = effect.setColor;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.getColor(game).setColor(setColor);
        return true;
    }

    @Override
    public SetCardColorAttachedEffect copy() {
        return new SetCardColorAttachedEffect(this);
    }

}
