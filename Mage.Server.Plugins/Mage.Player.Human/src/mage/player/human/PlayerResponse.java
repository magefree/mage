/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

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
    
    public PlayerResponse() {
        clear();
    }
    
    public String toString() {
        return new StringBuilder((responseString == null) ? "null" : responseString)
                .append(',')
                .append(responseUUID)
                .append(',')
                .append(responseBoolean)
                .append(',')
                .append(responseInteger)
                .append(',')
                .append(responseManaType)
                .append(',')
                .append(responseManaTypePlayerId).toString();
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
    }

    public void clear() {
        responseString = null;
        responseUUID = null;
        responseBoolean = null;
        responseInteger = null;
        responseManaType = null;
        responseManaTypePlayerId = null;
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
