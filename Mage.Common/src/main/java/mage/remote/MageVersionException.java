
package mage.remote;

import mage.MageException;
import mage.utils.MageVersion;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MageVersionException extends MageException {

    private final MageVersion serverVersion;

    public MageVersionException(MageVersion clientVersion, MageVersion serverVersion) {
        super("Wrong client version " + clientVersion + ", expecting version " + serverVersion + ". \r\n\r\nPlease download needed version from http://XMage.de or http://www.slightlymagic.net/forum/viewforum.php?f=70");
        this.serverVersion = serverVersion;
    }

    public MageVersion getServerVersion() {
        return serverVersion;
    }
}
