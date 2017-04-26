/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.game.ends;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GameIsADrawTest extends CardTestPlayerBase {

    @Test
    public void GameDrawByDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        // Flame Rift deals 4 damage to each player.
        addCard(Zone.HAND, playerA, "Flame Rift", 3); // Sorcery {1}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        // Flame Rift deals 4 damage to each player.
        addCard(Zone.HAND, playerB, "Flame Rift", 2); // Sorcery

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Rift");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Rift");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Rift");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flame Rift");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flame Rift");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 0);
        assertLife(playerB, 0);

        Assert.assertFalse("Player A has not won.", playerA.hasWon());
        Assert.assertFalse("Player B has not won.", playerB.hasWon());

        Assert.assertTrue("Game has ended.", currentGame.hasEnded());

        Assert.assertTrue("Both players had 0 life, game has be de a draw.", currentGame.isADraw());

    }

    @Test
    public void GameDrawByDivineIntervention() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        // Divine Intervention enters the battlefield with two intervention counters on it.
        // At the beginning of your upkeep, remove an intervention counter from Divine Intervention.
        // When you remove the last intervention counter from Divine Intervention, the game is a draw.
        addCard(Zone.HAND, playerA, "Divine Intervention", 1); // Enchantment {6}{W}{W}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divine Intervention");

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Assert.assertFalse("Player A has not won.", playerA.hasWon());
        Assert.assertFalse("Player B has not won.", playerB.hasWon());

        Assert.assertTrue("Game has ended.", currentGame.hasEnded());

        Assert.assertTrue("Both players had 0 life, game has be de a draw.", currentGame.isADraw());

    }

}
