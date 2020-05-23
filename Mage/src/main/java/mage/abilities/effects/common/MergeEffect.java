package mage.abilities.effects.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MergeEffect extends ContinuousEffectImpl {

    /**
     * Object we copy from
     */
    protected MageObject mergeSourceObject;
    protected boolean onTop;

    protected UUID mergeToObjectId;

    public MergeEffect(MageObject mergeSourceObject, boolean onTop, UUID mergeToObjectId) {
        this(Duration.Custom, mergeSourceObject, onTop, mergeToObjectId);
    }

    public MergeEffect(Duration duration, MageObject mergeSourceObject, boolean onTop, UUID mergeToObjectId) {
        super(duration, Layer.CopyEffects_1, SubLayer.CopyingMerging_1a, Outcome.BecomeCreature);
        this.mergeSourceObject = mergeSourceObject;
        this.mergeToObjectId = mergeToObjectId;
    }

    public MergeEffect(final MergeEffect effect) {
        super(effect);
        this.mergeSourceObject = effect.mergeSourceObject.copy();
        this.onTop = effect.onTop;
        this.mergeToObjectId = effect.mergeToObjectId;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (!(mergeSourceObject instanceof Permanent) && (mergeSourceObject instanceof Card)) {
            this.mergeSourceObject = new PermanentCard((Card) mergeSourceObject, source.getControllerId(), game);
        }
        Permanent permanent = game.getPermanent(mergeToObjectId);
        if (permanent != null) {
            affectedObjectList.add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (affectedObjectList.isEmpty()) {
            this.discard();
            return false;
        }
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD, source.getSourceObjectZoneChangeCounter());
            // As long as the permanent is still in the LKI continue to copy to get triggered abilities to TriggeredAbilites for dies events.
            if (permanent == null) {
                discard();
                return false;
            }
        }
        return mergeToPermanent(permanent, game, source);
    }

    protected boolean mergeToPermanent(Permanent permanent, Game game, Ability source) {
        if (onTop) {
            permanent.setName(mergeSourceObject.getName());
            permanent.getColor(game).setColor(mergeSourceObject.getColor(game));
            permanent.getManaCost().clear();
            permanent.getManaCost().add(mergeSourceObject.getManaCost());
            permanent.getCardType().clear();
            for (CardType type : mergeSourceObject.getCardType()) {
                permanent.addCardType(type);
            }
            permanent.getSubtype(game).clear();
            for (SubType type : mergeSourceObject.getSubtype(game)) {
                permanent.getSubtype(game).add(type);
            }
            permanent.getSuperType().clear();
            for (SuperType type : mergeSourceObject.getSuperType()) {
                permanent.addSuperType(type);
            }

            // to get the image of the copied permanent copy number and expansionCode
            if (mergeSourceObject instanceof PermanentCard) {
                permanent.setCardNumber(((PermanentCard) mergeSourceObject).getCard().getCardNumber());
                permanent.setExpansionSetCode(((PermanentCard) mergeSourceObject).getCard().getExpansionSetCode());
            } else if (mergeSourceObject instanceof PermanentToken || mergeSourceObject instanceof Card) {
                permanent.setCardNumber(((Card) mergeSourceObject).getCardNumber());
                permanent.setExpansionSetCode(((Card) mergeSourceObject).getExpansionSetCode());
            }
        }

        for (Ability ability : mergeSourceObject.getAbilities()) {
            permanent.addAbility(ability, getSourceId(), game, false);
        }

        return true;
    }

    @Override
    public MergeEffect copy() {
        return new MergeEffect(this);
    }

    public MageObject getTarget() {
        return mergeSourceObject;
    }

    public void setTarget(MageObject target) {
        this.mergeSourceObject = target;
    }

    public UUID getSourceId() {
        return mergeToObjectId;
    }

}
