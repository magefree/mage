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

package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;


/**
 * 
 * @author LevelX2
 */
public class CantAttackControllerAttachedEffect extends ReplacementEffectImpl<CantAttackControllerAttachedEffect> implements MageSingleton {

    /**
     * The creature this permanent is attached to can't attack the controller 
     * of the attachment nor it's plainswalkers
     * 
     * @param attachmentType 
     */
    public CantAttackControllerAttachedEffect(AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        if (attachmentType.equals(AttachmentType.AURA)) {
            this.staticText = "Enchanted creature can't attack you or a planeswalker you control";
        } else {
            this.staticText = "Equiped creature can't attack you or a planeswalker you control";
        }        
    }

    public CantAttackControllerAttachedEffect(final CantAttackControllerAttachedEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackControllerAttachedEffect copy() {
        return new CantAttackControllerAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player attackingPlayer = game.getPlayer(event.getPlayerId());
        if (attackingPlayer != null && sourcePermanent != null) {
            game.informPlayer(attackingPlayer, 
                    new StringBuilder("You can't attack this player or his or her planeswalker, because the attacking creature is enchanted by ")
                            .append(sourcePermanent.getName()).append(".").toString());
        }        
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DECLARE_ATTACKER) {
            Permanent attachment = game.getPermanent(source.getSourceId());
            if (attachment != null && attachment.getAttachedTo() != null
                    && event.getSourceId().equals(attachment.getAttachedTo())) {
                if (event.getTargetId().equals(source.getControllerId())) {
                    return true;
                }
                Permanent plainswalker = game.getPermanent(event.getTargetId());
                if (plainswalker != null && plainswalker.getControllerId().equals(source.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
