package mage.client.util;

/**
 * Controls game state on client side.
 *
 * @author nantuko
 */
public class GameManager {
    private static GameManager fInstance = new GameManager();
    public static GameManager getInstance() {
        return fInstance;
    }
    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }
    public int getStackSize() {
        return stackSize;
    }

    private int stackSize;
}
