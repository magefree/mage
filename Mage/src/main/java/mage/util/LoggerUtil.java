package mage.util;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public class LoggerUtil {
    public static String getLogLevel(Logger logger) {
        String logLevel = "";
        if (logger.isErrorEnabled()) {
            logLevel = Level.ERROR.toString();
        } else if (logger.isWarnEnabled()) {
            logLevel = Level.WARN.toString();
        } else if (logger.isInfoEnabled()) {
            logLevel = Level.INFO.toString();
        } else if (logger.isDebugEnabled()) {
            logLevel = Level.DEBUG.toString();
        } else if (logger.isTraceEnabled()) {
            logLevel = Level.TRACE.toString();
        }
        return logLevel;
    }
}
