package mage.players.net;

import java.io.Serializable;
import java.util.*;

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
    protected int autoTargetLevel;
    protected boolean useSameSettingsForReplacementEffects;
    protected boolean useFirstManaAbility = false;
    private String userIdStr;
    protected Map<UUID, Set<UUID>> requestedHandPlayersList; // game -> players list

    protected String matchHistory;
    protected int matchQuitRatio;
    protected String tourneyHistory;
    protected int tourneyQuitRatio;

    private int generalRating;
    private int constructedRating;
    private int limitedRating;

    public UserData(UserGroup userGroup,
                    int avatarId,
                    boolean showAbilityPickerForced,
                    boolean allowRequestShowHandCards,
                    boolean confirmEmptyManaPool,
                    UserSkipPrioritySteps userSkipPrioritySteps,
                    String flagName,
                    boolean askMoveToGraveOrder,
                    boolean manaPoolAutomatic,
                    boolean manaPoolAutomaticRestricted,
                    boolean passPriorityCast,
                    boolean passPriorityActivation,
                    boolean autoOrderTrigger,
                    int autoTargetLevel,
                    boolean useSameSettingsForReplacementEffects,
                    boolean useFirstManaAbility,
                    String userIdStr) {
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
        this.autoTargetLevel = autoTargetLevel;
        this.useSameSettingsForReplacementEffects = useSameSettingsForReplacementEffects;
        this.useFirstManaAbility = useFirstManaAbility;
        this.matchHistory = "";
        this.matchQuitRatio = 0;
        this.tourneyHistory = "";
        this.tourneyQuitRatio = 0;
        this.userIdStr = userIdStr;
        this.requestedHandPlayersList = new HashMap<>();
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
        this.autoTargetLevel = userData.autoTargetLevel;
        this.useSameSettingsForReplacementEffects = userData.useSameSettingsForReplacementEffects;
        this.useFirstManaAbility = userData.useFirstManaAbility;
        this.userIdStr = userData.userIdStr;
        // todo: why we don't update user stats here? => can't be updated from client side
    }

    public static UserData getDefaultUserDataView() {
        return new UserData(
                UserGroup.DEFAULT,
                0,
                false,
                false,
                true,
                new UserSkipPrioritySteps(),
                getDefaultFlagName(),
                false,
                true,
                true,
                false,
                false,
                true,
                1,
                true,
                false,
                ""
        );
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

    public boolean isAllowRequestHandToAll() {
        return allowRequestShowHandCards;
    }

    public boolean isAllowRequestHandToPlayer(UUID gameId, UUID requesterPlayerId) {
        // once per game
        boolean allowToPlayer = !requestedHandPlayersList.containsKey(gameId) || !requestedHandPlayersList.get(gameId).contains(requesterPlayerId);
        return isAllowRequestHandToAll() && allowToPlayer;
    }

    public void addPlayerToRequestedHandList(UUID gameId, UUID requesterPlayerId) {
        if (!requestedHandPlayersList.containsKey(gameId)) {
            requestedHandPlayersList.put(gameId, new HashSet<>());
        }
        Set<UUID> requestedPlayers = requestedHandPlayersList.get(gameId);
        requestedPlayers.add(requesterPlayerId);
    }

    public void setAllowRequestShowHandCards(boolean allowRequestShowHandCards) {
        this.allowRequestShowHandCards = allowRequestShowHandCards;
    }

    public void resetRequestedHandPlayersList(UUID gameId) {
        this.requestedHandPlayersList.remove(gameId);
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

    public int getAutoTargetLevel() {
        return autoTargetLevel;
    }

    public void setAutoTargetLevel(int autoTargetLevel) {
        this.autoTargetLevel = autoTargetLevel;
    }

    public boolean isUseSameSettingsForReplacementEffects() {
        return useSameSettingsForReplacementEffects;
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
}
