package mage.players.net;

import java.io.Serializable;

/**
 * User data that is passed during connection to the server.
 *
 * @author ayrat
 */
public class UserData implements Serializable {

    protected int groupId;
    protected int avatarId;
    protected boolean showAbilityPickerForced;
    protected boolean allowRequestShowHandCards;
    protected boolean confirmEmptyManaPool;
    protected UserSkipPrioritySteps userSkipPrioritySteps;
    protected String flagName;
    protected boolean askMoveToGraveOrder;

    public UserData(UserGroup userGroup, int avatarId, boolean showAbilityPickerForced,
            boolean allowRequestShowHandCards, boolean confirmEmptyManaPool, UserSkipPrioritySteps userSkipPrioritySteps,
            String flagName, boolean askMoveToGraveOrder) {
        this.groupId = userGroup.getGroupId();
        this.avatarId = avatarId;
        this.showAbilityPickerForced = showAbilityPickerForced;
        this.allowRequestShowHandCards = allowRequestShowHandCards;
        this.userSkipPrioritySteps = userSkipPrioritySteps;
        this.confirmEmptyManaPool = confirmEmptyManaPool;
        this.flagName = flagName;
        this.askMoveToGraveOrder = askMoveToGraveOrder;
    }

    public void update(UserData userData) {
        this.groupId = userData.groupId;
        this.avatarId = userData.avatarId;
        this.showAbilityPickerForced = userData.showAbilityPickerForced;
        this.allowRequestShowHandCards = userData.allowRequestShowHandCards;
        this.userSkipPrioritySteps = userData.userSkipPrioritySteps;
        this.confirmEmptyManaPool = userData.confirmEmptyManaPool;
        this.flagName = userData.flagName;
        this.askMoveToGraveOrder = userData.askMoveToGraveOrder;
    }

    public static UserData getDefaultUserDataView() {
        return new UserData(UserGroup.DEFAULT, 0, false, false, true, null, "world.png", false);
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public boolean isShowAbilityPickerForced() {
        return showAbilityPickerForced;
    }

    public void setShowAbilityPickerForced(boolean showAbilityPickerForced) {
        this.showAbilityPickerForced = showAbilityPickerForced;
    }

    public boolean isAllowRequestShowHandCards() {
        return allowRequestShowHandCards;
    }

    public void setAllowRequestShowHandCards(boolean allowRequestShowHandCards) {
        this.allowRequestShowHandCards = allowRequestShowHandCards;
    }

    public UserSkipPrioritySteps getUserSkipPrioritySteps() {
        return userSkipPrioritySteps;
    }

    public void setUserSkipPrioritySteps(UserSkipPrioritySteps userSkipPrioritySteps) {
        this.userSkipPrioritySteps = userSkipPrioritySteps;
    }

    public boolean confirmEmptyManaPool() {
        return confirmEmptyManaPool;
    }

    public void setConfirmEmptyManaPool(boolean confirmEmptyManaPool) {
        this.confirmEmptyManaPool = confirmEmptyManaPool;
    }

    public String getFlagName() {
        return flagName;
    }

    public boolean askMoveToGraveOrder() {
        return askMoveToGraveOrder;
    }

    public void setAskMoveToGraveOrder(boolean askMoveToGraveOrder) {
        this.askMoveToGraveOrder = askMoveToGraveOrder;
    }

}
