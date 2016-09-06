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

package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class AddCardSubtypeAttachedEffect extends ContinuousEffectImpl {
    private String addedSubtype;
    private AttachmentType attachmentType;

    public AddCardSubtypeAttachedEffect(String addedSubtype, Duration duration, AttachmentType attachmentType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedSubtype = addedSubtype;
        this.attachmentType = attachmentType;
        setText();
    }

    public AddCardSubtypeAttachedEffect(final AddCardSubtypeAttachedEffect effect) {
        super(effect);
        this.addedSubtype = effect.addedSubtype;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent target = game.getPermanent(equipment.getAttachedTo());
            if (target != null && !target.getSubtype(game).contains(addedSubtype))
                target.getSubtype(game).add(addedSubtype);
        }
        return true;
    }

    @Override
    public AddCardSubtypeAttachedEffect copy() {
        return new AddCardSubtypeAttachedEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (attachmentType == AttachmentType.AURA)
            sb.append("Enchanted");
        else if (attachmentType == AttachmentType.EQUIPMENT)
            sb.append("Equipped");

        sb.append(" creature becomes ").append(addedSubtype).append(" in addition to its other types"); //TODO add attacked card type detection
        staticText = sb.toString();
    }
}
