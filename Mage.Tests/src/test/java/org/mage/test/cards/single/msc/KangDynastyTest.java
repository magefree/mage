package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class KangDynastyTest extends CardTestPlayerBase {

    // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
    // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
    // III -- Target creature you control gets +1/+1 until end of turn for each card in your hand and can't be blocked this turn.
    private static final String kangDynasty = "Kang Dynasty";

    private static final String bears = "Grizzly Bears"; // 2/2
    private static final String lion = "Silvercoat Lion"; // 2/2

    @Test
    public void test_ChapterI_TapGoadAndDrawOnCombatDamage() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, kangDynasty);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kangDynasty);
        addTarget(playerA, bears);

        checkPermanentTapped("tapped by chapter I", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, bears, true, 1);

        // turn 2: goaded Grizzly Bears attacks playerA on its own

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue("Grizzly Bears should be goaded by playerA",
                getPermanent(bears, playerB).getGoadingPlayers().contains(playerA.getId()));
        assertLife(playerA, 20 - 2);
        assertHandCount(playerA, 1); // drawn by the delayed trigger
    }

    @Test
    public void test_ChapterI_TriggerEndsOnYourNextTurn() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, kangDynasty);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kangDynasty);
        addTarget(playerA, bears); // chapter I

        // turn 2: goaded Grizzly Bears attacks playerA on its own

        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter II, no new creatures

        // turn 4: no longer goaded, attacks by choice
        attack(4, playerB, bears, playerA);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        Assert.assertFalse("Grizzly Bears should no longer be goaded",
                getPermanent(bears, playerB).getGoadingPlayers().contains(playerA.getId()));
        assertLife(playerA, 20 - 2 - 2);
        assertHandCount(playerA, 2); // turn 2 delayed trigger + turn 3 draw step, nothing on turn 4
    }

    @Test
    public void test_ChapterIII_BoostLockedInAndCantBeBlocked() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, kangDynasty);
        addCard(Zone.HAND, playerA, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kangDynasty);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter I
        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter II
        addTarget(playerA, lion); // chapter III

        // three cards in hand as chapter III resolves: Ornithopter + turn 3 and turn 5 draws
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter");

        attack(5, playerA, lion, playerB);
        block(5, playerB, bears, lion);

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ornithopter", 1);
        assertPowerToughness(playerA, lion, 2 + 3, 2 + 3); // not lowered by casting Ornithopter
        assertLife(playerB, 20 - 5);
        assertPermanentCount(playerB, bears, 1);
        assertGraveyardCount(playerA, kangDynasty, 1);
    }
}
