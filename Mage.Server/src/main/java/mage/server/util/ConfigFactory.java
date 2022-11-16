package mage.server.util;

import mage.server.util.config.Config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class ConfigFactory {

    public static Config loadFromFile(final String filePath) {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance("mage.server.util.config");
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Config) unmarshaller.unmarshal(new File(filePath));
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }
}
