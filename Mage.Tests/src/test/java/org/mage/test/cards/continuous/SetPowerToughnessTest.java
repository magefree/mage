package org.mage.test.cards.continuous;

import mage.abilities.keyword.FlyingAbility;
import mage.cards.h.HalimarTidecaller;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Check that effects which increase the base PT interact correctly with effects which boost PT.
 * And check that the test functions created to read both base and boosted values work correctly.
 *
 * @author Alex-Vasile
 */
public class SetPowerToughnessTest extends CardTestPlayerBase {
    // {U}
    // Enchanted creature gets +1/+1 and has flying.
    private static final String arcaneFlight = "Arcane Flight";
    // {X}{G/U}{G/U}
    // Creatures you control have base power and toughness X/X until end of turn.
    private static final String biomassMutation = "Biomass Mutation";
    private static final String lion = "Silvercoat Lion";

    /**
     * Test that for a boosted creature, both base and the boosted power and toughness are correctly measured
     */
    @Test
    public void testBoostedVsBasePT() {
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, arcaneFlight);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcaneFlight);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertBasePowerToughness(playerA, lion, 2, 2);
        assertPowerToughness(playerA, lion, 3, 3);
    }

    /**
     * Test that base power effects work properly with +1/+1 and -1/-1 counters
     */
    @Test
    public void testBaseChangeAndCounter() {
        // {B}{B}
        // When Ancestral Vengeance enters the battlefield, put a +1/+1 counter on target creature you control.
        // Enchanted creature gets -1/-1.
        String ancestralVengeance = "Ancestral Vengeance";
        // 4/4
        String airElemental = "Air Elemental";

        addCard(Zone.HAND, playerA, ancestralVengeance);
        addCard(Zone.HAND, playerA, biomassMutation);
        addCard(Zone.BATTLEFIELD, playerA, lion); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, airElemental);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ancestralVengeance);
        addTarget(playerA, airElemental); // decrease from 4/4 to a 3/3
        addTarget(playerA, lion); // Boost from 2/2 to a 3/3

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertBasePowerToughness(playerA, lion, 2, 2);
        assertPowerToughness(playerA, lion, 3, 3); // 2/2 + +1/+1
        assertBasePowerToughness(playerA, airElemental, 4, 4);
        assertPowerToughness(playerA, airElemental, 3, 3); // 3/3 + -1/-1

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, biomassMutation);
        setChoice(playerA, "X=5"); // Everyone should have base P/T of 5/5

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertBasePowerToughness(playerA, lion, 5, 5);
        assertPowerToughness(playerA, lion, 6, 6);
        assertBasePowerToughness(playerA, airElemental, 5, 5);
        assertPowerToughness(playerA, airElemental, 4, 4);
    }

    /**
     * Test that base PT change effects lasts between turns if they're supposed to.
     */
    @Test
    public void testBaseChangeLasts() {
        // {1}{U}
        // Enchanted artifact is a creature with base power and toughness 5/5 in addition to its other types.
        String ensoulArtifact = "Ensoul Artifact";
        String solRing = "Sol Ring";
        // Destroy target artifact, enchantment, or creature with flying.
        String brokenWing = "Broken Wings";

        addCard(Zone.HAND, playerA, ensoulArtifact);
        addCard(Zone.HAND, playerA, arcaneFlight);
        addCard(Zone.HAND, playerA, brokenWing);
        addCard(Zone.BATTLEFIELD, playerA, solRing);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ensoulArtifact);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcaneFlight);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, solRing, 6, 6);
        assertBasePowerToughness(playerA, solRing, 5, 5);
        assertAbility(playerA, solRing, FlyingAbility.getInstance(), true);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // wait two turns and make sure it keeps working
        execute();
        assertPowerToughness(playerA, solRing, 6, 6);
        assertBasePowerToughness(playerA, solRing, 5, 5);
        assertAbility(playerA, solRing, FlyingAbility.getInstance(), true);

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, brokenWing, ensoulArtifact);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Remove Ensoul Artifact, and have Arcane Flight fall off, and check that the Sol Ring goes back to 0/0
        assertPowerToughness(playerA, solRing, 0, 0);
        assertBasePowerToughness(playerA, solRing, 0, 0);
        assertAbility(playerA, solRing, FlyingAbility.getInstance(), false);
    }

    /**
     * Test that base PT change effects are properly reset between turns.
     */
    @Test
    public void testBaseChangePTResets() {
        addCard(Zone.HAND, playerA, biomassMutation);
        addCard(Zone.HAND, playerA, arcaneFlight);

        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcaneFlight);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, biomassMutation);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertBasePowerToughness(playerA, lion, 1, 1);
        assertPowerToughness(playerA, lion, 2, 2);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertBasePowerToughness(playerA, lion, 2, 2);
        assertPowerToughness(playerA, lion, 3, 3);
    }

    /**
     * Test that BecomesCreatureAttachedEffect works properly
     */
    @Test
    public void becomesTokenWorks() {
        // {2}{W}
        // Enchant creature
        // Enchanted creature loses all abilities and is a blue Fish with base power and toughness 0/1.
        String ichthyomorphosis = "Ichthyomorphosis";

        addCard(Zone.HAND, playerA, ichthyomorphosis);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ichthyomorphosis, lion);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertBasePowerToughness(playerA, lion, 0, 1);
        assertPowerToughness(playerA, lion, 0, 1);
    }
}
