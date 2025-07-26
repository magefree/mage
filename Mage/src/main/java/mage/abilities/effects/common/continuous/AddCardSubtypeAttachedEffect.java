package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;

/**
 * @author nantuko
 */
public class AddCardSubtypeAttachedEffect extends ContinuousEffectImpl {
    private final SubType addedSubtype;

    public AddCardSubtypeAttachedEffect(SubType addedSubtype, AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedSubtype = addedSubtype;
        staticText = attachmentType.verb() +
                " creature becomes " + CardUtil.addArticle(addedSubtype.getDescription()) + " in addition to its other types";
    }

    protected AddCardSubtypeAttachedEffect(final AddCardSubtypeAttachedEffect effect) {
        super(effect);
        this.addedSubtype = effect.addedSubtype;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((Permanent) object).addSubType(game, addedSubtype);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            if (target != null) {
                affectedObjects.add(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public AddCardSubtypeAttachedEffect copy() {
        return new AddCardSubtypeAttachedEffect(this);
    }

}
