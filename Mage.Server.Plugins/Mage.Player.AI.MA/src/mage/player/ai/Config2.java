

package mage.player.ai;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Config2 {

    private static final Logger logger = Logger.getLogger(Config2.class);

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
            File file = new File(Config2.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            File propertiesFile = new File(file.getParent() + File.separator + "AIMinimax.properties");
            if (propertiesFile.exists()) {
                p.load(new FileInputStream(propertiesFile));
            } else {
//                p.setProperty("maxDepth", "10");
                p.setProperty("maxNodes", "50000");
                p.setProperty("evaluatorLifeFactor", "2");
                p.setProperty("evaluatorPermanentFactor", "1");
                p.setProperty("evaluatorCreatureFactor", "1");
                p.setProperty("evaluatorHandFactor", "1");
//                p.setProperty("maxThinkSeconds", "30");
            }
        } catch (IOException ex) {
            logger.error(null, ex);
        } catch (URISyntaxException ex) {
            logger.error(null, ex);
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
