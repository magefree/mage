package mage.server.managers;

import mage.server.game.GamesRoom;

import java.util.Optional;
import java.util.UUID;

public interface GamesRoomManager {
    UUID createRoom();

    UUID getMainRoomId();

    UUID getMainChatId();

    Optional<GamesRoom> getRoom(UUID roomId);

    void removeTable(UUID tableId);
}
