/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HarvestMageTest extends CardTestPlayerBase {

    @Test
    public void testOneInstance() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // {G}, {T}, Discard a card: Until end of turn, if you tap a land for mana, it produces one mana of a color of your choice instead of any other type and amount.
        addCard(Zone.HAND, playerA, "Harvest Mage", 1); // Creature 1/1 {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harvest Mage");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}, Discard a card: Until end of turn");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Harvest Mage", 1);
    }
}
