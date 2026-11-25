package org.mage.test.cards.single.nec;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Swift Reconfiguration {W} Enchantment — Aura
 * Flash
 * Enchant creature or Vehicle
 * Enchanted permanent is a Vehicle artifact with crew 5 and it loses all other card types.
 * (It's not a creature unless it's crewed.)
 * <p>
 * Losing the creature type also removes the creature subtypes; artifact subtypes stay (CR 205.1a).
 */
public class SwiftReconfigurationTest extends CardTestPlayerBase {

    @Test
    public void test_CreatureLosesCreatureSubtypes() {
        addCard(Zone.HAND, playerA, "Swift Reconfiguration");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swift Reconfiguration", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertType("Grizzly Bears", CardType.ARTIFACT, SubType.VEHICLE);
        assertNotType("Grizzly Bears", CardType.CREATURE);
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_ArtifactCreatureLosesCreatureSubtypes() {
        addCard(Zone.HAND, playerA, "Swift Reconfiguration");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Phyrexian Walker"); // Artifact Creature — Phyrexian Construct

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swift Reconfiguration", "Phyrexian Walker");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertType("Phyrexian Walker", CardType.ARTIFACT, SubType.VEHICLE);
        assertNotType("Phyrexian Walker", CardType.CREATURE);
        assertNotSubtype("Phyrexian Walker", SubType.PHYREXIAN);
        assertNotSubtype("Phyrexian Walker", SubType.CONSTRUCT);
    }
}
