package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WorldEnchantmentsRuleTest extends CardTestMultiPlayerBase {

    /**
     * 704.5m If two or more permanents have the supertype world, all except the
     * one that has had the world supertype for the shortest amount of time are
     * put into their owners' graveyards. In the event of a tie for the shortest
     * amount of time, all are put into their owners' graveyards. This is called
     * the “world rule.
     *
     */
    @Test
    public void TestTwoWorldEnchantments() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Nether Void", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerD, "Swamp", 7);
        addCard(Zone.HAND, playerD, "Nether Void", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nether Void");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion"); // just needed to get different craete time to second Nether Void
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Nether Void");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nether Void", 0);
        assertPermanentCount(playerD, "Nether Void", 1);
    }

    /**
     * 801.12 The "world rule" applies to a permanent only if other world permanents are within its controller's range of influence.
     */
    @Test
    public void TestTwoWorldEnchantmentsNotInRangeOfInfluence() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Nether Void", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerC, "Swamp", 7);
        addCard(Zone.HAND, playerC, "Nether Void", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nether Void");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion"); // just needed to get different craete time to second Nether Void
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Nether Void");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nether Void", 1);
        assertPermanentCount(playerC, "Nether Void", 1);
    }

    /**
     * 704.5 In the event of a tie for the shortest amount of time, all are put into their owners’ graveyards. This is called the “world rule.”
     * In this example the execution order of the leaves the battlefield triggers of the two Oblivion Rings decide, which World Enchnatment may stay
     * Player order: A -> D -> C -> B
     */
    @Test
    public void TestTwoWorldEnchantmentsFromTriggers() {
        setStrictChooseMode(true);
        // When Oblivion Ring enters the battlefield, exile another target nonland permanent.
        // When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "Oblivion Ring", 1);
        // All creatures have haste.
        addCard(Zone.HAND, playerA, "Concordant Crossroads", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // When Oblivion Ring enters the battlefield, exile another target nonland permanent.
        // When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        addCard(Zone.HAND, playerD, "Oblivion Ring", 1); // Enchantment {2}{W}
        // Destroy all white permanents.
        addCard(Zone.HAND, playerD, "Anarchy", 1); // Sorcery {2}{R}{R}

        // All creatures have haste.
        addCard(Zone.BATTLEFIELD, playerD, "Concordant Crossroads", 1); // World Enchantment {G}
        addCard(Zone.BATTLEFIELD, playerD, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring", true);
        addTarget(playerA, "Concordant Crossroads");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concordant Crossroads", true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Oblivion Ring");
        addTarget(playerD, "Concordant Crossroads");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerD, "Anarchy"); // Both World Enchantments return at the same time and go to grave

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerD, "Concordant Crossroads", 0);
        assertPermanentCount(playerA, "Concordant Crossroads", 1);

        assertGraveyardCount(playerA, "Oblivion Ring", 1);
        assertGraveyardCount(playerA, "Concordant Crossroads", 0);
        assertGraveyardCount(playerD, "Oblivion Ring", 1);
        assertGraveyardCount(playerD, "Concordant Crossroads", 1);
        assertGraveyardCount(playerD, "Anarchy", 1);
    }

    @Test
    public void TestTwoWorldEnchantmentsWithSameOrder() {
        setStrictChooseMode(true);
        // When Charmed Griffin enters the battlefield, each other player may put an artifact or enchantment card onto the battlefield from their hand.
        addCard(Zone.HAND, playerA, "Charmed Griffin", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // All creatures have haste.
        addCard(Zone.HAND, playerD, "Concordant Crossroads", 1);
        addCard(Zone.HAND, playerB, "Concordant Crossroads", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Charmed Griffin");

        setChoice(playerD, true); // Put an artifact or enchantment card from your hand onto the battlefield?
        setChoice(playerD, "Concordant Crossroads");

        setChoice(playerB, true); // Put an artifact or enchantment card from your hand onto the battlefield?
        setChoice(playerB, "Concordant Crossroads");

        concede(1, PhaseStep.PRECOMBAT_MAIN, playerC); // World Enchantments come into range

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Charmed Griffin", 1);
        assertPermanentCount(playerD, "Concordant Crossroads", 0);
        assertPermanentCount(playerB, "Concordant Crossroads", 0);

        assertGraveyardCount(playerB, "Concordant Crossroads", 1);
        assertGraveyardCount(playerD, "Concordant Crossroads", 1);
    }
}
