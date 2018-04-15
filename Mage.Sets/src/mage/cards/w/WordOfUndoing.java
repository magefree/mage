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
package mage.cards.w;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author tcontis
 */
public class WordOfUndoing extends CardImpl {

    public WordOfUndoing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        //Return target creature and all white Auras you own attached to it to their owners’ hands.
        this.getSpellAbility().addEffect(new WordOfUndoingReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public WordOfUndoing(final WordOfUndoing card) {
        super(card);
    }

    @Override
    public WordOfUndoing copy() {
        return new WordOfUndoing(this);
    }
}

class WordOfUndoingReturnToHandEffect extends OneShotEffect {

    public WordOfUndoingReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature and all white Auras you own attached to it to their owners’ hands.";
    }

    public WordOfUndoingReturnToHandEffect(final WordOfUndoingReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public WordOfUndoingReturnToHandEffect copy() {
        return new WordOfUndoingReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Card> attachments = new LinkedHashSet<>();
        Player player = game.getPlayer(source.getControllerId());
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null) {
            for (UUID attachmentId : target.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getControllerId().equals(source.getControllerId())
                        && attachment.hasSubtype(SubType.AURA, game) && attachment.getColor(game).isWhite()) {
                    attachments.add(attachment);
                }
            }
            attachments.add(target);
            player.moveCards(attachments, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}

