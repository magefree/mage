package org.mage.network.model;

import java.io.Serializable;
import mage.remote.Connection;
import mage.utils.MageVersion;

/**
 *
 * @author BetaSteward
 */
public class RegisterClientRequest implements Serializable {
    private Connection connection;
    private MageVersion version;
    
    public RegisterClientRequest(Connection connection, MageVersion version) {
        this.connection = connection;
        this.version = version;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public MageVersion getMageVersion() {
        return version;
    }
    
}
