/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class KardurDoomscourgeAndKithkinMourncallerTest extends CardTestPlayerBase {

    @Test
    public void testKDRemovedFromCombatViaRegenerateAbility() {
        setStrictChooseMode(true);
        // Kardur, Doomscourge: if an attacking creature dies, each opponent loses 1 life and you gain 1 life
        addCard(Zone.BATTLEFIELD, playerA, "Kardur, Doomscourge");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Archers");
        addCard(Zone.BATTLEFIELD, playerB, "Serra Angel");
        addCard(Zone.HAND, playerA, "Regenerate");
        addCard(Zone.HAND, playerA, "Terror");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Regenerate", "Elvish Archers");

        attack(1, playerA, "Elvish Archers");
        block(1, playerB, "Serra Angel", "Elvish Archers");  // regeneration shield used up and EA is removed from combat

        castSpell(1, PhaseStep.END_COMBAT, playerA, "Terror", "Elvish Archers");  // still within the combat phase, the EA is destroyed/dies

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Elvish Archers", 1);

        // does not fire due to the Elvish Archers not in an attacking state
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void testSuccessfulKDTrigger() {
        setStrictChooseMode(true);
        // Kardur, Doomscourge: if an attacking creature dies, each opponent loses 1 life and you gain 1 life
        addCard(Zone.BATTLEFIELD, playerA, "Kardur, Doomscourge");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Archers"); // 2/2 first strike
        addCard(Zone.BATTLEFIELD, playerB, "Serra Angel");  // 4/4 vigilance

        attack(1, playerA, "Elvish Archers");
        block(1, playerB, "Serra Angel", "Elvish Archers");  // Elvish Archer dies causing KD to trigger

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Elvish Archers", 1);

        // successful fire so playerA gains 1 life and playerB loses 1 life
        assertLife(playerA, 21);
        assertLife(playerB, 19);

    }

    @Test
    public void testKMTrigger() {
        setStrictChooseMode(true);
        // Kithkin Mourncaller: if an elf or kithkin dies, you may draw a card
        addCard(Zone.BATTLEFIELD, playerA, "Kithkin Mourncaller");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Archers");  // 2/1 first strike
        addCard(Zone.BATTLEFIELD, playerA, "Pearled Unicorn"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Serra Angel"); // 4/4 vigilance
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear");  // 2/2 
        addCard(Zone.LIBRARY, playerA, "Island", 2);  // used for draw trigger

        attack(1, playerA, "Elvish Archers");
        attack(1, playerA, "Pearled Unicorn");
        block(1, playerB, "Serra Angel", "Elvish Archers");  // Elvish Achers will die and trigger KM
        block(1, playerB, "Runeclaw Bear", "Pearled Unicorn");  // Pearled Unicorn will die but not trigger KM

        setChoice(playerA, "Yes");  // accept the drawing of a card from the single trigger (Elvish Archers "elf type")
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Elvish Archers", 1);
        assertGraveyardCount(playerA, "Pearled Unicorn", 1);

        // successful fire due to dead Elvish Archers (elf) so playerA draws a card
        assertHandCount(playerA, 1);

    }

}
