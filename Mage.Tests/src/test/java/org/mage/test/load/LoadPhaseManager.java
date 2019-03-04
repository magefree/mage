package org.mage.test.load;

import mage.constants.PhaseStep;
import mage.view.GameView;
import mage.view.PlayerView;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoadPhaseManager {
    private static final Logger log = Logger.getLogger("Load phase");

    private static final LoadPhaseManager instance = new LoadPhaseManager();

    public static String DEFAULT_PLAYER_NAME = "player";

    public static String PHASE_ON = "on";
    public static String PHASE_OFF = "off";
    public static String UPKEEP_YOU = "upkeepYou";
    public static String DRAW_YOU = "drawYou";
    public static String MAIN_YOU = "mainYou";
    public static String BEFORE_COMBAT_YOU = "beforeCombatYou";
    public static String END_OF_COMBAT_YOU = "endOfCombatYou";
    public static String MAIN_2_YOU = "main2You";
    public static String END_OF_TURN_YOU = "endOfTurnYou";

    public static String UPKEEP_OTHERS = "upkeepOthers";
    public static String DRAW_OTHERS = "drawOthers";
    public static String MAIN_OTHERS = "mainOthers";
    public static String BEFORE_COMBAT_OTHERS = "beforeCombatOthers";
    public static String END_OF_COMBAT_OTHERS = "endOfCombatOthers";
    public static String MAIN_2_OTHERS = "main2Others";
    public static String END_OF_TURN_OTHERS = "endOfTurnOthers";

    private static Map<PhaseStep, Boolean> skipYou;
    static {
        skipYou = new HashMap() {{
            put(PhaseStep.UPKEEP, true);
            put(PhaseStep.PRECOMBAT_MAIN, true);
            put(PhaseStep.BEGIN_COMBAT, true);
            put(PhaseStep.DECLARE_ATTACKERS, true);
            put(PhaseStep.DECLARE_BLOCKERS, true);
            put(PhaseStep.END_COMBAT, true);
            put(PhaseStep.POSTCOMBAT_MAIN, true);
            put(PhaseStep.END_TURN, true);
        }};
    }

    private static Map<PhaseStep, Boolean> skipOthers;
    static {
        skipYou = new HashMap() {{
            put(PhaseStep.UPKEEP, true);
            put(PhaseStep.PRECOMBAT_MAIN, true);
            put(PhaseStep.BEGIN_COMBAT, true);
            put(PhaseStep.DECLARE_ATTACKERS, true);
            put(PhaseStep.DECLARE_BLOCKERS, true);
            put(PhaseStep.END_COMBAT, true);
            put(PhaseStep.POSTCOMBAT_MAIN, true);
            put(PhaseStep.END_TURN, true);
        }};
    }

    public static LoadPhaseManager getInstance() {
        return instance;
    }

    public boolean isSkip(GameView gameView, String message, UUID playerId) {
        // skip callbacks

        UUID activePlayer = null;
        Map<PhaseStep, Boolean> map = skipOthers;
        for (PlayerView playerView : gameView.getPlayers()) {
            if (playerView.isActive()) {
                activePlayer = playerView.getPlayerId();
                if (activePlayer.equals(playerId)) {
                    map = skipYou;
                }
            }
        }

        if (activePlayer == null) {
            throw new IllegalStateException("No active player found.");
        }

        // PROCCESS

        if(map.containsKey(gameView.getStep())){
            return map.get(gameView.getStep());
        } else {
            log.error("Unknown phase manager step: " + gameView.getStep().toString());
            return false;
        }
    }
}
