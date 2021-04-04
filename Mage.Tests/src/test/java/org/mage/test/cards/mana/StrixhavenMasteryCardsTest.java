package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author htrajan
 */
public class StrixhavenMasteryCardsTest extends CardTestPlayerBase {

    @Test
    public void testBalefulMastery() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Baleful Mastery");

        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerB, "Witchbane Orb");

        assertHandCount(playerB, 0);
        assertExileCount(playerB, 0);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", "Goblin Piker");
        setChoice(playerA, "Yes");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerB, 1);
        assertExileCount(playerB, 1);
    }

}
