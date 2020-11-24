package mage.game.events;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class SearchLibraryEvent extends GameEvent {

    protected UUID searchingControllerId; // who controls the searching process, see Opposition Agent

    /**
     * Searching library event
     *
     * @param targetPlayerId whose library will be searched
     * @param sourceId       source of the searching effect
     * @param playerId       who must search the library (also see searchingControllerId)
     * @param amount         cards amount to search
     */
    public SearchLibraryEvent(UUID targetPlayerId, UUID sourceId, UUID playerId, int amount) {
        super(GameEvent.EventType.SEARCH_LIBRARY, targetPlayerId, sourceId, playerId, amount, false);
        this.searchingControllerId = playerId;
    }

    public UUID getSearchingControllerId() {
        return this.searchingControllerId;
    }

    public void setSearchingControllerId(UUID searchingControllerId) {
        this.searchingControllerId = searchingControllerId;
    }
}
