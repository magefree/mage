package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class CommanderCardScore extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_CommanderColorIdentityManaAbility() {
        // NPE error: https://www.reddit.com/r/XMage/comments/f8yzgg/game_error_with_any_mana_lands/
        addCard(Zone.BATTLEFIELD, playerA, "Command Tower", 1);

        // AI must play one time to run score calc
        // Verify tests checking all cards with broken getMana()
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
