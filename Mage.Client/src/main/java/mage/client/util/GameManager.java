package mage.client.util;

import java.util.UUID;

/**
 * Controls game state on client side.
 *
 * @author nantuko
 */
public class GameManager {
    private static final GameManager instance = new GameManager();

    public static GameManager getInstance() {
        return instance;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public int getStackSize() {
        return stackSize;
    }

    public UUID getCurrentPlayerUUID() {
        return currentPlayerUUID;
    }

    public void setCurrentPlayerUUID(UUID currentPlayerUUID) {
        this.currentPlayerUUID = currentPlayerUUID;
    }

    private int stackSize;
    private UUID currentPlayerUUID;
}
