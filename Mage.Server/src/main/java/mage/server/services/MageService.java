package mage.server.services;

/**
 * Common interface for all services.
 *
 * @author noxx
 */
public interface MageService {
    /**
     * Restores data on startup.
     */
    void initService();

    /**
     * Dumps data to DB.
     */
    void saveData();
}
