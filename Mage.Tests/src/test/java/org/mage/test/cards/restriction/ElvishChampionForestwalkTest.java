

package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

/*
 *  Elvish Champion
 *  Creature — Elf 2/2, 1GG
 *  Other Elf creatures get +1/+1 and have forestwalk. (They can't be blocked as long as defending player controls a Forest.)
 */

public class ElvishChampionForestwalkTest extends CardTestPlayerBase {

    /**
     * Tests "If all other elves get the Forestwalk ability and can't be blockt from creatures whose controller has a forest in game"
     */

    @Test
    public void testCannotBlockCreatureWithForestwalk() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Elvish Champion");
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerA, "Defiant Elf");

        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Canyon Minotaur");

        attack(3, playerA, "Arbor Elf");
        attack(3, playerA, "Elvish Champion");
        block(3, playerB, "Silvercoat Lion", "Arbor Elf");
        block(3, playerB, "Canyon Minotaur", "Elvish Champion");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals("Arbor Elf is blocked incorrectly.", e.getMessage());
        }

        //assertPermanentCount(playerA, "Arbor Elf", 1);
        //assertPermanentCount(playerA, "Defiant Elf", 1);

        //assertLife(playerA, 20);
        //assertLife(playerB, 16);
    }

}
