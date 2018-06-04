
package mage.remote.interfaces;

import java.util.Optional;
import java.util.UUID;

/**
 * @author noxx
 */
public interface ChatSession {

    Optional<UUID> getRoomChatId(UUID roomId);

    Optional<UUID> getTableChatId(UUID tableId);

    Optional<UUID> getGameChatId(UUID gameId);

    Optional<UUID> getTournamentChatId(UUID tournamentId);

    boolean joinChat(UUID chatId);

    boolean leaveChat(UUID chatId);

    boolean sendChatMessage(UUID chatId, String message);

    boolean sendBroadcastMessage(String message);
}
