package org.mage.test.cards.single.mmq;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */

public class MisdirectionTest extends CardTestPlayerBase {

    /**
     * Tests if Misdirection for target opponent works correctly
     * https://github.com/magefree/mage/issues/574
     */

    @Test
    public void test_RakshaDiscardWorks() {
        // Target opponent discards two cards. Put the top two cards of your library into your graveyard.
        addCard(Zone.HAND, playerA, "Rakshasa's Secret"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);
        addCard(Zone.HAND, playerB, "Ashcoat Bear", 5);

        // A cast discard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rakshasa's Secret", playerB);
        setChoice(playerB, "Silvercoat Lion"); // select target 1
        setChoice(playerB, "Silvercoat Lion"); // select target 2
        checkHandCardCount("B haven't lions", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Silvercoat Lion", 0);
        checkHandCardCount("B have 5 bears", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 5);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_MisdirectionRetargetWorks() {
        // Return target permanent to its owner’s hand.
        addCard(Zone.HAND, playerA, "Boomerang", 1); // {U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Ashcoat Bear", 1);
        // Change the target of target spell with a single target.
        addCard(Zone.HAND, playerB, "Misdirection"); // {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // A cast Boomerang to remove lion
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boomerang", "Silvercoat Lion");
        // B counter it by Misdirection and remove bear
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Misdirection", "Boomerang", "Boomerang");
        addTarget(playerB, "Ashcoat Bear");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Boomerang", 1);
        assertPermanentCount(playerA, "Ashcoat Bear", 0);
        assertGraveyardCount(playerB, "Misdirection", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_MisdirectionCantTargetToIllegal() {
        // Target opponent discards two cards. Put the top two cards of your library into your graveyard.
        addCard(Zone.HAND, playerA, "Rakshasa's Secret"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        /*    
        Misdirection {3}{U}{U}
        Instant
        You may exile a blue card from your hand rather than pay Misdirection's mana cost.
        Change the target of target spell with a single target.
        */
        addCard(Zone.HAND, playerB, "Misdirection");
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);
        addCard(Zone.HAND, playerB, "Ashcoat Bear", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        // cast Raksha and select B
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rakshasa's Secret", playerB);
        // cast misdir, but it's not apply and taget will be same
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Misdirection", "Rakshasa's Secret", "Rakshasa's Secret");
        addTarget(playerB, playerB); // new target for rakhas will be same B
        // B must select cards to discard (2 lions, not bears)
        setChoice(playerB, "Silvercoat Lion"); // select target 1
        setChoice(playerB, "Silvercoat Lion"); // select target 2
        checkHandCardCount("B haven't lions", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Silvercoat Lion", 0);
        checkHandCardCount("B have 5 bears", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 5);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Rakshasa's Secret", 1);
        assertGraveyardCount(playerB, "Misdirection", 1);
        assertHandCount(playerB, "Silvercoat Lion", 0);
    }

    // check to change target permanent creature legal to to a creature the opponent of the spell controller controls
    // target to illegal target can't be tested
    @Test
    public void test_ChangePublicExecution() {
        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        /*    
        Misdirection {3}{U}{U}
        Instant
        You may exile a blue card from your hand rather than pay Misdirection's mana cost.
        Change the target of target spell with a single target.
        */
        addCard(Zone.HAND, playerB, "Misdirection");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Custodian of the Trove", 1); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Public Execution", "Pillarfield Ox");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Misdirection", "Public Execution", "Public Execution");
        addTarget(playerB, "Custodian of the Trove");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Public Execution", 1);
        assertGraveyardCount(playerB, "Misdirection", 1);

        assertGraveyardCount(playerB, "Custodian of the Trove", 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 4);
    }
}