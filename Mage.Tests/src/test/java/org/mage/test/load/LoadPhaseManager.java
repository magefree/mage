package org.mage.test.load;

import mage.view.GameView;
import mage.view.PlayerView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoadPhaseManager {

    private static final LoadPhaseManager fInstance = new LoadPhaseManager();

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

    private static Map<String, String> mapYou = new HashMap<String, String>() {{
        put("Upkeep - play instants and activated abilities.", UPKEEP_YOU);
        put("Draw - play instants and activated abilities.", DRAW_YOU);
        put("Precombat Main - play spells and abilities.", MAIN_YOU);
        put("Begin Combat - play instants and activated abilities.", BEFORE_COMBAT_YOU);
        put("End Combat - play instants and activated abilities.", END_OF_COMBAT_YOU);
        put("Postcombat Main - play spells and abilities.", MAIN_2_YOU);
        put("End Turn - play instants and activated abilities.", END_OF_TURN_YOU);
    }};

    private static Map<String, String> mapOthers = new HashMap<String, String>() {{
        put("Upkeep - play instants and activated abilities.", UPKEEP_OTHERS);
        put("Draw - play instants and activated abilities.", DRAW_OTHERS);
        put("Precombat Main - play instants and activated abilities.", MAIN_OTHERS);
        put("Begin Combat - play instants and activated abilities.", BEFORE_COMBAT_OTHERS);
        put("End Combat - play instants and activated abilities.", END_OF_COMBAT_OTHERS);
        put("Postcombat Main - play instants and activated abilities.", MAIN_2_OTHERS);
        put("End Turn - play instants and activated abilities.", END_OF_TURN_OTHERS);
    }};

    public static LoadPhaseManager getInstance() {
        return fInstance;
    }

    public boolean isSkip(GameView gameView, String message, UUID playerId) {
        UUID activePlayer = null;
        Map<String, String> map = mapOthers;
        for (PlayerView playerView : gameView.getPlayers()) {
            if (playerView.isActive()) {
                activePlayer = playerView.getPlayerId();
                if (activePlayer.equals(playerId)) {
                    map = mapYou;
                }
            }
        }
        if (activePlayer == null) {
            throw new IllegalStateException("No active player found.");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (message.equals(entry.getKey())) {
                /*if (message.equals("Precombat Main - play spells and abilities.")) {
                    return false;
                }*/
                return true;
            }
        }
         return false;
    }
}
