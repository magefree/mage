/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.client.util;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import mage.client.MageFrame;
import mage.view.GameView;

public class PhaseManager {

    private static final PhaseManager fInstance = new PhaseManager();

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

    private static final Preferences prefs = MageFrame.getPreferences();

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

    public static PhaseManager getInstance() {
        return fInstance;
    }

    public boolean isSkip(GameView gameView, String message) {
        // no skipping if stack is not empty
        if (GameManager.getInstance().getStackSize() > 0) {
            return false;
        }
        if (gameView.getActivePlayerId() == null) {
            throw new IllegalStateException("No active player found.");
        }
        String prefKey;
        if (gameView.getActivePlayerId().equals(GameManager.getInstance().getCurrentPlayerUUID())) {
            prefKey = mapYou.get(message);
        } else {
            prefKey = mapOthers.get(message);
        }
        if (prefKey != null) {
            String prop = prefs.get(prefKey, PHASE_ON);
            return !prop.equals(PHASE_ON);
        }
        return false;
    }
}
