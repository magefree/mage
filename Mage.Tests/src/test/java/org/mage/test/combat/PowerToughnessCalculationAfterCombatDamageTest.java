/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Test that calculated p/t is applied after combat damage resolution, so a 2/2 Nighthowler
 * dies if blocked from a 2/2 creature.
 *
 * @author LevelX2
 */
public class PowerToughnessCalculationAfterCombatDamageTest extends CardTestPlayerBase {

    @Test
    public void powerToughnessCalculation() {
        addCard(Zone.BATTLEFIELD, playerA, "Pain Seer");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        // Nighthowler and enchanted creature each get +X/+X, where X is the number of creature cards in all graveyards.
        addCard(Zone.BATTLEFIELD, playerB, "Nighthowler");
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Nighthowler");
        block(2, playerA, "Pain Seer", "Nighthowler");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Nighthowler", 1);
        assertGraveyardCount(playerA, "Pain Seer", 1);
    }
}
