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

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */

public class CantBeTargetedAttachedEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterStackObject filterSource;
    private final AttachmentType attachmentType;
    
    public CantBeTargetedAttachedEffect(FilterStackObject filterSource, Duration duration, AttachmentType attachmentType) {
        super(duration, Outcome.Benefit);
        this.filterSource = filterSource;
        this.attachmentType = attachmentType;
    }

    public CantBeTargetedAttachedEffect(final CantBeTargetedAttachedEffect effect) {
        super(effect);
        this.filterSource = effect.filterSource.copy();
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public CantBeTargetedAttachedEffect copy() {
        return new CantBeTargetedAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && event.getTargetId().equals(attachment.getAttachedTo())) {
            StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
            if (sourceObject != null && filterSource.match(sourceObject, source.getControllerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (attachmentType.equals(AttachmentType.AURA)) {
            sb.append("Enchanted creature");
        } else {
            sb.append("Equipped creature");
        }
        sb.append(" can't be the target of ");
        sb.append(filterSource.getMessage());
        if (!duration.toString().isEmpty()) {
            sb.append(" ");            
            if (duration.equals(Duration.EndOfTurn)) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }
        }        
        return sb.toString();
    }

}
