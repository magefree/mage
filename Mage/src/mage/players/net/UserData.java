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
    protected boolean manaPoolAutomatic;
    protected boolean manaPoolAutomaticRestricted;
    protected boolean passPriorityCast;
    protected boolean passPriorityActivation;
    protected boolean autoOrderTrigger;

    public UserData(UserGroup userGroup, int avatarId, boolean showAbilityPickerForced,
            boolean allowRequestShowHandCards, boolean confirmEmptyManaPool, UserSkipPrioritySteps userSkipPrioritySteps,
            String flagName, boolean askMoveToGraveOrder, boolean manaPoolAutomatic, boolean manaPoolAutomaticRestricted,
            boolean passPriorityCast, boolean passPriorityActivation, boolean autoOrderTrigger) {
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
        this.passPriorityCast = passPriorityCast;
        this.passPriorityActivation = passPriorityActivation;
        this.autoOrderTrigger = autoOrderTrigger;
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
        this.manaPoolAutomatic = userData.manaPoolAutomatic;
        this.manaPoolAutomaticRestricted = userData.manaPoolAutomaticRestricted;
        this.passPriorityCast = userData.passPriorityCast;
        this.passPriorityActivation = userData.passPriorityActivation;
        this.autoOrderTrigger = userData.autoOrderTrigger;
    }

    public static UserData getDefaultUserDataView() {
        return new UserData(UserGroup.DEFAULT, 0, false, false, true, null, "world.png", false, true, true, false, false, false);
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

    public boolean isPassPriorityCast() {
        return passPriorityCast;
    }

    public void setPassPriorityCast(boolean passPriorityCast) {
        this.passPriorityCast = passPriorityCast;
    }

    public boolean isPassPriorityActivation() {
        return passPriorityActivation;
    }

    public void setPassPriorityActivation(boolean passPriorityActivation) {
        this.passPriorityActivation = passPriorityActivation;
    }

    public boolean isAutoOrderTrigger() {
        return autoOrderTrigger;
    }

    public void setAutoOrderTrigger(boolean autoOrderTrigger) {
        this.autoOrderTrigger = autoOrderTrigger;
    }

}
