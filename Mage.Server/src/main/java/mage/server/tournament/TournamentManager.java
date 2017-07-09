/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.server.tournament;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.cards.decks.Deck;
import mage.game.tournament.Tournament;
import mage.view.TournamentView;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum TournamentManager {
    instance;
    private final ConcurrentHashMap<UUID, TournamentController> controllers = new ConcurrentHashMap<>();

    public TournamentController getTournamentController(UUID tournamentId) {
        return controllers.get(tournamentId);
    }

    public void createTournamentSession(Tournament tournament, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId) {
        TournamentController tournamentController = new TournamentController(tournament, userPlayerMap, tableId);
        controllers.put(tournament.getId(), tournamentController);
    }

    public void joinTournament(UUID tournamentId, UUID userId) {
        controllers.get(tournamentId).join(userId);
    }

    public void quit(UUID tournamentId, UUID userId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            tournamentController.quit(userId);
        } else {
            Logger.getLogger(TournamentManager.class).error("Tournament controller missing  tournamentid: " + tournamentId + " userId: " + userId);
        }
    }

    public void timeout(UUID tournamentId, UUID userId) {
        controllers.get(tournamentId).timeout(userId);
    }

    public void submitDeck(UUID tournamentId, UUID playerId, Deck deck) {
        controllers.get(tournamentId).submitDeck(playerId, deck);
    }

    public boolean updateDeck(UUID tournamentId, UUID playerId, Deck deck) {
        return controllers.get(tournamentId).updateDeck(playerId, deck);
    }

    public TournamentView getTournamentView(UUID tournamentId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            return tournamentController.getTournamentView();
        }
        return null;
    }

    public Optional<UUID> getChatId(UUID tournamentId) {
        if (controllers.containsKey(tournamentId)) {
            return Optional.of(controllers.get(tournamentId).getChatId());
        }
        return Optional.empty();
    }

    public void removeTournament(UUID tournamentId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            controllers.remove(tournamentId);
            tournamentController.cleanUpOnRemoveTournament();

        }
    }

}
