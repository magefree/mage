package mage.view;

import java.io.Serializable;
import mage.players.net.UserData;
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
    protected boolean allowRequestShowHandCards;
    protected UserSkipPrioritySteps userSkipPrioritySteps;

    public UserDataView(int avatarId, boolean showAbilityPickerForced, boolean allowRequestShowHandCards, UserSkipPrioritySteps userSkipPrioritySteps) {
        this.avatarId = avatarId;
        this.showAbilityPickerForced = showAbilityPickerForced;
        this.allowRequestShowHandCards = allowRequestShowHandCards;
        this.userSkipPrioritySteps = userSkipPrioritySteps;
    }

    public UserDataView(UserData userData) {
        this.avatarId = userData.getAvatarId();
        this.userGroup = userData.getGroupId();
        this.allowRequestShowHandCards = userData.isAllowRequestShowHandCards();
        this.showAbilityPickerForced = userData.isShowAbilityPickerForced();
        this.userSkipPrioritySteps = userData.getUserSkipPrioritySteps();
    }

    public int getAvatarId() {
        return avatarId;
    }

    public boolean isShowAbilityPickerForced() {
        return showAbilityPickerForced;
    }

    public boolean allowRequestShowHandCards() {
        return allowRequestShowHandCards;
    }

    public UserSkipPrioritySteps getUserSkipPrioritySteps() {
        return userSkipPrioritySteps;
    }
    
}
