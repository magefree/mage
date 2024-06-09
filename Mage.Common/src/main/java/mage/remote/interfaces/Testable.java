
package mage.remote.interfaces;

import mage.cards.decks.DeckCardLists;

import java.util.UUID;

/**
 * @author noxx
 */
public interface Testable {

    boolean isTestMode();

    boolean cheatShow(UUID gameId, UUID playerId);
}
