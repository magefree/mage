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
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;


/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnToHandTargetEffect extends OneShotEffect<ReturnToHandTargetEffect> {

    public ReturnToHandTargetEffect() {
        super(Outcome.ReturnToHand);
    }

    public ReturnToHandTargetEffect(final ReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandTargetEffect copy() {
        return new ReturnToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = true; // in case no target is selected
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            switch (game.getState().getZone(targetId)) {
                case BATTLEFIELD:
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.moveToZone(Zone.HAND, source.getId(), game, false);
                    } else {
                        result = false;
                    }
                    break;
                case GRAVEYARD:
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getId(), game, true);
                    }  else {
                        result = false;
                    }
                    break;
                case EXILED:
                    card = game.getCard(targetId);
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getId(), game, true);
                    } else {
                        result = false;
                    }
                    break;
            }
        }
        return result;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().size() < 1) {
            return "";
        }
        Target target = mode.getTargets().get(0);
        StringBuilder sb = new StringBuilder("Return ");
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            sb.append("up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName()).append(" to their owners' hand");
            return sb.toString();
        } else {
            if (!target.getTargetName().startsWith("another")) {
                sb.append(" target ");
            }
            sb.append(target.getTargetName()).append(" to it's owner's hand").toString();
            return sb.toString();
        }
    }

}
