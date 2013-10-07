/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.single.ths;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class HeliodGodOfTheSun extends CardTestPlayerBase {

    /**
     * Tests Heliod get a God with devotion to white >>= 5
     */
    @Test
    public void testHeliodBecomesCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Angel of Jubilation");
        addCard(Zone.HAND, playerA, "Heliod, God of the Sun");
        addCard(Zone.HAND, playerA, "Spear of Heliod");
        addCard(Zone.HAND, playerA, "Hold the Gates");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Jubilation");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Heliod, God of the Sun");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Spear of Heliod");
        castSpell(7, PhaseStep.PRECOMBAT_MAIN, playerA, "Hold the Gates");


        setStopAt(7, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerA, "Angel of Jubilation", 1);
        assertPermanentCount(playerA, "Heliod, God of the Sun", 1);
        assertPermanentCount(playerA, "Spear of Heliod", 1);
        assertPermanentCount(playerA, "Hold the Gates", 1);

        Permanent heliodGodOfTheSun = getPermanent("Heliod, God of the Sun", playerA);
        Assert.assertTrue(heliodGodOfTheSun.getCardType().contains(CardType.CREATURE));
    }

 
}
