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
package mage.sets.shadowmoor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.token.RatToken;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author jeffwadsworth
 */
public class PlagueOfVermin extends CardImpl {

    public PlagueOfVermin(UUID ownerId) {
        super(ownerId, 73, "Plague of Vermin", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{6}{B}");
        this.expansionSetCode = "SHM";

        this.color.setBlack(true);

        // Starting with you, each player may pay any amount of life. Repeat this process until no one pays life. Each player puts a 1/1 black Rat creature token onto the battlefield for each 1 life he or she paid this way.
        this.getSpellAbility().addEffect(new PlagueOfVerminEffect());

    }

    public PlagueOfVermin(final PlagueOfVermin card) {
        super(card);
    }

    @Override
    public PlagueOfVermin copy() {
        return new PlagueOfVermin(this);
    }
}

class PlagueOfVerminEffect extends OneShotEffect {

    public PlagueOfVerminEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Starting with you, each player may pay any amount of life. Repeat this process until no one pays life. Each player puts a 1/1 black Rat creature token onto the battlefield for each 1 life he or she paid this way.";
    }

    public PlagueOfVerminEffect(final PlagueOfVerminEffect effect) {
        super(effect);
    }

    @Override
    public PlagueOfVerminEffect copy() {
        return new PlagueOfVerminEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        Map<UUID, Integer> payLife = new HashMap<>();
        int currentLifePaid;
        int totalPaidLife;
        if (controller != null) {
            PlayerList playerList = game.getState().getPlayerList().copy();
            while (!playerList.get().equals(source.getControllerId()) && controller.isInGame()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            UUID firstInactivePlayer = null;

            while (controller.isInGame()) {
                if (firstInactivePlayer == null) {
                    firstInactivePlayer = currentPlayer.getId();
                }
                if (currentPlayer != null && currentPlayer.isInGame() && controller.getInRange().contains(currentPlayer.getId())) {
                    currentLifePaid = 0;
                    totalPaidLife = 0;
                    if (currentPlayer.chooseUse(Outcome.AIDontUseIt, "Pay life?", game)) {
                        totalPaidLife = currentPlayer.getAmount(0, controller.getLife(), "Pay how many life?", game);
                        if (totalPaidLife > 0) {
                            currentPlayer.loseLife(totalPaidLife, game);
                            if (payLife.get(currentPlayer.getId()) == null) {
                                payLife.put(currentPlayer.getId(), totalPaidLife);
                            } else {
                                currentLifePaid = payLife.get(currentPlayer.getId());
                                payLife.put(currentPlayer.getId(), currentLifePaid + totalPaidLife);
                            }
                        }
                        game.informPlayers(new StringBuilder(sourceCard.getName()).append(": ").append(currentPlayer.getLogName()).append(" pays ").append(payLife.get(currentPlayer.getId())).append(" life").toString());
                        firstInactivePlayer = null;
                    }
                }
                
                // get next player
                playerList.getNext();
                currentPlayer = game.getPlayer(playerList.get());
                
                // if all player since this player didn't put permanent in play finish the process
                if (currentPlayer.getId().equals(firstInactivePlayer)) {
                    break;
                }
            }

            // create tokens according to life spent by each player
            RatToken token = new RatToken();
            for (Map.Entry<UUID, Integer> entry
                    : payLife.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    token.putOntoBattlefield(entry.getValue(), game, source.getSourceId(), player.getId());
                }
            }

        }
        return true;
    }
}
