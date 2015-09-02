package mage.view;

import java.io.Serializable;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.players.net.UserSkipPrioritySteps;

/**
 * Transfer object for {@link mage.players.net.UserData}
 *
 * @author ayrat
 */
public class UserDataView implements Serializable {

    protected int groupId;
    protected int avatarId;
    protected boolean showAbilityPickerForced;
    protected boolean allowRequestShowHandCards;
    protected boolean confirmEmptyManaPool;
    protected UserSkipPrioritySteps userSkipPrioritySteps;
    protected String flagName;
    protected boolean askMoveToGraveOrder;
    protected boolean manaPoolAutomatic;
    protected boolean manaPoolAutomaticRestricted;

    static UserData getDefaultUserData() {
        return UserData.getDefaultUserDataView();
    }

    public UserDataView(UserGroup userGroup, int avatarId, boolean showAbilityPickerForced,
            boolean allowRequestShowHandCards, boolean confirmEmptyManaPool, UserSkipPrioritySteps userSkipPrioritySteps,
            String flagName, boolean askMoveToGraveOrder, boolean manaPoolAutomatic, boolean manaPoolAutomaticRestricted) {
        this.groupId = userGroup.getGroupId();
        this.avatarId = avatarId;
        this.showAbilityPickerForced = showAbilityPickerForced;
        this.allowRequestShowHandCards = allowRequestShowHandCards;
        this.userSkipPrioritySteps = userSkipPrioritySteps;
        this.confirmEmptyManaPool = confirmEmptyManaPool;
        this.flagName = flagName;
        this.askMoveToGraveOrder = askMoveToGraveOrder;
        this.manaPoolAutomatic = manaPoolAutomatic;
        this.manaPoolAutomaticRestricted = manaPoolAutomaticRestricted;
    }

    public void update(UserData userData) {
        this.groupId = userData.getGroupId();
        this.avatarId = userData.getAvatarId();
        this.showAbilityPickerForced = userData.isShowAbilityPickerForced();
        this.allowRequestShowHandCards = userData.isAllowRequestShowHandCards();
        this.userSkipPrioritySteps = userData.getUserSkipPrioritySteps();
        this.confirmEmptyManaPool = userData.confirmEmptyManaPool();
        this.flagName = userData.getFlagName();
        this.askMoveToGraveOrder = userData.askMoveToGraveOrder();
        this.manaPoolAutomatic = userData.isManaPoolAutomatic();
        this.manaPoolAutomaticRestricted = userData.isManaPoolAutomaticRestricted();
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

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    public boolean askMoveToGraveOrder() {
        return askMoveToGraveOrder;
    }

    public void setAskMoveToGraveOrder(boolean askMoveToGraveOrder) {
        this.askMoveToGraveOrder = askMoveToGraveOrder;
    }

    public boolean isManaPoolAutomatic() {
        return manaPoolAutomatic;
    }

    public void setManaPoolAutomatic(boolean manaPoolAutomatic) {
        this.manaPoolAutomatic = manaPoolAutomatic;
    }

    public boolean isManaPoolAutomaticRestricted() {
        return manaPoolAutomaticRestricted;
    }

    public void setManaPoolAutomaticRestricted(boolean manaPoolAutomaticRestricted) {
        this.manaPoolAutomaticRestricted = manaPoolAutomaticRestricted;
    }

}
