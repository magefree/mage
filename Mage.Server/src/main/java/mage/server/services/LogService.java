package mage.server.services;

/**
 * Responsible for gathering logs and storing them in DB.
 *
 * @author noxx
 */
public interface LogService {
    public static final String KEY_GAME_STARTED = "gameStarted";

    void log(String key, String... args);
}
