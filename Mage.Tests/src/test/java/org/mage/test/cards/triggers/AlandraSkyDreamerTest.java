/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlandraSkyDreamerTest extends CardTestPlayerBase {

    @Test
    public void testDraw2() {
        addCard(Zone.HAND, playerA, "Inspiration");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Alandra, Sky Dreamer", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inspiration", playerA);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 2);
        assertPermanentCount(playerA, "Drake Token", 1);
        assertPowerToughness(playerA, "Drake Token", 2, 2);
        assertPowerToughness(playerA, "Alandra, Sky Dreamer", 2, 4);
    }
    
    @Test
    public void testDraw5() {
        addCard(Zone.HAND, playerA, "Inspiration");
        addCard(Zone.HAND, playerA, "Ancestral Recall");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Alandra, Sky Dreamer", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inspiration", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Recall", playerA);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        int drawnCards = 5;
        assertHandCount(playerA, drawnCards);
        assertPermanentCount(playerA, "Drake Token", 1);
        assertPowerToughness(playerA, "Drake Token", 2 + drawnCards, 2 + drawnCards);
        assertPowerToughness(playerA, "Alandra, Sky Dreamer", 2 + drawnCards, 4 + drawnCards);
    }
}
