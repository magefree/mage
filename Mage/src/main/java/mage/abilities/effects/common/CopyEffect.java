/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
import mage.util.functions.ApplyToPermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CopyEffect extends ContinuousEffectImpl {

    /**
     * Object we copy from
     */
    protected MageObject copyFromObject;

    protected UUID copyToObjectId;
    protected ApplyToPermanent applier;

    public CopyEffect(MageObject copyFromObject, UUID copyToObjectId) {
        this(Duration.Custom, copyFromObject, copyToObjectId);
    }

    public CopyEffect(Duration duration, MageObject copyFromObject, UUID copyToObjectId) {
        super(duration, Layer.CopyEffects_1, SubLayer.NA, Outcome.BecomeCreature);
        this.copyFromObject = copyFromObject;
        this.copyToObjectId = copyToObjectId;
    }

    public CopyEffect(final CopyEffect effect) {
        super(effect);
        this.copyFromObject = effect.copyFromObject.copy();
        this.copyToObjectId = effect.copyToObjectId;
        this.applier = effect.applier;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (!(copyFromObject instanceof Permanent) && (copyFromObject instanceof Card)) {
            this.copyFromObject = new PermanentCard((Card) copyFromObject, source.getControllerId(), game);
        }
        Permanent permanent = game.getPermanent(copyToObjectId);
        if (permanent != null) {
            affectedObjectList.add(new MageObjectReference(permanent, game));
        } else if (source.getAbilityType() == AbilityType.STATIC) {
            // for replacement effects that let a permanent enter the battlefield as a copy of another permanent we need to apply that copy
            // before the permanent is added to the battlefield
            permanent = game.getPermanentEntering(copyToObjectId);
            if (permanent != null) {
                copyToPermanent(permanent, game, source);
                // set reference to the permanent later on the battlefield so we have to add already one (if no token) to the zone change counter
                int ZCCDiff = 1;
                if (permanent instanceof PermanentToken) {
                    ZCCDiff = 0;
                }
                affectedObjectList.add(new MageObjectReference(permanent.getId(), game.getState().getZoneChangeCounter(copyToObjectId) + ZCCDiff, game));
            }
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
        return copyToPermanent(permanent, game, source);
    }

    protected boolean copyToPermanent(Permanent permanent, Game game, Ability source) {
        permanent.setCopy(true);
        permanent.setName(copyFromObject.getName());
        permanent.getColor(game).setColor(copyFromObject.getColor(game));
        permanent.getManaCost().clear();
        permanent.getManaCost().add(copyFromObject.getManaCost());
        permanent.getCardType().clear();
        for (CardType type : copyFromObject.getCardType()) {
            permanent.addCardType(type);
        }
        permanent.getSubtype(game).clear();
        for (String type : copyFromObject.getSubtype(game)) {
            permanent.getSubtype(game).add(type);
        }
        permanent.getSuperType().clear();
        for (SuperType type : copyFromObject.getSuperType()) {
            permanent.addSuperType(type);
        }

        permanent.removeAllAbilities(source.getSourceId(), game);
        if (copyFromObject instanceof Permanent) {
            for (Ability ability : ((Permanent) copyFromObject).getAbilities(game)) {
                permanent.addAbility(ability, getSourceId(), game, false); // no new Id so consumed replacement effects are known while new continuousEffects.apply happen.
            }
        } else {
            for (Ability ability : copyFromObject.getAbilities()) {
                permanent.addAbility(ability, getSourceId(), game, false); // no new Id so consumed replacement effects are known while new continuousEffects.apply happen.
            }
        }
        permanent.getPower().setValue(copyFromObject.getPower().getValue());
        permanent.getToughness().setValue(copyFromObject.getToughness().getValue());
        if (copyFromObject instanceof Permanent) {
            Permanent targetPermanent = (Permanent) copyFromObject;
            permanent.setTransformed(targetPermanent.isTransformed());
            permanent.setSecondCardFace(targetPermanent.getSecondCardFace());
            permanent.setFlipCard(targetPermanent.isFlipCard());
            permanent.setFlipCardName(targetPermanent.getFlipCardName());
        }

        // to get the image of the copied permanent copy number und expansionCode
        if (copyFromObject instanceof PermanentCard) {
            permanent.setCardNumber(((PermanentCard) copyFromObject).getCard().getCardNumber());
            permanent.setExpansionSetCode(((PermanentCard) copyFromObject).getCard().getExpansionSetCode());
        } else if (copyFromObject instanceof PermanentToken || copyFromObject instanceof Card) {
            permanent.setCardNumber(((Card) copyFromObject).getCardNumber());
            permanent.setExpansionSetCode(((Card) copyFromObject).getExpansionSetCode());
        }
        return true;
    }

    @Override
    public CopyEffect copy() {
        return new CopyEffect(this);
    }

    public MageObject getTarget() {
        return copyFromObject;
    }

    public void setTarget(MageObject target) {
        this.copyFromObject = target;
    }

    public UUID getSourceId() {
        return copyToObjectId;
    }

    public ApplyToPermanent getApplier() {
        return applier;
    }

    public void setApplier(ApplyToPermanent applier) {
        this.applier = applier;
    }

}
