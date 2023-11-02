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

}
