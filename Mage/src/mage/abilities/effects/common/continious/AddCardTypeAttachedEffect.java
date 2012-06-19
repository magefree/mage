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

package mage.abilities.effects.common.continious;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class AddCardTypeAttachedEffect extends ContinuousEffectImpl<AddCardTypeAttachedEffect> {
    private Constants.CardType addedCardType;
    private Constants.AttachmentType attachmentType;

    public AddCardTypeAttachedEffect(Constants.CardType addedCardType, Constants.Duration duration, Constants.AttachmentType attachmentType) {
        super(duration, Constants.Layer.TypeChangingEffects_4, Constants.SubLayer.NA, Constants.Outcome.Benefit);
        this.addedCardType = addedCardType;
        this.attachmentType = attachmentType;
        setText();
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
            if (target != null && !target.getCardType().contains(addedCardType))
                target.getCardType().add(addedCardType);
        }
        return true;
    }

    @Override
    public AddCardTypeAttachedEffect copy() {
        return new AddCardTypeAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (attachmentType == Constants.AttachmentType.AURA)
            sb.append("Enchanted");
        else if (attachmentType == Constants.AttachmentType.EQUIPMENT)
            sb.append("Equipped");

        sb.append(" creature becomes ").append(addedCardType.toString()).append(" in addition to its other types"); //TODO add attacked card type detection
        staticText = sb.toString();
    }
}
