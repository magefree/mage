

package mage.player.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Config {

    private static final Logger logger = Logger.getLogger(Config.class);

//    public static final int maxDepth;
    public static final int maxNodes;
    public static final int evaluatorLifeFactor;
    public static final int evaluatorPermanentFactor;
    public static final int evaluatorCreatureFactor;
    public static final int evaluatorHandFactor;
//    public static final int maxThinkSeconds;

    static {
        Properties p = new Properties();
        try {
            File file = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            p.load(new FileInputStream(new File(file.getParent() + File.separator + "AIMinimax.properties")));
        } catch (IOException ex) {
            logger.fatal("", ex);
        } catch (URISyntaxException ex) {
            logger.fatal("", ex);
        }
//        maxDepth = Integer.parseInt(p.getProperty("maxDepth"));
        maxNodes = Integer.parseInt(p.getProperty("maxNodes"));
        evaluatorLifeFactor = Integer.parseInt(p.getProperty("evaluatorLifeFactor"));
        evaluatorPermanentFactor = Integer.parseInt(p.getProperty("evaluatorPermanentFactor"));
        evaluatorCreatureFactor = Integer.parseInt(p.getProperty("evaluatorCreatureFactor"));
        evaluatorHandFactor = Integer.parseInt(p.getProperty("evaluatorHandFactor"));
//        maxThinkSeconds = Integer.parseInt(p.getProperty("maxThinkSeconds"));
    }

}
