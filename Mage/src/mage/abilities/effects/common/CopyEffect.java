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


import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CopyEffect extends ContinuousEffectImpl {

    /**
     * Object we copy from
     */
    private MageObject target;
    
    private UUID sourceId;
    private ApplyToPermanent applier;
    
    public CopyEffect(MageObject target, UUID sourceId) {
        this(Duration.Custom, target, sourceId);
    }
    
    public CopyEffect(Duration duration, MageObject target, UUID sourceId) {
        super(duration, Layer.CopyEffects_1, SubLayer.NA, Outcome.BecomeCreature);
        this.target = target;
        this.sourceId = sourceId;
    }

    public CopyEffect(final CopyEffect effect) {
        super(effect);
        this.target = effect.target.copy();
        this.sourceId = effect.sourceId;
        this.applier = effect.applier;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(getSourceId(), game));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD, source.getSourceObjectZoneChangeCounter());
            // As long as the permanent is still in the LKI continue to copy to get triggered abilities to TriggeredAbilites for dies events.
            if (permanent == null) {
                discard();
                return false;
            }
        }
        permanent.setCopy(true);
        permanent.setName(target.getName());
        permanent.getColor(game).setColor(target.getColor(game));
        permanent.getManaCost().clear();
        permanent.getManaCost().add(target.getManaCost());
        permanent.getCardType().clear();
        for (CardType type: target.getCardType()) {
            permanent.getCardType().add(type);
        }
        permanent.getSubtype().clear();
        for (String type: target.getSubtype()) {
            permanent.getSubtype().add(type);
        }
        permanent.getSupertype().clear();
        for (String type: target.getSupertype()) {
            permanent.getSupertype().add(type);
        }

        permanent.removeAllAbilities(source.getSourceId(), game);
        for (Ability ability: target.getAbilities()) {
             permanent.addAbility(ability, getSourceId(), game, false); // no new Id so consumed replacement effects are known while new continuousEffects.apply happen.
        }
        permanent.getPower().setValue(target.getPower().getValue());
        permanent.getToughness().setValue(target.getToughness().getValue());
        if (target instanceof Permanent) {
            Permanent targetPermanent = (Permanent) target;
            permanent.setTransformed(targetPermanent.isTransformed());
            permanent.setSecondCardFace(targetPermanent.getSecondCardFace());
            permanent.setFlipCard(targetPermanent.isFlipCard());
            permanent.setFlipCardName(targetPermanent.getFlipCardName());
        }

        // to get the image of the copied permanent copy number und expansionCode
        if (target instanceof PermanentCard) {
            permanent.setCardNumber(((PermanentCard) target).getCard().getCardNumber());
            permanent.setExpansionSetCode(((PermanentCard) target).getCard().getExpansionSetCode());
        } else if (target instanceof PermanentToken || target instanceof Card) {
            permanent.setCardNumber(((Card) target).getCardNumber());
            permanent.setExpansionSetCode(((Card) target).getExpansionSetCode());
        }        
        return true;
    }

    @Override
    public CopyEffect copy() {
        return new CopyEffect(this);
    }

    public MageObject getTarget() {
        return target;
    }

    public void setTarget(MageObject target) {
        this.target = target;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public ApplyToPermanent getApplier() {
        return applier;
    }

    public void setApplier(ApplyToPermanent applier) {
        this.applier = applier;
    }
       
}
