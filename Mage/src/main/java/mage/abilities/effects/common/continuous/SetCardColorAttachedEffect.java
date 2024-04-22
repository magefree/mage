
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

    private ObjectColor setColor;
    private AttachmentType attachmentType;

    public SetCardColorAttachedEffect(ObjectColor setColor, Duration duration, AttachmentType attachmentType) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.setColor = setColor;
        this.attachmentType = attachmentType;
        setText();
    }

    protected SetCardColorAttachedEffect(final SetCardColorAttachedEffect effect) {
        super(effect);
        this.setColor = effect.setColor;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            if (target != null) {
                target.getColor(game).setColor(setColor);
                return true;
            }
        }
        return false;
    }

    @Override
    public SetCardColorAttachedEffect copy() {
        return new SetCardColorAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb());
        sb.append(" creature is ").append(setColor.getDescription());
        staticText = sb.toString();
    }
}
