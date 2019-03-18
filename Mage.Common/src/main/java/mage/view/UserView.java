
package mage.view;

import java.io.Serializable;
import java.util.Date;

/**
 * Admin Console View
 *
 * @author BetaSteward_at_googlemail.com
 */
public class UserView implements Serializable {

    private final String userName;
    private final String host;
    private final String sessionId;
    private final Date timeConnected;
    private final Date lastActivity;
    private final String gameInfo;
    private final String userState;
    private final Date muteChatUntil;
    private final String clientVersion;
    private final String email;
    private final String userIdStr;

    public UserView(String userName, String host, String sessionId, Date timeConnected, Date lastActivity, String gameInfo, String userState, Date muteChatUntil, String clientVersion, String email, String userIdStr) {
        this.userName = userName;
        this.host = host;
        this.sessionId = sessionId;
        this.timeConnected = timeConnected;
        this.lastActivity = lastActivity;
        this.gameInfo = gameInfo;
        this.userState = userState;
        this.muteChatUntil = muteChatUntil;
        this.clientVersion = clientVersion;
        this.email = email;
        this.userIdStr = userIdStr;
    }

    public String getUserName() {
        return userName;
    }

    public String getHost() {
        return host;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getGameInfo() {
        return gameInfo;
    }

    public String getUserState() {
        return userState;
    }

    public Date getMuteChatUntil() {
        return muteChatUntil;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public Date getTimeConnected() {
        return timeConnected;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public String getEmail() {
        return email;
    }

    public String getUserIdStr() {
        return userIdStr;
    }
}
