

package mage.game.draft;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageItem;
import mage.cards.ExpansionSet;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Draft extends MageItem, Serializable {

    void addPlayer(Player player);
    Collection<DraftPlayer> getPlayers();
    boolean replacePlayer(Player oldPlayer, Player newPlayer);
    DraftPlayer getPlayer(UUID playerId);
    int getNumberBoosters();
    DraftCube getDraftCube();
    List<ExpansionSet> getSets();
    int getBoosterNum();
    int getCardNum();
    boolean addPick(UUID playerId, UUID cardId, Set<UUID> hiddenCards);
    void setBoosterLoaded(UUID playerID);
    void start();
    boolean isStarted();
    void setStarted();

    boolean allJoined();
    void leave(UUID playerId);
    void autoPick(UUID playerId);

    void addTableEventListener(Listener<TableEvent> listener);
    void fireUpdatePlayersEvent();
    void fireEndDraftEvent();
    void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener);
    void firePickCardEvent(UUID playerId);

    boolean isAbort();
    void setAbort(boolean abort);

}
