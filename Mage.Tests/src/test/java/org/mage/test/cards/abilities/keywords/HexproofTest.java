/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HexproofTest extends CardTestPlayerBase {

    /**
     * Tests one target gets hexproof
     */
    @Test
    public void testOneTargetOneGainingHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elder of Laurels");
        addCard(Zone.HAND, playerA, "Ranger's Guile");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Into the Void");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Into the Void", "Elder of Laurels");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ranger's Guile", "Elder of Laurels");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // because of hexproof the Elder should be onto the battlefield
        assertPermanentCount(playerA, "Elder of Laurels", 1);
        assertPowerToughness(playerA, "Elder of Laurels", 3, 4);
        assertAbility(playerA, "Elder of Laurels", HexproofAbility.getInstance(), true);
    }
    /**
     * Tests one target gets hexproof
     */
    @Test
    public void testTwoTargetsOneGainingHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elder of Laurels");
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf");
        addCard(Zone.HAND, playerA, "Ranger's Guile");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Into the Void");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Into the Void", "Elder of Laurels^Arbor Elf");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ranger's Guile", "Elder of Laurels");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // because of hexproof the Elder should be onto the battlefield
        assertPermanentCount(playerA, "Elder of Laurels", 1);
        assertPowerToughness(playerA, "Elder of Laurels", 3, 4);
        assertAbility(playerA, "Elder of Laurels", HexproofAbility.getInstance(), true);
        assertPermanentCount(playerA, "Arbor Elf", 0);
    }
}
