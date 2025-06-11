package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author noxx
 */
public class AddCardColorAttachedEffect extends ContinuousEffectImpl {

    private final ObjectColor addedColor;

    public AddCardColorAttachedEffect(ObjectColor addedColor, AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.addedColor = addedColor;
        staticText = attachmentType.verb() + " creature is " + addedColor.getDescription()
                + " in addition to its other colors";
    }

    protected AddCardColorAttachedEffect(final AddCardColorAttachedEffect effect) {
        super(effect);
        this.addedColor = effect.addedColor;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent target = (Permanent) object;
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
    public AddCardColorAttachedEffect copy() {
        return new AddCardColorAttachedEffect(this);
    }

}
