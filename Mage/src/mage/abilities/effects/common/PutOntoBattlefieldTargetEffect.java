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

import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX
 */
public class PutOntoBattlefieldTargetEffect extends OneShotEffect<PutOntoBattlefieldTargetEffect> {
    
    boolean tapped;

    public PutOntoBattlefieldTargetEffect(boolean tapped) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
    }

    public PutOntoBattlefieldTargetEffect(final PutOntoBattlefieldTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
    }

    @Override
    public PutOntoBattlefieldTargetEffect copy() {
        return new PutOntoBattlefieldTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (UUID targetId : targetPointer.getTargets(source)) {
            Card card = game.getCard(targetId);
            if (card != null) {

                    switch (game.getState().getZone(targetId)) {
                        case GRAVEYARD:
                            for (Player player : game.getPlayers().values()) {
                                if (player.getGraveyard().contains(card.getId())) {
                                    player.getGraveyard().remove(card);
                                    result |= card.moveToZone(Zone.BATTLEFIELD, source.getId(), game, tapped);
                                }
                            }
                        case HAND:
                            for (Player player : game.getPlayers().values()) {
                                if (player.getHand().contains(card.getId())) {
                                    player.getHand().remove(card);
                                    result |= card.moveToZone(Zone.BATTLEFIELD, source.getId(), game, tapped);
                                }
                            }
                    }
            }
        }
        return result;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Put ");
        if (mode.getTargets().get(0).getMaxNumberOfTargets() == 0) {
            sb.append("any number of ");
        }
        sb.append(mode.getTargets().get(0).getTargetName());
        sb.append(tapped ? "tapped" : "").append(" onto the battlefield");
        return sb.toString();

    }
}
