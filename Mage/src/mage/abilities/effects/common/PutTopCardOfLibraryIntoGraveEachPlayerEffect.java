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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class PutTopCardOfLibraryIntoGraveEachPlayerEffect extends OneShotEffect<PutTopCardOfLibraryIntoGraveEachPlayerEffect> {

    private final int numberCards;
    private final TargetController targetController;

    public PutTopCardOfLibraryIntoGraveEachPlayerEffect(int numberCards, TargetController targetController) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
        this.targetController = targetController;
        this.staticText = setText();
    }

    public PutTopCardOfLibraryIntoGraveEachPlayerEffect(final PutTopCardOfLibraryIntoGraveEachPlayerEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
        this.targetController = effect.targetController;
    }

    @Override
    public PutTopCardOfLibraryIntoGraveEachPlayerEffect copy() {
        return new PutTopCardOfLibraryIntoGraveEachPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            switch(targetController) {
                case OPPONENT:
                    for(UUID playerId: game.getOpponents(source.getControllerId()) ) {
                        putCardsToGravecard(playerId, source, game);
                    }
                    break;
                case ANY:
                    for(UUID playerId: player.getInRange() ) {
                        putCardsToGravecard(playerId, source, game);
                    }
                    break;
                case NOT_YOU:
                    for(UUID playerId: player.getInRange() ) {
                        if (!playerId.equals(source.getSourceId())) {
                            putCardsToGravecard(playerId, source, game);
                        }
                    }
                default:
                    throw new UnsupportedOperationException("TargetController type not supported.");        
            }            
            int cardsCount = Math.min(numberCards, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, true);
                }
            }
            return true;
        }
        return false;
    }
    
    private void putCardsToGravecard(UUID playerId, Ability source, Game game) {
        Player player = game.getPlayer(id);
        if (player != null) {
            int cardsCount = Math.min(numberCards, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, true);
                }
            }
        }
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        switch(targetController) {
            case OPPONENT:
                sb.append("Each opponent ");
                break;
            case ANY:
                sb.append("Each player ");
                break;
            case NOT_YOU:
                sb.append("Each other player ");
                break;                
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");                   
        }
        sb.append("puts the top ");
        if (numberCards == 1) {
            sb.append(" card");
        } else {
            sb.append(CardUtil.numberToText(numberCards));
            sb.append(" cards");
        }
        sb.append(" of his or her library into his or her graveyard");
        return sb.toString();
    }
}
