package mage.server.util;

import mage.server.util.config.Config;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ConfigSettings {

    private static final Logger logger = Logger.getLogger(ConfigSettings.class);
    private static final ConfigSettings INSTANCE = new ConfigSettings();

    private Config config;

    public static ConfigSettings getInstance() {
        return INSTANCE;
    }

    private ConfigSettings() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("mage.server.util.config");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            config = (Config) unmarshaller.unmarshal(new File("config/config.xml"));
        } catch (JAXBException ex) {
            logger.fatal("ConfigSettings error", ex);
        }
    }

    public String getServerName() {
        return config.getServer().getServerName();
    }

    public int getPort() {
        return config.getServer().getPort().intValue();
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
    
    public String getMaxAiOpponents() {
        return config.getServer().getMaxAiOpponents();
    }

    public Boolean isSaveGameActivated() {
        return config.getServer().isSaveGameActivated();
    }

    public Boolean isUseSSL() {
        return config.getServer().isUseSSL();
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

}
