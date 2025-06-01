package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author nantuko
 */
public class AddCardSuperTypeAttachedEffect extends ContinuousEffectImpl {

    private final SuperType addedSuperType;
    private final AttachmentType attachmentType;

    public AddCardSuperTypeAttachedEffect(SuperType addedSuperType, Duration duration, AttachmentType attachmentType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedSuperType = addedSuperType;
        this.attachmentType = attachmentType;
        setText();
    }

    protected AddCardSuperTypeAttachedEffect(final AddCardSuperTypeAttachedEffect effect) {
        super(effect);
        this.addedSuperType = effect.addedSuperType;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).addSuperType(game, addedSuperType);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            return target != null ? Collections.singletonList(target) : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public AddCardSuperTypeAttachedEffect copy() {
        return new AddCardSuperTypeAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb());
        sb.append(" permanent is ").append(addedSuperType.toString().toLowerCase(Locale.ENGLISH)); //TODO add attacked card type detection
        staticText = sb.toString();
    }
}
