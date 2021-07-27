package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class OathOfLiegesTest extends CardTestPlayerBase {

    //addCard(Zone.BATTLEFIELD, playerA, "Hypersonic Dragon", 1); // can cast spells at any time
    //addCard(Zone.HAND, playerA, "Breath of Life", 1); // {3}{W} // return creatures
    //addCard(Zone.HAND, playerA, "Replenish", 1); // {3}{W} // return all enchantments
    @Test
    public void testOath_OwnCardTriggersOnOwnTurn() {
        // A
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.LIBRARY, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Oath of Lieges", 1); // {1}{W}
        // B
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        // turn 1 - A
        // oath A triggers for A and activates
        addTarget(playerA, playerB); // who control more lands
        setChoice(playerA, true); // search library
        addTarget(playerA, "Plains"); // card from library

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Plains", 4 + 1);
        assertPermanentCount(playerB, "Island", 5);
    }

    @Test
    public void testOath_OwnCardTriggersOnOpponentTurn() {
        // A
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.LIBRARY, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Hypersonic Dragon", 1); // can cast spells at any time
        addCard(Zone.GRAVEYARD, playerA, "Oath of Lieges", 1); // {1}{W}
        addCard(Zone.HAND, playerA, "Replenish", 1); // {3}{W} // return all enchantments
        // B
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.LIBRARY, playerB, "Plains", 5);

        // turn 1 - A (play oath from grave)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Replenish");
        checkPermanentCount("A have oath", 1, PhaseStep.END_TURN, playerA, "Oath of Lieges", 1);
        checkPermanentCount("A have 5 plains", 1, PhaseStep.END_TURN, playerA, "Plains", 5);
        checkPermanentCount("B have 4 plains", 1, PhaseStep.END_TURN, playerB, "Plains", 4);

        // turn 2 - B
        // oath A triggers for B and activates
        addTarget(playerB, playerA); // who control more lands
        setChoice(playerB, true); // search library
        addTarget(playerB, "Plains"); // card from library

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Plains", 5);
        assertPermanentCount(playerB, "Plains", 4 + 1);
    }

    @Test
    public void testOath_OpponentCardTriggersOnOwnTurn() {
        // A
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.LIBRARY, playerA, "Plains", 5);
        // B
        addCard(Zone.LIBRARY, playerB, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Oath of Lieges", 1); // {1}{W}

        // turn 1 - A
        // oath B triggers for A and activates
        addTarget(playerA, playerB); // who control more lands
        setChoice(playerA, true); // search library
        addTarget(playerA, "Plains"); // card from library

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Plains", 4 + 1);
        assertPermanentCount(playerB, "Plains", 5);
    }

    @Test
    public void testOath_DoubleOath() {
        // A
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.LIBRARY, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Oath of Lieges", 2); // {1}{W}
        // B
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);

        // turn 1 - A
        // oath A triggers for A and activates
        // oath B triggers for A and activates
        // 1
        addTarget(playerA, playerB); // who control more lands
        setChoice(playerA, true); // search library
        addTarget(playerA, "Plains"); // card from library
        // 2
        addTarget(playerA, playerB); // who control more lands
        setChoice(playerA, true); // search library
        addTarget(playerA, "Plains"); // card from library

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Plains", 3 + 2);
        assertPermanentCount(playerB, "Plains", 5);
    }

    @Test
    public void testOath_OwnNormalAndOwnCopy() {
        // A
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3); // for copy
        addCard(Zone.LIBRARY, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Hypersonic Dragon", 1); // can cast spells at any time
        addCard(Zone.GRAVEYARD, playerA, "Oath of Lieges", 1); // {1}{W}
        addCard(Zone.HAND, playerA, "Replenish", 1); // {3}{W} // return all enchantments
        addCard(Zone.HAND, playerA, "Copy Enchantment", 1); // {2}{U} // copy target
        // B
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 12);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.LIBRARY, playerB, "Plains", 5);

        // turn 1 - A
        // cast oath A
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Replenish");
        // cast oath copy
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Copy Enchantment");
        setChoice(playerA, true); // use copy effect
        setChoice(playerA, "Oath of Lieges"); // target for copy
        checkPermanentCount("A have 2 oath", 1, PhaseStep.END_TURN, playerA, "Oath of Lieges", 2);
        checkPermanentCount("A have 10 plains", 1, PhaseStep.END_TURN, playerA, "Plains", 10);
        checkPermanentCount("B have 12 plains", 1, PhaseStep.END_TURN, playerB, "Plains", 12);

        // turn 2 - B
        // oath A triggers for B and do nothing
        // copy oath A triggers for B and do nothing
        setChoice(playerA, "At the beginning of each player's upkeep"); // two triggers on upkeep
        checkPermanentCount("A have 10 plains", 1, PhaseStep.END_TURN, playerA, "Plains", 10);
        checkPermanentCount("B have 12 plains", 1, PhaseStep.END_TURN, playerB, "Plains", 12);

        // turn 3 - A
        // oath A triggers for A and activates
        // copy oath A triggers for A and activates
        setChoice(playerA, "At the beginning of each player's upkeep"); // two triggers on upkeep
        // 1
        addTarget(playerA, playerB); // who control more lands
        setChoice(playerA, true); // search library
        addTarget(playerA, "Plains"); // card from library
        // 2
        addTarget(playerA, playerB); // who control more lands
        setChoice(playerA, true); // search library
        addTarget(playerA, "Plains"); // card from library

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Plains", 10 + 2);
        assertPermanentCount(playerB, "Plains", 12);
    }

    @Test
    public void testOath_OwnNormalAndOpponentCopy() {
        // special test to check targetadjusters (copy card must used target adjuster from original card, not from copied)

        // A
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, "Oath of Lieges", 1); // {1}{W}
        addCard(Zone.HAND, playerA, "Replenish", 1); // {3}{W} // return all enchantments
        addCard(Zone.BATTLEFIELD, playerA, "Hypersonic Dragon", 1); // can cast spells at any time
        // B
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 12);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3); // for copy
        addCard(Zone.LIBRARY, playerB, "Plains", 5);
        addCard(Zone.HAND, playerB, "Copy Enchantment", 1); // {2}{U} // copy target

        // turn 1 - A
        // nothing
        // turn 2 - B
        // cast oath A
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Replenish");
        // cast oath copy by opponent
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Copy Enchantment");
        setChoice(playerB, true); // use copy effect
        setChoice(playerB, "Oath of Lieges"); // target for copy
        checkPermanentCount("A have 1 oath", 2, PhaseStep.END_TURN, playerA, "Oath of Lieges", 1);
        checkPermanentCount("B have 1 oath", 2, PhaseStep.END_TURN, playerA, "Oath of Lieges", 1);
        checkPermanentCount("A have 10 plains", 2, PhaseStep.END_TURN, playerA, "Plains", 10);
        checkPermanentCount("B have 12 plains", 2, PhaseStep.END_TURN, playerB, "Plains", 12);

        // turn 3 - A
        // oath A triggers for A and activates
        // copy oath B triggers for A and activates
        // 1
        addTarget(playerA, playerB); // who control more lands
        setChoice(playerA, true); // search library
        addTarget(playerA, "Plains"); // card from library
        // 2
        addTarget(playerA, playerB); // who control more lands
        setChoice(playerA, true); // search library
        addTarget(playerA, "Plains"); // card from library

        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Plains", 10 + 2);
        assertPermanentCount(playerB, "Plains", 12);
    }
}
