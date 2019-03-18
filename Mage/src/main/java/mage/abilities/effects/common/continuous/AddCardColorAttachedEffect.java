

package mage.abilities.effects.common.continuous;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public class AddCardColorAttachedEffect extends ContinuousEffectImpl {

    private ObjectColor addedColor;
    private AttachmentType attachmentType;

    public AddCardColorAttachedEffect(ObjectColor addedColor, Duration duration, AttachmentType attachmentType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedColor = addedColor;
        this.attachmentType = attachmentType;
        setText();
    }

    public AddCardColorAttachedEffect(final AddCardColorAttachedEffect effect) {
        super(effect);
        this.addedColor = effect.addedColor;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            if (target != null) {
                if (addedColor.isBlack())
                    target.getColor(game).setBlack(true);
                if (addedColor.isBlue())
                    target.getColor(game).setBlue(true);
                if (addedColor.isWhite())
                    target.getColor(game).setWhite(true);
                if (addedColor.isGreen())
                    target.getColor(game).setGreen(true);
                if (addedColor.isRed())
                    target.getColor(game).setRed(true);
            }
        }
        return true;
    }

    @Override
    public AddCardColorAttachedEffect copy() {
        return new AddCardColorAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb());
        sb.append(" creature is a ").append(addedColor.getDescription()).append(" in addition to its colors");
        staticText = sb.toString();
    }
}
