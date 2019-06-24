package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class EmbodimentOfAgoniesTest extends CardTestPlayerBase {

    @Test
    public void testHybridAndPhyrexian() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Embodiment of Agonies");
        addCard(Zone.GRAVEYARD, playerA, "Rakdos Cackler"); // Mana Cost: {B/R}
        addCard(Zone.GRAVEYARD, playerA, "Surgical Extraction"); // Mana Cost: {B/P}
        addCard(Zone.GRAVEYARD, playerA, "Tormented Soul"); // Mana Cost: {B}
        addCard(Zone.GRAVEYARD, playerA, "Gut Shot"); // Mana Cost: {R/P}
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt"); // Mana Cost: {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Embodiment of Agonies");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Creature should be 5/5 as there are 5 distinct mana costs in graveyard
        assertPowerToughness(playerA, "Embodiment of Agonies", 5, 5);
    }

    @Test
    public void testSplitCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Embodiment of Agonies");
        addCard(Zone.GRAVEYARD, playerA, "Turn // Burn"); // Mana Cost: {3}{U}{R}
        addCard(Zone.GRAVEYARD, playerA, "Prophetic Bolt"); // Mana Cost: {3}{U}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Embodiment of Agonies");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Creature should be 1/1 as there 1 distinct mana cost in graveyard
        assertPowerToughness(playerA, "Embodiment of Agonies", 1, 1);
    }

    @Test
    public void testSplitCards2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Embodiment of Agonies");
        addCard(Zone.GRAVEYARD, playerA, "Turn // Burn"); // Mana Cost: {3}{U}{R}
        addCard(Zone.GRAVEYARD, playerA, "Divination"); // Mana Cost: {2}{U}
        addCard(Zone.GRAVEYARD, playerA, "Magma Jet"); // Mana Cost: {1}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Embodiment of Agonies");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Creature should be 3/3 as there are 3 distinct mana costs in graveyard
        assertPowerToughness(playerA, "Embodiment of Agonies", 3, 3);
    }

    @Test
    public void testEmptyManaCosts() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Embodiment of Agonies");
        addCard(Zone.GRAVEYARD, playerA, "Ancestral Vision"); // No Mana Cost

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Embodiment of Agonies");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Creature should be dead as there are no mana costs in graveyards
        assertGraveyardCount(playerA, "Embodiment of Agonies", 1);
    }

    @Test
    public void testXCosts() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Embodiment of Agonies");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt"); // Mana Cost: {R}
        addCard(Zone.GRAVEYARD, playerA, "Fireball"); // Mana Cost: {X}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Embodiment of Agonies");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Creature should be 2/2 as there are 2 distinct mana costs in graveyard
        assertPowerToughness(playerA, "Embodiment of Agonies", 2, 2);
    }
}
