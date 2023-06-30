
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

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
            if (target != null) {
                target.addCardType(game, addedCardType);
            }
        }
        return true;
    }

    @Override
    public AddCardTypeAttachedEffect copy() {
        return new AddCardTypeAttachedEffect(this);
    }

}
