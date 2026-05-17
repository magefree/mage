package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

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
        this.staticText = attachmentType.verb() + " permanent is " + addedSuperType.toString().toLowerCase(Locale.ENGLISH);
    }

    protected AddCardSuperTypeAttachedEffect(final AddCardSuperTypeAttachedEffect effect) {
        super(effect);
        this.addedSuperType = effect.addedSuperType;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.addSuperType(game, addedSuperType);
        return true;
    }

    @Override
    public AddCardSuperTypeAttachedEffect copy() {
        return new AddCardSuperTypeAttachedEffect(this);
    }

}
