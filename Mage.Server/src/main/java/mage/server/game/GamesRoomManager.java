

package mage.server.game;

import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum GamesRoomManager {
    instance;

    private final ConcurrentHashMap<UUID, GamesRoom> rooms = new ConcurrentHashMap<>();
    private final UUID mainRoomId;
    private static final Logger logger = Logger.getLogger(GamesRoomManager.class);


    GamesRoomManager() {
        GamesRoom mainRoom = new GamesRoomImpl();
        mainRoomId = mainRoom.getRoomId();
        rooms.put(mainRoomId, mainRoom);
    }

    public UUID createRoom() {
        GamesRoom room = new GamesRoomImpl();
        rooms.put(room.getRoomId(), room);
        return room.getRoomId();
    }

    public UUID getMainRoomId() {
        return mainRoomId;
    }

    public Optional<GamesRoom> getRoom(UUID roomId) {
        if(rooms.containsKey(roomId)) {
            return Optional.of(rooms.get(roomId));
        }
        logger.error("room not found : " + roomId);
        return Optional.empty();

    }

    public void removeTable(UUID tableId) {
        for (GamesRoom room : rooms.values()) {
            room.removeTable(tableId);
        }
    }

}
