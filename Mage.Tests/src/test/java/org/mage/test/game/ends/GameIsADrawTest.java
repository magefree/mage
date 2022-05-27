
package org.mage.test.game.ends;

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

        assertHasNotWonTheGame(playerA);
        assertHasNotWonTheGame(playerB);

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

        assertHasNotWonTheGame(playerA);
        assertHasNotWonTheGame(playerB);

        Assert.assertTrue("Game has ended.", currentGame.hasEnded());

        Assert.assertTrue("Both players had 0 life, game has be de a draw.", currentGame.isADraw());

    }

    /**
     * So here I made a simple infinite loop with Stuffy Doll and Pariah's
     * Shield, which should make the game a draw. But instead, it just keeps
     * going...
     */
    @Test
    public void GameDrawByInfiniteLoop() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // All damage that would be dealt to you is dealt to equipped creature instead.
        // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Pariah's Shield", 1); // Artifact Equipment {5}

        // As Stuffy Doll enters the battlefield, choose a player.
        // Stuffy Doll is indestructible.
        // Whenever Stuffy Doll is dealt damage, it deals that much damage to the chosen player.
        // {T}: Stuffy Doll deals 1 damage to itself.
        addCard(Zone.HAND, playerA, "Stuffy Doll", 1); // Artifact Creature {5} 0/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stuffy Doll");
        setChoice(playerA, "PlayerA");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Stuffy Doll");
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: {this} deals");
        setChoice(playerA, "Yes");
        setChoice(playerB, "Yes");
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Stuffy Doll", 1);
        Permanent shield = getPermanent("Pariah's Shield");
        Assert.assertTrue("Pariah's Shield is attached", shield.getAttachedTo() != null);

        assertHasNotWonTheGame(playerA);
        assertHasNotWonTheGame(playerB);

        Assert.assertTrue("Game has ended.", currentGame.hasEnded());

        Assert.assertTrue("Infinite loop detected, game has be de a draw.", currentGame.isADraw());
    }

    /**
     * Check that a simple triggered ability does not trigger the infinite loop
     * request to players
     */
    @Test
    public void GameDrawByInfiniteLoopNot() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 43);

        // Whenever a creature enters the battlefield under your control, you gain life equal to its toughness.
        addCard(Zone.BATTLEFIELD, playerA, "Angelic Chorus", 1); // Enchantment {5}

        // Create X 4/4 white Angel creature tokens with flying.
        // Miracle (You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.)
        addCard(Zone.HAND, playerA, "Entreat the Angels", 1); // Sorcery {X}{X}{W}{W}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Entreat the Angels");

        setChoice(playerA, "X=20");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Angel Token", 20);
        Assert.assertFalse("Game should not have ended.", currentGame.hasEnded());
        assertLife(playerA, 100);

        Assert.assertFalse("No infinite loop detected, game has be no draw.", currentGame.isADraw());

    }

}
