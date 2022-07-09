package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class TidehollowScullerTest extends CardTestPlayerBase {

    /**
     * Test if the same Tidehollow Sculler is cast multiple times, the correct
     * corresponding exiled cards are returned
     */
    @Test
    public void test_CastOneCardFromHandWillBeExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Tidehollow Sculler {W}{B}
        // When Tidehollow Sculler enters the battlefield, target opponent reveals their hand and you choose a nonland card from it. Exile that card.
        // When Tidehollow Sculler leaves the battlefield, return the exiled card to its owner's hand.
        addCard(Zone.HAND, playerA, "Tidehollow Sculler", 1); // 2/2
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // {R}
        //
        addCard(Zone.HAND, playerB, "Bloodflow Connoisseur", 1);

        // cast and exile from hand
        checkHandCardCount("B hand must have blood", 1, PhaseStep.UPKEEP, playerB, "Bloodflow Connoisseur", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tidehollow Sculler");
        // addTarget(playerA, playerB); // choose opponent (Autochosen, only target)
        setChoice(playerA, "Bloodflow Connoisseur"); // card to exile
        checkHandCardCount("B hand must lost blood", 1, PhaseStep.BEGIN_COMBAT, playerB, "Bloodflow Connoisseur", 0);

        // destroy and return card to hand
        checkPermanentCount("A must have tide", 1, PhaseStep.END_COMBAT, playerA, "Tidehollow Sculler", 1);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Tidehollow Sculler");
        checkPermanentCount("A must lost tide", 1, PhaseStep.END_TURN, playerA, "Tidehollow Sculler", 0);
        checkHandCardCount("B must return blood", 1, PhaseStep.END_TURN, playerB, "Bloodflow Connoisseur", 1);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerB, "Bloodflow Connoisseur", 1);
        assertPermanentCount(playerA, "Tidehollow Sculler", 0);
    }

    @Test
    public void test_CastTwoCardFromHandWillBeExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Tidehollow Sculler {W}{B}
        // When Tidehollow Sculler enters the battlefield, target opponent reveals their hand and you choose a nonland card from it. Exile that card.
        // When Tidehollow Sculler leaves the battlefield, return the exiled card to its owner's hand.
        addCard(Zone.HAND, playerA, "Tidehollow Sculler@tide", 2); // 2/2
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2); // {R}
        //
        addCard(Zone.HAND, playerB, "Bloodflow Connoisseur", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        // turn 1 - A
        // cast 1 and exile from hand
        checkHandCardCount("B hand must have blood", 1, PhaseStep.UPKEEP, playerB, "Bloodflow Connoisseur", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tidehollow Sculler");
        // addTarget(playerA, playerB); // choose opponent (Autochosen, only target)
        setChoice(playerA, "Bloodflow Connoisseur"); // card to exile
        checkHandCardCount("B hand must lost blood", 1, PhaseStep.BEGIN_COMBAT, playerB, "Bloodflow Connoisseur", 0);
        // cast 2 and exile from hand
        checkHandCardCount("B hand must have lion", 1, PhaseStep.END_COMBAT, playerB, "Silvercoat Lion", 1);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tidehollow Sculler");
        // addTarget(playerA, playerB); // choose opponent (Autochosen, only target)
        setChoice(playerA, "Silvercoat Lion"); // card to exile
        checkHandCardCount("B hand must lost lion", 1, PhaseStep.END_TURN, playerB, "Silvercoat Lion", 0);

        // turn 2 - B
        // destroy 1 and return card to hand
        checkPermanentCount("A must have 2 tide", 2, PhaseStep.UPKEEP, playerA, "Tidehollow Sculler", 2);
        checkHandCardCount("B hand must have 0 blood", 2, PhaseStep.UPKEEP, playerB, "Bloodflow Connoisseur", 0);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@tide.1");
//        showHand("B hand", 2, PhaseStep.BEGIN_COMBAT, playerB);
        checkPermanentCount("A must have 1 tide", 2, PhaseStep.BEGIN_COMBAT, playerA, "Tidehollow Sculler", 1);
        checkHandCardCount("B hand must have 1 blood", 2, PhaseStep.BEGIN_COMBAT, playerB, "Bloodflow Connoisseur", 1);
        // destroy 2 and return card to hand
        checkPermanentCount("A must have 1 tide", 2, PhaseStep.END_COMBAT, playerA, "Tidehollow Sculler", 1);
        checkHandCardCount("B hand must have 0 lion", 2, PhaseStep.END_COMBAT, playerB, "Silvercoat Lion", 0);
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "@tide.2");
        checkPermanentCount("A must have 0 tide", 2, PhaseStep.END_TURN, playerA, "Tidehollow Sculler", 0);
        checkHandCardCount("B hand must have 1 lion", 2, PhaseStep.END_TURN, playerB, "Silvercoat Lion", 1);

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_MultipleRuns() {
        // test random selection by AI (must use direct select by alias, not AI)
        for (int i = 1; i <= 10; i++) {
            try {
                this.reset();
                // System.out.println("run " + i);
                test_CastTwoCardFromHandWillBeExiled();
            } catch (Exception e) {
                //
            }
        }
    }

}
