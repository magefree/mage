package mage.remote;

import mage.MageException;
import mage.utils.MageVersion;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MageVersionException extends MageException {

    private final MageVersion serverVersion;

    public MageVersionException(MageVersion clientVersion, MageVersion serverVersion) {
        super("Wrong client version."
                + "<br/>Your version: " + clientVersion
                + "<br/>Server version: " + serverVersion
                + "<br/>Release app download: http://xmage.de"
                + "<br/>BETA app download: http://xmage.today");
        this.serverVersion = serverVersion;
    }

    public MageVersion getServerVersion() {
        return serverVersion;
    }
}
