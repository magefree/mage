package mage.view;

import mage.players.net.UserData;
import mage.players.net.UserSkipPrioritySteps;

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
    protected boolean confirmEmptyManaPool;
    protected UserSkipPrioritySteps userSkipPrioritySteps;
    String flagName;
    protected boolean askMoveToGraveOrder;

    static UserData getDefaultUserData() {
        return UserData.getDefaultUserDataView();
    }

    public static UserDataView getDefaultUserDataView() {
        return new UserDataView(getDefaultUserData());
    }

    public UserDataView(int avatarId, boolean showAbilityPickerForced, boolean allowRequestShowHandCards,
                        boolean confirmEmptyManaPool, UserSkipPrioritySteps userSkipPrioritySteps, String flagName, boolean askMoveToGraveOrder) {
        this.avatarId = avatarId;
        this.showAbilityPickerForced = showAbilityPickerForced;
        this.userSkipPrioritySteps = userSkipPrioritySteps;
        this.confirmEmptyManaPool = confirmEmptyManaPool;
        this.flagName = flagName;
        this.askMoveToGraveOrder = askMoveToGraveOrder;

    }

    public UserDataView(UserData userData) {
        this.avatarId = userData.getAvatarId();
        this.userGroup = userData.getGroupId();
        this.showAbilityPickerForced = userData.isShowAbilityPickerForced();
        this.userSkipPrioritySteps = userData.getUserSkipPrioritySteps();
        this.confirmEmptyManaPool = userData.confirmEmptyManaPool();
        this.flagName = userData.getFlagName();
        this.askMoveToGraveOrder = userData.askMoveToGraveOrder();
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

    public boolean confirmEmptyManaPool() {
        return confirmEmptyManaPool;
    }

    public String getFlagName() {
        return flagName;
    }

    public boolean askMoveToGraveOrder() {
        return askMoveToGraveOrder;
    }

}
