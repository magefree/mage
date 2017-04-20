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
    protected boolean useFirstManaAbility = false;
    private String userIdStr;

    protected String matchHistory;
    protected int matchQuitRatio;
    protected String tourneyHistory;
    protected int tourneyQuitRatio;

    private int generalRating;
    private int constructedRating;
    private int limitedRating;

    public UserData(UserGroup userGroup, int avatarId, boolean showAbilityPickerForced,
            boolean allowRequestShowHandCards, boolean confirmEmptyManaPool, UserSkipPrioritySteps userSkipPrioritySteps,
            String flagName, boolean askMoveToGraveOrder, boolean manaPoolAutomatic, boolean manaPoolAutomaticRestricted,
            boolean passPriorityCast, boolean passPriorityActivation, boolean autoOrderTrigger, boolean useFirstManaAbility, String userIdStr) {
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
        this.useFirstManaAbility = useFirstManaAbility;
        this.matchHistory = "";
        this.matchQuitRatio = 0;
        this.tourneyHistory = "";
        this.tourneyQuitRatio = 0;
        this.userIdStr = userIdStr;
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
        this.useFirstManaAbility = userData.useFirstManaAbility;
        this.userIdStr = userData.userIdStr;
        // todo: why we don't copy user stats here?
    }

    public static UserData getDefaultUserDataView() {
        return new UserData(UserGroup.DEFAULT, 0, false, false, true, null, getDefaultFlagName(), false, true, true, false, false, false, false, "");
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

    public boolean isUseFirstManaAbility() {
        return useFirstManaAbility;
    }

    public void setUseFirstManaAbility(boolean useFirstManaAbility) {
        this.useFirstManaAbility = useFirstManaAbility;
    }

    public String getHistory() {
        if (UserGroup.COMPUTER.equals(this.groupId)) { // Why we are checking UserGroup and integer equality??
            return "";
        }
        // todo: add preference to hide rating?
        return "Matches: " + this.matchHistory + " (" + this.matchQuitRatio + "%), Tourneys: " + this.tourneyHistory + " (" + this.tourneyQuitRatio + "%)"
                + ", Constructed Rating: " + getConstructedRating()
                + ", Limited Rating: " + getLimitedRating();
    }

    public void setMatchHistory(String history) {
        this.matchHistory = history;
    }

    public String getMatchHistory() {
        return matchHistory;
    }

    public void setMatchQuitRatio(int ratio) {
        this.matchQuitRatio = ratio;
    }

    public int getMatchQuitRatio() {
        return matchQuitRatio;
    }

    public void setTourneyHistory(String history) {
        this.tourneyHistory = history;
    }

    public String getTourneyHistory() {
        return tourneyHistory;
    }

    public void setTourneyQuitRatio(int ratio) {
        this.tourneyQuitRatio = ratio;
    }

    public int getTourneyQuitRatio() {
        return tourneyQuitRatio;
    }

    public int getGeneralRating() {
        return generalRating;
    }

    public void setGeneralRating(int generalRating) {
        this.generalRating = generalRating;
    }

    public int getConstructedRating() {
        return constructedRating;
    }

    public void setConstructedRating(int constructedRating) {
        this.constructedRating = constructedRating;
    }

    public int getLimitedRating() {
        return limitedRating;
    }

    public void setLimitedRating(int limitedRating) {
        this.limitedRating = limitedRating;
    }

    public static String getDefaultFlagName() {
        return "world.png";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final UserData userData = (UserData) o;

        if (getGroupId() != userData.getGroupId()) return false;
        if (getAvatarId() != userData.getAvatarId()) return false;
        if (isShowAbilityPickerForced() != userData.isShowAbilityPickerForced()) return false;
        if (isAllowRequestShowHandCards() != userData.isAllowRequestShowHandCards()) return false;
        if (confirmEmptyManaPool != userData.confirmEmptyManaPool) return false;
        if (askMoveToGraveOrder != userData.askMoveToGraveOrder) return false;
        if (isManaPoolAutomatic() != userData.isManaPoolAutomatic()) return false;
        if (isManaPoolAutomaticRestricted() != userData.isManaPoolAutomaticRestricted())
            return false;
        if (isPassPriorityCast() != userData.isPassPriorityCast()) return false;
        if (isPassPriorityActivation() != userData.isPassPriorityActivation()) return false;
        if (isAutoOrderTrigger() != userData.isAutoOrderTrigger()) return false;
        if (isUseFirstManaAbility() != userData.isUseFirstManaAbility()) return false;
        if (getMatchQuitRatio() != userData.getMatchQuitRatio()) return false;
        if (getTourneyQuitRatio() != userData.getTourneyQuitRatio()) return false;
        if (getGeneralRating() != userData.getGeneralRating()) return false;
        if (getConstructedRating() != userData.getConstructedRating()) return false;
        if (getLimitedRating() != userData.getLimitedRating()) return false;
        if (getUserSkipPrioritySteps() != null ? !getUserSkipPrioritySteps().equals(userData
            .getUserSkipPrioritySteps()) : userData.getUserSkipPrioritySteps() != null)
            return false;
        if (getFlagName() != null ? !getFlagName().equals(userData.getFlagName()) : userData
            .getFlagName() != null)
            return false;
        if (userIdStr != null ? !userIdStr.equals(userData.userIdStr) : userData.userIdStr != null)
            return false;
        if (getMatchHistory() != null ? !getMatchHistory().equals(userData.getMatchHistory()) :
            userData.getMatchHistory() != null)
            return false;
        return getTourneyHistory() != null ? getTourneyHistory().equals(userData
            .getTourneyHistory()) : userData.getTourneyHistory() == null;

    }

    @Override
    public int hashCode() {
        int result = getGroupId();
        result = 31 * result + getAvatarId();
        result = 31 * result + (isShowAbilityPickerForced() ? 1 : 0);
        result = 31 * result + (isAllowRequestShowHandCards() ? 1 : 0);
        result = 31 * result + (confirmEmptyManaPool ? 1 : 0);
        result = 31 * result + (getUserSkipPrioritySteps() != null ? getUserSkipPrioritySteps()
            .hashCode() : 0);
        result = 31 * result + (getFlagName() != null ? getFlagName().hashCode() : 0);
        result = 31 * result + (askMoveToGraveOrder ? 1 : 0);
        result = 31 * result + (isManaPoolAutomatic() ? 1 : 0);
        result = 31 * result + (isManaPoolAutomaticRestricted() ? 1 : 0);
        result = 31 * result + (isPassPriorityCast() ? 1 : 0);
        result = 31 * result + (isPassPriorityActivation() ? 1 : 0);
        result = 31 * result + (isAutoOrderTrigger() ? 1 : 0);
        result = 31 * result + (isUseFirstManaAbility() ? 1 : 0);
        result = 31 * result + (userIdStr != null ? userIdStr.hashCode() : 0);
        result = 31 * result + (getMatchHistory() != null ? getMatchHistory().hashCode() : 0);
        result = 31 * result + getMatchQuitRatio();
        result = 31 * result + (getTourneyHistory() != null ? getTourneyHistory().hashCode() : 0);
        result = 31 * result + getTourneyQuitRatio();
        result = 31 * result + getGeneralRating();
        result = 31 * result + getConstructedRating();
        result = 31 * result + getLimitedRating();
        return result;
    }
}
