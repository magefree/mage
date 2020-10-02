

package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class AddCardSubtypeAttachedEffect extends ContinuousEffectImpl {
    private SubType addedSubtype;
    private AttachmentType attachmentType;

    public AddCardSubtypeAttachedEffect(SubType addedSubtype, Duration duration, AttachmentType attachmentType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedSubtype = addedSubtype;
        this.attachmentType = attachmentType;
        setText();
    }

    public AddCardSubtypeAttachedEffect(final AddCardSubtypeAttachedEffect effect) {
        super(effect);
        this.addedSubtype = effect.addedSubtype;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            if (target != null)
                target.addSubType(game, addedSubtype);
        }
        return true;
    }

    @Override
    public AddCardSubtypeAttachedEffect copy() {
        return new AddCardSubtypeAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();

        sb.append(attachmentType.verb());
        sb.append(" creature becomes ").append(addedSubtype).append(" in addition to its other types"); //TODO add attacked card type detection
        staticText = sb.toString();
    }
}
