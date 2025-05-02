package mage.remote;

import mage.MageException;
import mage.utils.MageVersion;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MageVersionException extends MageException {

    public MageVersionException(MageVersion clientVersion, MageVersion serverVersion) {
        super("Wrong client version."
                + "<br/>Your version: " + clientVersion
                + "<br/>Server version: " + (serverVersion == null ? "unknown" : serverVersion)
                + "<br/>App download: http://xmage.today");
    }
}
