package org.mage.test.cards.single.jud;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SpoilsOfWar}
 *
 * Spoils of War {X}{B}
 * Sorcery
 * X is the number of artifact and/or creature cards in an opponent's graveyard as you cast this spell.
 * Distribute X +1/+1 counters among any number of target creatures.
 *
 * @author Vernon
 */
public class SpoilsOfWarTest extends CardTestPlayerBase {

    /**
     * Test: Verify Spoils of War card exists and can be cast
     */
    @Test
    public void testCardExists() {
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Spoils of War");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // Just verify the game state is valid
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
    }
}





