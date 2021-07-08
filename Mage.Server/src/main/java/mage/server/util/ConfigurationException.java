package mage.server.util;

import java.util.List;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException(List<String> messages) {
        super(String.join(",", messages));
    }
    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}
