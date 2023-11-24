package mage.player.human;

import mage.constants.ManaType;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.UUID;

/**
 * Network: server side data for waiting a user's response like new choice
 * <p>
 * Details:
 * - one response object per user;
 * - support multiple data types;
 * - waiting and writing response on diff threads;
 * - start by response.wait (game thread) and end by response.notifyAll (network/call thread)
 * - user's request can income in diff order, so only one latest response allowed (except async commands like concede and cheat)
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class PlayerResponse implements Serializable, Copyable<PlayerResponse> {

    private String activeAction; // for debug information

    private String responseString;
    private UUID responseUUID;
    private Boolean responseBoolean;
    private Integer responseInteger;
    private ManaType responseManaType;
    private UUID responseManaPlayerId;

    // async commands can income any time from network thread as signal,
    // must process it in game thread on any priority
    // TODO: is concede/hand view confirmation can broken waiting cycle for other choosing/priority player
    //  (with same response type, with diff response type)???
    private Boolean asyncWantConcede;
    private Boolean asyncWantCheat;

    public PlayerResponse() {
        clear();
    }

    @Override
    public String toString() {
        return ((this.responseString == null) ? "null" : this.responseString)
                + "," + this.responseUUID
                + "," + this.responseBoolean
                + "," + this.responseInteger
                + "," + this.responseManaType
                + "," + this.responseManaPlayerId
                + "," + this.asyncWantConcede
                + "," + this.asyncWantCheat
                + " (" + (this.activeAction == null ? "sleep" : this.activeAction) + ")";
    }

    protected PlayerResponse(final PlayerResponse response) {
        this.copyFrom(response);
    }

    @Override
    public PlayerResponse copy() {
        return new PlayerResponse(this);
    }

    public void copyFrom(final PlayerResponse response) {
        this.activeAction = response.activeAction;
        this.responseString = response.responseString;
        this.responseUUID = response.responseUUID;
        this.responseBoolean = response.responseBoolean;
        this.responseInteger = response.responseInteger;
        this.responseManaType = response.responseManaType;
        this.responseManaPlayerId = response.responseManaPlayerId;
        this.asyncWantConcede = response.asyncWantConcede;
        this.asyncWantCheat = response.asyncWantCheat;
    }

    public void clear() {
        this.activeAction = null;
        this.responseString = null;
        this.responseUUID = null;
        this.responseBoolean = null;
        this.responseInteger = null;
        this.responseManaType = null;
        this.responseManaPlayerId = null;
        this.asyncWantConcede = null;
        this.asyncWantCheat = null;
    }

    public String getActiveAction() {
        return this.activeAction;
    }

    public void setActiveAction(String activeAction) {
        this.activeAction = activeAction;
    }

    public String getString() {
        return this.responseString;
    }

    public void setString(String newString) {
        this.responseString = newString;
    }

    public UUID getUUID() {
        return this.responseUUID;
    }

    public void setUUID(UUID newUUID) {
        this.responseUUID = newUUID;
    }

    public Boolean getBoolean() {
        return this.responseBoolean;
    }

    public void setBoolean(Boolean newBoolean) {
        this.responseBoolean = newBoolean;
    }

    public Integer getInteger() {
        return responseInteger;
    }

    public void setInteger(Integer newInteger) {
        this.responseInteger = newInteger;
    }

    public ManaType getManaType() {
        return this.responseManaType;
    }

    public void setManaType(ManaType newManaType) {
        this.responseManaType = newManaType;
    }

    public UUID getManaPlayerId() {
        return this.responseManaPlayerId;
    }

    public void setResponseManaPlayerId(UUID newManaPlayerId) {
        this.responseManaPlayerId = newManaPlayerId;
    }

    public Boolean getAsyncWantConcede() {
        return this.asyncWantConcede != null && this.asyncWantConcede;
    }

    public void setAsyncWantConcede() {
        this.asyncWantConcede = true;
    }

    public Boolean getAsyncWantCheat() {
        return this.asyncWantCheat != null && this.asyncWantCheat;
    }

    /**
     * Start cheat dialog on next player's priority
     */
    public void setAsyncWantCheat() {
        this.asyncWantCheat = true;
    }
}
