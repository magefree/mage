package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 *
 * @author JayDi85
 */
public class ChooseModalAbilityAITest extends CardTestPlayerBaseWithAIHelps {

    private void setupSingleMode() {
        // Choose one --
        // * Target creature you control deals damage equal to its power to target creature an opponent controls.
        // * Destroy target artifact or enchantment.
        addCard(Zone.HAND, playerA, "It's Clobberin' Time!", 1); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // possible mode 1 target: own creature - negative score
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 1); // 1/1

        // possible mode 1 target: opponent creature, survive, nothing to score
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        // possible mode 2 target: opponent artifact, destroy, gives ~500 score
        addCard(Zone.BATTLEFIELD, playerB, "Manalith", 1);
    }

    @Test
    public void test_SingleModal_Manual() {
        setupSingleMode();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "It's Clobberin' Time!");
        setModeChoice(playerA, "2"); // 2: Destroy target artifact or enchantment
        addTarget(playerA, "Manalith");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "It's Clobberin' Time!", 1);
        assertPermanentCount(playerB, "Manalith", 0);
    }

    @Test
    public void test_SingleModal_AI() {
        setupSingleMode();

        // ai must choose mode 2 due useless mode 1 (1 damage to 2/2 bear is worthless)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "It's Clobberin' Time!", 1); // exile due to Rebound
        assertPermanentCount(playerB, "Manalith", 0);
    }

    private void setupMultiMode() {
        // Fiery Confluence {2}{R}{R} - Choose three. You may choose the same mode more than once.
        // * Fiery Confluence deals 1 damage to each creature.
        // * Fiery Confluence deals 2 damage to each opponent.
        // * Destroy target artifact.
        addCard(Zone.HAND, playerA, "Fiery Confluence", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // no targets on mode 1 = it's useless
        // has targets on mode 2 = so AI must use it
        // no targets on mode 3 = it's useless
    }

    @Test
    public void test_MultiModal_Manual() {
        setupMultiMode();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Confluence");
        // 2: {this} deals 2 damage to each opponent
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Fiery Confluence", 1);
        assertLife(playerB, 20 - 2 * 3);
    }

    @Test
    @Ignore // TODO: add support of You may choose the same mode more than once, see addModeOptions
    public void test_MultiModal_AI() {
        setupMultiMode();

        // ai must choose x3 mode 2 due useless mode 1 and 3 (no targets)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Fiery Confluence", 1);
        assertLife(playerB, 20 - 2 * 3);
    }
}