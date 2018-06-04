
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.SubTypeList;

/**
 * @author nantuko
 */
public class SetCardSubtypeAttachedEffect extends ContinuousEffectImpl {

    private SubTypeList setSubtypes = new SubTypeList();
    private final AttachmentType attachmentType;

    public SetCardSubtypeAttachedEffect(Duration duration, AttachmentType attachmentType, SubType... setSubtype) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.setSubtypes.add(setSubtype);
        this.attachmentType = attachmentType;
        this.setText();
    }

    /*public SetCardSubtypeAttachedEffect(SubType setSubtype, Duration duration, AttachmentType attachmentType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.setSubtypes.add(setSubtype);
        this.attachmentType = attachmentType;
        setText();
    }

    public SetCardSubtypeAttachedEffect(List<String> setSubtypes, Duration duration, AttachmentType attachmentType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.setSubtypes.addAll(setSubtypes);
        this.attachmentType = attachmentType;
        setText();
    }*/

    public SetCardSubtypeAttachedEffect(final SetCardSubtypeAttachedEffect effect) {
        super(effect);
        this.setSubtypes = effect.setSubtypes;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            if (target != null) {
                target.getSubtype(game).retainAll(SubType.getLandTypes(false));
                target.getSubtype(game).addAll(setSubtypes);
            }
        }
        return true;
    }

    @Override
    public SetCardSubtypeAttachedEffect copy() {
        return new SetCardSubtypeAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb());
        sb.append(" creature is a");
        for (SubType subtype : this.setSubtypes) {
            sb.append(' ').append(subtype);
        }
        staticText = sb.toString();
    }
}
