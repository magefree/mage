package org.mage.test.cards.single.mh1;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PlagueEngineerTest extends CardTestPlayerBase {

    @Test
    public void test_Standard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Deathtouch
        // As Plague Engineer enters the battlefield, choose a creature type.
        // Creatures of the chosen type your opponents control get -1/-1.        
        addCard(Zone.HAND, playerA, "Plague Engineer"); // Creature {2}{B}  (2/2)

        addCard(Zone.BATTLEFIELD, playerA, "Defiant Elf", 2); //Creature - Elf (1/1)
        addCard(Zone.BATTLEFIELD, playerB, "Defiant Elf", 2); //Creature - Elf (1/1)

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plague Engineer");
        setChoice(playerA, "Elf");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Plague Engineer", 1);
        
        assertPermanentCount(playerA, "Defiant Elf", 2);
        assertGraveyardCount(playerB, "Defiant Elf", 2);

        assertLife(playerA, 20);
        assertLife(playerB, 20);        
    }
}