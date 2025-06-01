
package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author nantuko
 */
public class AddCardTypeAttachedEffect extends ContinuousEffectImpl {

    private final CardType addedCardType;
    private final AttachmentType attachmentType;

    public AddCardTypeAttachedEffect(CardType addedCardType, AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedCardType = addedCardType;
        this.attachmentType = attachmentType;
        staticText = "and is " +
                CardUtil.addArticle(CardUtil.getTextWithFirstCharLowerCase(addedCardType.toString())) +
                " in addition to its other types";
    }

    protected AddCardTypeAttachedEffect(final AddCardTypeAttachedEffect effect) {
        super(effect);
        this.addedCardType = effect.addedCardType;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            object.addCardType(game, addedCardType);
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
    public AddCardTypeAttachedEffect copy() {
        return new AddCardTypeAttachedEffect(this);
    }

}
