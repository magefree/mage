package mage.view;

import mage.players.net.UserData;

import java.io.Serializable;

/**
 * Transfer object for {@link mage.players.net.UserData}
 *
 * @author ayrat
 */
public class UserDataView implements Serializable {

    protected int avatarId;
    protected int userGroup;

    public UserDataView(int avatarId) {
        this.avatarId = avatarId;
    }

    public UserDataView(UserData userData) {
        this.avatarId = userData.getAvatarId();
        this.userGroup = userData.getGroupId();
    }

    public int getAvatarId() {
        return avatarId;
    }
}
