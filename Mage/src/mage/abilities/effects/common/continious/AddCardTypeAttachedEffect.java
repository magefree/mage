package mage.abilities.effects.common.continious;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class AddCardTypeAttachedEffect extends ContinuousEffectImpl<AddCardTypeAttachedEffect> {
    private Constants.CardType addedCardType;
    private Constants.AttachmentType attachmentType;

    public AddCardTypeAttachedEffect(Constants.CardType addedCardType, Constants.Duration duration, Constants.AttachmentType attachmentType) {
        super(duration, Constants.Layer.TypeChangingEffects_4, Constants.SubLayer.NA, Constants.Outcome.Benefit);
        this.addedCardType = addedCardType;
        this.attachmentType = attachmentType;
		setText();
    }

    public AddCardTypeAttachedEffect(final AddCardTypeAttachedEffect effect) {
        super(effect);
        this.addedCardType = effect.addedCardType;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            if (!target.getCardType().contains(addedCardType))
                target.getCardType().add(addedCardType);
        }
        return true;
    }

    @Override
    public AddCardTypeAttachedEffect copy() {
        return new AddCardTypeAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (attachmentType == Constants.AttachmentType.AURA)
            sb.append("Enchanted");
        else if (attachmentType == Constants.AttachmentType.EQUIPMENT)
            sb.append("Equipped");

        sb.append(" creature becomes ").append(addedCardType.toString()).append(" in addition to its other types"); //TODO add attacked card type detection
        staticText = sb.toString();
    }
}
