
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

    boolean setBoosterLoaded(UUID draftId);
    
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
