package mage.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import mage.cards.CardDimensions;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Config {

    // TODO: Remove this class completely
    private static final Logger logger = Logger.getLogger(Config.class);

    public static final String remoteServer;
    public static final String serverName;
    public static final int port;
    public static final double cardScalingFactor;
    public static final double cardScalingFactorEnlarged;
    public static final double handScalingFactor;
    public static final CardDimensions dimensions;
    public static final CardDimensions dimensionsEnlarged;

    public static final String defaultGameType;
    public static final String defaultDeckPath;
    public static final String defaultOtherPlayerIndex;
    public static final String defaultComputerName;

    static {
        Properties p = new Properties();
        boolean fileFound = true;
        try (FileInputStream fis = new FileInputStream(new File("config/config.properties"))) {
            p.load(fis);
        } catch (IOException ex) {
            fileFound = false;
        }
        if (fileFound) {
            serverName = p.getProperty("server-name");
            port = Integer.parseInt(p.getProperty("port"));
            remoteServer = p.getProperty("remote-server");
            cardScalingFactor = Double.valueOf(p.getProperty("card-scaling-factor"));
            cardScalingFactorEnlarged = Double.valueOf(p.getProperty("card-scaling-factor-enlarged"));
            handScalingFactor = Double.valueOf(p.getProperty("hand-scaling-factor"));
            defaultGameType = p.getProperty("default-game-type", "Human");
            defaultDeckPath = p.getProperty("default-deck-path");
            defaultOtherPlayerIndex = p.getProperty("default-other-player-index");
            defaultComputerName = p.getProperty("default-computer-name");

            dimensions = new CardDimensions(cardScalingFactor);
            dimensionsEnlarged = new CardDimensions(cardScalingFactorEnlarged);
        } else { // Take some default valies for netbeans design view
            serverName = "localhost";
            port = 17171;
            remoteServer = "mage-server";
            cardScalingFactor = 0.4;
            cardScalingFactorEnlarged = 0.5;
            handScalingFactor = 1.3;
            defaultGameType = p.getProperty("default-game-type", "Human");
            defaultDeckPath = "";
            defaultOtherPlayerIndex = "1";
            defaultComputerName = "AI Computer";

            dimensions = new CardDimensions(cardScalingFactor);
            dimensionsEnlarged = new CardDimensions(cardScalingFactorEnlarged);
        }

    }

}
