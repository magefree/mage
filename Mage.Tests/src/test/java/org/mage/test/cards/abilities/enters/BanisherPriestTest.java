/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BanisherPriestTest extends CardTestPlayerBase {

   /**
     * If Banisher Priest leaves the battlefield before its enters-the-battlefield
     * ability resolves, the target creature won't be exiled.
     */
    @Test
    public void testDoNotExileIfBanisherPriestLeavesBattlefieldBeforeResolve() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        /**
        * Banisher Pries
        * Creature — Human Cleric 2/2, 1WW
        * When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.
        */
        addCard(Zone.HAND, playerA, "Banisher Priest");
        addCard(Zone.HAND, playerB, "Incinerate");

        /**
         *  Rockslide Elemental
         *  Creature — Elemental 1/1, 2R (3)
         *  First strike.
         *  Whenever another creature dies, you may put a +1/+1 counter on Rockslide Elemental..
         */
        addCard(Zone.BATTLEFIELD, playerB, "Rockslide Elemental");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banisher Priest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Incinerate", "Banisher Priest");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // Rockslide Elemental returned to battlefield
        assertPermanentCount(playerB, "Rockslide Elemental", 1);
        // Banisher Priest should be in graveyard
        assertPermanentCount(playerA, "Banisher Priest", 0);

        // check that returning Rockslide Elemental did get a +1/+1 counter from dying Banisher Priest
        assertPowerToughness(playerB, "Rockslide Elemental", 2, 2);
    }


    /**
     * Check if the returning target did not trigger the die Event of
     * the dying Banisher Priest
     */
    @Test
    public void testReturningTargetDoesNotTriggerDieEventOfBanisherPriest() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        /**
        * Banisher Pries
        * Creature — Human Cleric 2/2, 1WW
        * When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.
        */
        addCard(Zone.HAND, playerA, "Banisher Priest");
        addCard(Zone.HAND, playerB, "Incinerate");

        /**
         *  Rockslide Elemental
         *  Creature — Elemental 1/1, 2R (3)
         *  First strike.
         *  Whenever another creature dies, you may put a +1/+1 counter on Rockslide Elemental..
         */
        addCard(Zone.BATTLEFIELD, playerB, "Rockslide Elemental");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banisher Priest");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Incinerate", "Banisher Priest");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // Rockslide Elemental returned to battlefield
        assertPermanentCount(playerB, "Rockslide Elemental", 1);
        // Banisher Priest should be in graveyard
        assertPermanentCount(playerA, "Banisher Priest", 0);

        // check that returning Rockslide Elemental did not get a +1/+1 counter from dying Banisher Priest
        assertPowerToughness(playerB, "Rockslide Elemental", 1, 1);
    }
}

