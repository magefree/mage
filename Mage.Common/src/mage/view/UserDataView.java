package mage.view;

import mage.players.net.UserData;

import java.io.Serializable;
import mage.players.net.UserSkipPrioritySteps;

/**
 * Transfer object for {@link mage.players.net.UserData}
 *
 * @author ayrat
 */
public class UserDataView implements Serializable {

    protected int avatarId;
    protected int userGroup;
    protected boolean showAbilityPickerForced;
    protected UserSkipPrioritySteps userSkipPrioritySteps;

    public UserDataView(int avatarId, boolean showAbilityPickerForced, UserSkipPrioritySteps userSkipPrioritySteps) {
        this.avatarId = avatarId;
        this.showAbilityPickerForced = showAbilityPickerForced;
        this.userSkipPrioritySteps = userSkipPrioritySteps;
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

    public UserSkipPrioritySteps getUserSkipPrioritySteps() {
        return userSkipPrioritySteps;
    }
    
}
