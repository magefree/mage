package mage.server.services;

/**
 * Responsible for gathering logs and storing them in DB.
 *
 * @author noxx
 */
public interface LogService {

    /**
     * Logs any information
     *
     * @param key Log key. Should be the same for the same types of logs.
     * @param args Any parameters in string representation.
     */
    void log(String key, String... args);
}
