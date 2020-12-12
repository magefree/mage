package mage.game.events;

import mage.abilities.Ability;
import mage.target.Target;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class LibrarySearchedEvent extends GameEvent {

    protected Target searchedTarget;

    /**
     * Searched library event (after library searching finished). Return false on replaceEvent to
     *
     * @param targetPlayerId whose library searched
     * @param source         source ability of the searching effect
     * @param playerId       who must search the library
     * @param searchedTarget founded cards (targets list can be changed by replace events, see Opposition Agent)
     */
    public LibrarySearchedEvent(UUID targetPlayerId, Ability source, UUID playerId, Target searchedTarget) {
        super(GameEvent.EventType.LIBRARY_SEARCHED, targetPlayerId, source, playerId, searchedTarget.getTargets().size(), false);
        this.searchedTarget = searchedTarget;
    }

    public Target getSearchedTarget() {
        return this.searchedTarget;
    }
}
