package org.mage.test.cards.single.bro;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SpotterThopterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SpotterThopter Spotter Thopter} {8}
     * Artifact Creature — Thopter
     * Prototype {3}{U} — 2/3 (You may cast this spell with different mana cost, color, and size. It keeps its abilities and types.)
     * Flying
     * When this creature enters, scry X, where X is its power.
     * 4/5
     */
    private static final String thopter = "Spotter Thopter";

    @Test
    public void test_prototype_scry2() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancient Amphitheater");
        addCard(Zone.LIBRARY, playerA, "Baleful Strix");
        addCard(Zone.LIBRARY, playerA, "Crucible of Worlds");
        addCard(Zone.LIBRARY, playerA, "Desecration Demon");

        addCard(Zone.HAND, playerA, thopter, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, thopter + " using Prototype");

        // Oath of Jace's upkeep trigger triggers
        addTarget(playerA, "Desecration Demon"); // put on bottom with scry 2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_normal_scry4() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancient Amphitheater");
        addCard(Zone.LIBRARY, playerA, "Baleful Strix");
        addCard(Zone.LIBRARY, playerA, "Crucible of Worlds");
        addCard(Zone.LIBRARY, playerA, "Desecration Demon");

        addCard(Zone.HAND, playerA, thopter, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, thopter);

        // Oath of Jace's upkeep trigger triggers
        addTarget(playerA, "Desecration Demon^Crucible of Worlds"); // put on bottom with scry 2
        setChoice(playerA, "Crucible of Worlds"); // order for bottom
        setChoice(playerA, "Baleful Strix"); // order for top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
