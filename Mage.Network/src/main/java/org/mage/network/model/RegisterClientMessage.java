package org.mage.network.model;

import java.io.Serializable;
import mage.utils.MageVersion;

/**
 *
 * @author BetaSteward
 */
public class RegisterClientMessage implements Serializable {
    private String userName;
    private MageVersion version;
    
    public RegisterClientMessage(String userName, MageVersion version) {
        this.userName = userName;
        this.version = version;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public MageVersion getMageVersion() {
        return version;
    }
    
}
