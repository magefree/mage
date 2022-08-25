package org.mage.test.cards.continuous;

import mage.cards.h.HalimarTidecaller;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SetPowerToughnessTest extends CardTestPlayerBase {
    // {U}
    // Enchanted creature gets +1/+1 and has flying.
    private static final String arcaneFlight = "Arcane Flight";
    // {X}{G/U}{G/U}
    // Creatures you control have base power and toughness X/X until end of turn.
    private static final String biomassMutation = "Biomass Mutation";
    private static final String lion = "Silvercoat Lion";

    // TODO: https://scryfall.com/search?q=o%3A%22base+power%22
    //       https://scryfall.com/search?q=o%3A%22base+toughness%22

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
        assertPowerToughness(playerA, lion, 3, 3);
        assertBasePowerToughness(playerA, airElemental, 4, 4);
        assertPowerToughness(playerA, airElemental, 3, 3);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, biomassMutation);
        setChoice(playerA, "X=5");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertBasePowerToughness(playerA, lion, 5, 5);
        assertPowerToughness(playerA, lion, 6, 6);
        assertBasePowerToughness(playerA, airElemental, 5, 5);
        assertPowerToughness(playerA, airElemental, 6, 6);
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

        addCard(Zone.HAND, playerA, ensoulArtifact);
        addCard(Zone.HAND, playerA, arcaneFlight);
        addCard(Zone.BATTLEFIELD, playerA, solRing);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ensoulArtifact);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcaneFlight);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertBasePowerToughness(playerA, solRing, 6, 6);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, solRing, 5, 5);
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
}
