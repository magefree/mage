
package mage.player.human;

import java.io.Serializable;
import java.util.UUID;
import mage.constants.ManaType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerResponse implements Serializable {

    private String responseString;
    private UUID responseUUID;
    private Boolean responseBoolean;
    private Integer responseInteger;
    private ManaType responseManaType;
    private UUID responseManaTypePlayerId;
    private Boolean responseConcedeCheck;

    public PlayerResponse() {
        clear();
    }

    @Override
    public String toString() {
        return ((responseString == null) ? "null" : responseString)
                + ',' + responseUUID
                + ',' + responseBoolean
                + ',' + responseInteger
                + ',' + responseManaType
                + ',' + responseManaTypePlayerId
                + ',' + responseConcedeCheck;
    }

    public PlayerResponse(PlayerResponse other) {
        copy(other);
    }

    public void copy(PlayerResponse other) {
        responseString = other.responseString;
        responseUUID = other.responseUUID;
        responseBoolean = other.responseBoolean;
        responseInteger = other.responseInteger;
        responseManaType = other.responseManaType;
        responseManaTypePlayerId = other.responseManaTypePlayerId;
        responseConcedeCheck = other.responseConcedeCheck;
    }

    public void clear() {
        responseString = null;
        responseUUID = null;
        responseBoolean = null;
        responseInteger = null;
        responseManaType = null;
        responseManaTypePlayerId = null;
        responseConcedeCheck = null;
    }

    public String getString() {
        return responseString;
    }

    public void setString(String responseString) {
        this.responseString = responseString;
    }

    public UUID getUUID() {
        return responseUUID;
    }

    public void setUUID(UUID responseUUID) {
        this.responseUUID = responseUUID;
    }

    public Boolean getBoolean() {
        return responseBoolean;
    }

    public void setBoolean(Boolean responseBoolean) {
        this.responseBoolean = responseBoolean;
    }

    public Boolean getResponseConcedeCheck() {
        if (responseConcedeCheck == null) {
            return false;
        }
        return responseConcedeCheck;
    }

    public void setResponseConcedeCheck() {
        this.responseConcedeCheck = true;
    }

    public Integer getInteger() {
        return responseInteger;
    }

    public void setInteger(Integer responseInteger) {
        this.responseInteger = responseInteger;
    }

    public ManaType getManaType() {
        return responseManaType;
    }

    public void setManaType(ManaType responseManaType) {
        this.responseManaType = responseManaType;
    }

    public UUID getResponseManaTypePlayerId() {
        return responseManaTypePlayerId;
    }

    public void setResponseManaTypePlayerId(UUID responseManaTypePlayerId) {
        this.responseManaTypePlayerId = responseManaTypePlayerId;
    }

}
