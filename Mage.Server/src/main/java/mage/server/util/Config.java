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

package mage.server.util;

import java.io.IOException;
import java.util.Properties;
import mage.server.http.util.JwtAuthHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Config {

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
        signingKey = p.getProperty("signingKey");
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
    public static final String signingKey;
}
