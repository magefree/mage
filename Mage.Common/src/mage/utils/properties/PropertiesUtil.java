package mage.utils.properties;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author noxx
 */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static final String LOG_JDBC_URL = "jdbc:h2:file:./db/mage.h2;AUTO_SERVER=TRUE";
    private static final String FEEDBACK_JDBC_URL = "jdbc:h2:file:./db/feedback.h2;AUTO_SERVER=TRUE";

    private static Properties properties = new Properties();

    static {
        InputStream in = PropertiesUtil.class.getResourceAsStream("/xmage.properties");
        if (in != null) {
            try {
                properties.load(in);
            } catch (IOException e) {
                logger.error(e, "Couldn't load properties");
            }
        } else {
            logger.warn("No xmage.properties were found on classpath");
        }
    }

    /**
     * Hide constructor
     */
    private PropertiesUtil() {

    }

    public static String getDBLogUrl() {
        String url = properties.getProperty(PropertyKeys.KEY_DB_LOG_URL, LOG_JDBC_URL);
        if (url != null) {
            return url.trim();
        }
        return null;
    }

    public static String getDBFeedbackUrl() {
        String url = properties.getProperty(PropertyKeys.KEY_DB_FEEDBACK_URL, FEEDBACK_JDBC_URL);
        if (url != null) {
            return url.trim();
        }
        return null;
    }
}
