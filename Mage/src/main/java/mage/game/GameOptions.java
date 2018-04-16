package mage.game;

import mage.constants.PhaseStep;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * Game options for Mage game. Mainly used in tests to configure
 * {@link GameImpl} with specific params.
 *
 * @author ayratn
 */
public class GameOptions implements Serializable {

    private static final GameOptions deinstance = new GameOptions();

    public static GameOptions getDefault() {
        return deinstance;
    }

    /**
     * Defines the running mode. There are some exclusions made for test mode.
     */
    public boolean testMode = false;

    /**
     * Defines the turn number game should stop on. By default, is null meaning
     * that game shouldn't stop on any specific turn.
     */
    public Integer stopOnTurn = null;

    /**
     * Stop at the end of the turn if true otherwise stop at the beginning
     */
    public PhaseStep stopAtStep = PhaseStep.UNTAP;

    /**
     * If true, library won't be shuffled at the beginning of the game
     */
    public boolean skipInitShuffling = false;

    /**
     * If true, players can rollback turn if all players agree
     */
    public boolean rollbackTurnsAllowed = true;

    /**
     * Names of users banned from participating in the game
     */
    public Set<String> bannedUsers = Collections.emptySet();
    
    /**
     * Use planechase variant
     */
    public boolean planeChase = false;

}
