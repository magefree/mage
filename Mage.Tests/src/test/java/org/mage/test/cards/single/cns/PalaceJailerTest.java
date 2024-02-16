package org.mage.test.cards.single.cns;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PalaceJailerTest extends CardTestPlayerBase {

    @Test
    public void test_PalaceJailer1() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // When Palace Jailer enters the battlefield, you become the monarch.
        // When Palace Jailer enters the battlefield, exile target creature an opponent controls until an opponent becomes the monarch. (That creature returns under its owner's control.)
        addCard(Zone.HAND, playerA, "Palace Jailer"); // Creature {2}{W}{W} (2/2)

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Palace Jailer");
        setChoice(playerA, "When {this} enters the battlefield, you become the monarch.");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Palace Jailer", 1);
        assertExileCount(playerB, "Silvercoat Lion", 1);

        Assert.assertTrue("Player A has to be the monarch", currentGame.getMonarchId().equals(playerA.getId()));
    }

    /**
     * TheGibber on reddit
     *
     * Palace Jailer
     *
     * When exile effect is on more than one target from the same palace jailer
     * card, and you lose monarch, only the most recent card is returned to play
     * instead of all of them
     */
    @Test
    public void test_PalaceJailer2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // When Palace Jailer enters the battlefield, you become the monarch.
        // When Palace Jailer enters the battlefield, exile target creature an opponent controls until an opponent becomes the monarch. (That creature returns under its owner's control.)
        addCard(Zone.HAND, playerA, "Palace Jailer"); // Creature {2}{W}{W} (2/2)
        addCard(Zone.HAND, playerA, "Cloudshift"); // Instant {W}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.HAND, playerB, "Palace Jailer"); // Creature {2}{W}{W} (2/2)
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Palace Jailer");
        setChoice(playerA, "When {this} enters the battlefield, you become the monarch.");
        addTarget(playerA, "Silvercoat Lion");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Palace Jailer");
        setChoice(playerA, "When {this} enters the battlefield, you become the monarch.");
        addTarget(playerA, "Pillarfield Ox");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Palace Jailer");
        setChoice(playerB, "When {this} enters the battlefield, you become the monarch.");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Palace Jailer", 1);

        assertPermanentCount(playerB, "Palace Jailer", 1);
        assertExileCount(playerA, "Silvercoat Lion", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 1);

        Assert.assertTrue("Player B has to be the monarch", currentGame.getMonarchId().equals(playerB.getId()));
    }

}
