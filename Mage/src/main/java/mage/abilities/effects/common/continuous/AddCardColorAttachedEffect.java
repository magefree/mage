package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject mageObject : objects) {
            if (!((mageObject instanceof Permanent))) {
                continue;
            }
            Permanent permanent = (Permanent) mageObject;
            permanent.getColor(game).addColor(addedColor);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            return target != null ? Collections.singletonList(target) : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public AddCardColorAttachedEffect copy() {
        return new AddCardColorAttachedEffect(this);
    }

}
