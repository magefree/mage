

package mage.view;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author LevelX2
 */
public class RoomUsersView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int numberActiveGames;
    private final int numberGameThreads;
    private final int numberMaxGames;

    private final List<UsersView> usersView;

    public RoomUsersView(List<UsersView> usersView, int numberActiveGames, int numberGameThreads, int numberMaxGames) {

        this.numberActiveGames = numberActiveGames;
        this.numberGameThreads = numberGameThreads;
        this.numberMaxGames = numberMaxGames;
        
        this.usersView = usersView;
    }

    public int getNumberActiveGames() {
        return numberActiveGames;
    }

    public int getNumberGameThreads() {
        return numberGameThreads;
    }

    public int getNumberMaxGames() {
        return numberMaxGames;
    }

    public List<UsersView> getUsersView() {
        return usersView;
    }

}
