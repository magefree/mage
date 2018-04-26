/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.remote.interfaces;

import java.util.Set;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.view.DraftPickView;

/**
 * @author noxx
 */
public interface GamePlay {

    boolean startMatch(UUID roomId, UUID tableId);

    boolean watchGame(UUID gameId);

    boolean stopWatching(UUID gameId);

    boolean sendPlayerUUID(UUID gameId, UUID data);

    boolean sendPlayerBoolean(UUID gameId, boolean data);

    boolean sendPlayerInteger(UUID gameId, int data);

    boolean sendPlayerString(UUID gameId, String data);

    boolean sendPlayerManaType(UUID gameId, UUID playerId, ManaType data);

    boolean quitMatch(UUID gameId);

    boolean quitTournament(UUID tournamentId);

    boolean quitDraft(UUID draftId);

    boolean submitDeck(UUID tableId, DeckCardLists deck);

    boolean updateDeck(UUID tableId, DeckCardLists deck);

    DraftPickView sendCardPick(UUID draftId, UUID cardId, Set<UUID> hiddenCards);
    DraftPickView sendCardMark(UUID draftId, UUID cardId);

    /**
     * magenoxx:
     *   it should be done separately as sendPlayer* methods calls are injected into the game flow
     *   - this is similar to concedeGame method
     * 
     * This method sends player actions for a game
     * priority handling, undo
     *
     * @param passPriorityAction
     * @param gameId
     * @param Data
     * @return
     */
    boolean sendPlayerAction(PlayerAction passPriorityAction, UUID gameId, Object Data);

}
