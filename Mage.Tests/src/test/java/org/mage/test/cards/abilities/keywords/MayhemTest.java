package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class MayhemTest extends CardTestPlayerBase {

    private static final String islanders = "Spider-Islanders";

    @Test
    public void testCastRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, islanders);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, islanders);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, islanders, 1);
    }

    private static final String imp = "Putrid Imp";

    @Test
    public void testCastDiscarded() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, imp);
        addCard(Zone.HAND, playerA, islanders);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, islanders);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, islanders + " with Mayhem");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, islanders, 1);
    }

    @Test
    public void testCantCastGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.GRAVEYARD, playerA, islanders);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, islanders + " with Mayhem");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        try {
            execute();
        } catch (Throwable e) {
            Assert.assertEquals(
                    "Should fail to be able to cast " + islanders + " with mayhem as it wasn't discarded this turn",
                    "Can't find ability to activate command: Cast " + islanders + " with Mayhem", e.getMessage()
            );
        }
    }

    @Test
    public void testCantCastDiscardedPreviously() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, imp);
        addCard(Zone.HAND, playerA, islanders);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, islanders);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, islanders + " with Mayhem");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        try {
            execute();
        } catch (Throwable e) {
            Assert.assertEquals(
                    "Should fail to be able to cast " + islanders + " with mayhem as it wasn't discarded this turn",
                    "Can't find ability to activate command: Cast " + islanders + " with Mayhem", e.getMessage()
            );
        }
    }

    // https://github.com/magefree/mage/issues/14107
    // 702.35c
    // After resolving a madness triggered ability, if the exiled card wasn’t cast and was moved to a public zone,
    // effects referencing the discarded card can find that card. See rule 400.7k.
    @Test
    public void testCastDiscardedWithMadnessDeclined() {
        // Green Goblin, back face of Norman Osborn
        // Flying, menace
        // Spells you cast from your graveyard cost {2} less to cast.
        // Goblin Formula -- Each nonland card in your graveyard has mayhem. The mayhem cost is equal to its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Green Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // gets mayhem from Goblin Formula before the discard
        //
        // Discard a card: Putrid Imp gains flying until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Imp");
        //
        // Fiery Temper deals 3 damage to any target.
        // Madness {R}
        addCard(Zone.HAND, playerA, "Fiery Temper"); // {1}{R}{R}, mayhem for {R}{R} after the {2} reduction

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, "Fiery Temper"); // card to discard
        setChoice(playerA, false); // don't cast with madness, so it goes to the graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("discarded", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Temper", 1);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fiery Temper with Mayhem", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, "Fiery Temper", 1);
    }

    @Test
    public void testCantCastDiscardedWithMadnessCast() {
        // Green Goblin, back face of Norman Osborn
        // Flying, menace
        // Spells you cast from your graveyard cost {2} less to cast.
        // Goblin Formula -- Each nonland card in your graveyard has mayhem. The mayhem cost is equal to its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Green Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1 + 2);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // gets mayhem from Goblin Formula before the discard
        //
        // Discard a card: Putrid Imp gains flying until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Imp");
        //
        // Fiery Temper deals 3 damage to any target.
        // Madness {R}
        addCard(Zone.HAND, playerA, "Fiery Temper"); // {1}{R}{R}, mayhem for {R}{R} after the {2} reduction

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, "Fiery Temper"); // card to discard
        setChoice(playerA, true); // cast with madness
        addTarget(playerA, playerB); // Fiery Temper's target
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("resolved", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Temper", 1);

        // it was cast, so the card in the graveyard is a new object and wasn't discarded this turn
        checkPlayableAbility("no mayhem", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Fiery Temper with Mayhem", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
    }
}
