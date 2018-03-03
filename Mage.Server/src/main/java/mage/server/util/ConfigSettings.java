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

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import mage.server.util.config.Config;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum ConfigSettings {
    instance;
    private final Logger logger = Logger.getLogger(ConfigSettings.class);

    private Config config;

    ConfigSettings() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("mage.server.util.config");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            config = (Config) unmarshaller.unmarshal(new File("config/config.xml"));
        } catch (JAXBException ex) {
            logger.fatal("ConfigSettings error", ex);
        }
    }

    public String getServerAddress() {
        return config.getServer().getServerAddress();
    }

    public String getServerName() {
        return config.getServer().getServerName();
    }

    public int getPort() {
        return config.getServer().getPort().intValue();
    }

    public int getSecondaryBindPort() {
        return config.getServer().getSecondaryBindPort().intValue();
    }

    public int getLeasePeriod() {
        return config.getServer().getLeasePeriod().intValue();
    }

    public int getSocketWriteTimeout() {
        return config.getServer().getSocketWriteTimeout().intValue();
    }

    public int getMaxPoolSize() {
        return config.getServer().getMaxPoolSize().intValue();
    }

    public int getNumAcceptThreads() {
        return config.getServer().getNumAcceptThreads().intValue();
    }

    public int getBacklogSize() {
        return config.getServer().getBacklogSize().intValue();
    }

    public int getMaxGameThreads() {
        return config.getServer().getMaxGameThreads().intValue();
    }

    public int getMaxSecondsIdle() {
        return config.getServer().getMaxSecondsIdle().intValue();
    }

    public int getMinUserNameLength() {
        return config.getServer().getMinUserNameLength().intValue();
    }

    public int getMaxUserNameLength() {
        return config.getServer().getMaxUserNameLength().intValue();
    }

    public String getInvalidUserNamePattern() {
        return config.getServer().getInvalidUserNamePattern();
    }

    public int getMinPasswordLength() {
        return config.getServer().getMinPasswordLength().intValue();
    }

    public int getMaxPasswordLength() {
        return config.getServer().getMaxPasswordLength().intValue();
    }

    public String getMaxAiOpponents() {
        return config.getServer().getMaxAiOpponents();
    }

   public Boolean isSaveGameActivated() {
        return config.getServer().isSaveGameActivated();
    }

    public Boolean isAuthenticationActivated() {
        return config.getServer().isAuthenticationActivated();
    }

    public String getGoogleAccount() {
        return config.getServer().getGoogleAccount();
    }

    public String getMailgunApiKey() {
        return config.getServer().getMailgunApiKey();
    }

    public String getMailgunDomain() {
        return config.getServer().getMailgunDomain();
    }

    public String getMailSmtpHost() {
        return config.getServer().getMailSmtpHost();
    }

    public String getMailSmtpPort() {
        return config.getServer().getMailSmtpPort();
    }

    public String getMailUser() {
        return config.getServer().getMailUser();
    }

    public String getMailPassword() {
        return config.getServer().getMailPassword();
    }

    public String getMailFromAddress() {
        return config.getServer().getMailFromAddress();
    }

    public List<Plugin> getPlayerTypes() {
        return config.getPlayerTypes().getPlayerType();
    }

    public List<GamePlugin> getGameTypes() {
        return config.getGameTypes().getGameType();
    }

    public List<GamePlugin> getTournamentTypes() {
        return config.getTournamentTypes().getTournamentType();
    }

    public List<Plugin> getDraftCubes() {
        return config.getDraftCubes().getDraftCube();
    }

    public List<Plugin> getDeckTypes() {
        return config.getDeckTypes().getDeckType();
    }

    public String getSigningKey() {
        //
        // TODO: I can't get JAXB to regenerate the config
        //       java classes; so diverving from the pattern
        //       here for now. Posted the build log here:
        //       https://gist.github.com/hooptie45/77fd37eba03861bd7b262f5dd7f4e92a
        //
        // TODO: This would probably be better
        //       as an ENV var, as I'm not crazy about storing the
        //       key in the repo, a production deploy
        //
        // TODO: Follow up about the S3 bucket keys, which
        //       are needed for the logging work in #4554;
        //       but couldn't find a good place to but them.
        //
        // NOTE: The fallback key is ONLY meant for local dev,
        //       is it possile to throw based on the current
        //       deployment ENV? Might be a good time to
        //       indroduce a containerized system.
        //
        return System.getenv("JWT_SIGNING_KEY") != null ? System.getenv("JWT_SIGNING_KEY")
                                                        : "f85a25571c20c0fadfd7a52f769747c7c62f8910";
    }

}
