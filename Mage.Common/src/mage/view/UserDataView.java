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
    protected boolean showAbilityPickerForced;

    public UserDataView(int avatarId, boolean showAbilityPickerForced) {
        this.avatarId = avatarId;
        this.showAbilityPickerForced = showAbilityPickerForced;
    }

    public UserDataView(UserData userData) {
        this.avatarId = userData.getAvatarId();
        this.userGroup = userData.getGroupId();
    }

    public int getAvatarId() {
        return avatarId;
    }

    public boolean isShowAbilityPickerForced() {
        return showAbilityPickerForced;
    }
    
}
