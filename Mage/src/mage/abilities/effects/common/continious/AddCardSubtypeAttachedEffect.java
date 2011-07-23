package mage.abilities.effects.common.continious;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class AddCardSubtypeAttachedEffect extends ContinuousEffectImpl<AddCardSubtypeAttachedEffect> {
    private String addedSubtype;
    private Constants.AttachmentType attachmentType;

    public AddCardSubtypeAttachedEffect(String addedSubtype, Constants.Duration duration, Constants.AttachmentType attachmentType) {
        super(duration, Constants.Layer.TypeChangingEffects_4, Constants.SubLayer.NA, Constants.Outcome.Benefit);
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
            if (target != null && !target.getSubtype().contains(addedSubtype))
                target.getSubtype().add(addedSubtype);
        }
        return true;
    }

    @Override
    public AddCardSubtypeAttachedEffect copy() {
        return new AddCardSubtypeAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (attachmentType == Constants.AttachmentType.AURA)
            sb.append("Enchanted");
        else if (attachmentType == Constants.AttachmentType.EQUIPMENT)
            sb.append("Equipped");

        sb.append(" creature becomes ").append(addedSubtype).append(" in addition to its other types"); //TODO add attacked card type detection
        staticText = sb.toString();
    }
}
