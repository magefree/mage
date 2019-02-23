

package mage.server.util;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Config {

    private Config(){}

    private static final Logger logger = Logger.getLogger(Config.class);

    static {
        Properties p = new Properties();
        try {
            p.load(Config.class.getResourceAsStream("resources/config.properties"));
        } catch (IOException ex) {
            logger.fatal("Config error", ex);
        }
        port = Integer.parseInt(p.getProperty("port"));
        secondaryBindPort = Integer.parseInt(p.getProperty("secondaryBindPort"));
        backlogSize = Integer.parseInt(p.getProperty("backlogSize"));
        numAcceptThreads = Integer.parseInt(p.getProperty("numAcceptThreads"));
        maxPoolSize = Integer.parseInt(p.getProperty("numPoolSize"));
        leasePeriod = Integer.parseInt(p.getProperty("leasePeriod"));
        
        remoteServer = p.getProperty("remote-server");
        maxGameThreads = Integer.parseInt(p.getProperty("max-game-threads"));
        maxSecondsIdle = Integer.parseInt(p.getProperty("max-seconds-idle"));
        minUserNameLength = Integer.parseInt(p.getProperty("minUserNameLength"));
        maxUserNameLength = Integer.parseInt(p.getProperty("maxUserNameLength"));
        userNamePattern = p.getProperty("userNamePattern");
        saveGameActivated = Boolean.parseBoolean(p.getProperty("saveGameActivated"));
    }

    public static final String remoteServer;
    public static final int port;
    public static final int secondaryBindPort;
    public static final int backlogSize;
    public static final int numAcceptThreads;
    public static final int maxPoolSize;
    public static final int leasePeriod;
    public static final int maxGameThreads;
    public static final int maxSecondsIdle;
    public static final int minUserNameLength;
    public static final int maxUserNameLength;
    public static final String userNamePattern;
    public static final boolean saveGameActivated;

}
