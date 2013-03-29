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

import mage.cards.decks.DeckCardLists;
import mage.view.DraftPickView;

import java.util.UUID;

/**
 * @author noxx
 */
public interface GamePlay {

    boolean startGame(UUID roomId, UUID tableId);

    boolean watchGame(UUID gameId);

    boolean stopWatching(UUID gameId);

    boolean sendPlayerUUID(UUID gameId, UUID data);

    boolean sendPlayerBoolean(UUID gameId, boolean data);

    boolean sendPlayerInteger(UUID gameId, int data);

    boolean sendPlayerString(UUID gameId, String data);

    boolean concedeGame(UUID gameId);

    boolean submitDeck(UUID tableId, DeckCardLists deck);

    boolean updateDeck(UUID tableId, DeckCardLists deck);

    DraftPickView sendCardPick(UUID draftId, UUID cardId);

    boolean undo(UUID gameId);

    /*** Separate methods for priority handling ***/
    /**
     * magenoxx:
     *   it should be done separately as sendPlayer* methods calls are injected into the game flow
     *   - this is similar to concedeGame method
     */

    /**
     * Pass priority until next your turn.
     * Don't stop at all even if something happens.
     *
     * @param gameId
     * @return
     */
    boolean passPriorityUntilNextYourTurn(UUID gameId);

    /**
     * Passes current turn but stop on pre combat phase.
     *
     * @param gameId
     * @return
     */
    boolean passTurnPriority(UUID gameId);

    /**
     * This method cancels all other calls made before.
     *
     * @param gameId
     * @return
     */
    boolean restorePriority(UUID gameId);

}
