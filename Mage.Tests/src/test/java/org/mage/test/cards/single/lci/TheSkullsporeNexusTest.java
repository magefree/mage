package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheSkullsporeNexusTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheSkullsporeNexus} <br>
     * The Skullspore Nexus {6}{G}{G} <br>
     * Legendary Artifact <br>
     * This spell costs {X} less to cast, where X is the greatest power among creatures you control. <br>
     * Whenever one or more nontoken creatures you control die, create a green Fungus Dinosaur creature token with base power and toughness each equal to the total power of those creatures. <br>
     * {2}, {T}: Double target creature’s power until end of turn. <br>
     */
    private static final String nexus = "The Skullspore Nexus";

    @Test
    public void test_trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, nexus);
        addCard(Zone.BATTLEFIELD, playerA, "Butcher Ghoul"); // 1/1 undying
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 doesn't count as not controlled
        addCard(Zone.HAND, playerA, "Grave Titan"); // 6/6, etb with 2 2/2 tokens
        addCard(Zone.HAND, playerA, "Damnation", 2); // destroy all creatures.

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6 + 4 * 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grave Titan", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Damnation", true);
        setChoice(playerA, "Whenever one or more nontoken creatures you control die"); // ordering triggers
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Only 2 creatures do count: 6 from Titan + 1 from Ghoul
        checkPT("after first damnation", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fungus Dinosaur Token", 7, 7);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Damnation", true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // only undying ghoul counts (for 2)
        assertPowerToughness(playerA, "Fungus Dinosaur Token", 2, 2);
    }

    @Test
    public void test_activation() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, nexus);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Double", "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 4, 2);
        assertTappedCount("Swamp", true, 2);
        assertTapped(nexus, true);
    }

    // This test is there as some of the cost reduction code has issue with
    // counting/checking for the right player, when you cast a card you don't
    // own.
    @Test
    public void test_costreduction_opp_card() {
        setStrictChooseMode(true);

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerB, nexus);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // B has a 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // A has a 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 5 + 2);
        addCard(Zone.HAND, playerA, "Underground Sea");

        // Siphon Insight {U}{B} -- Instant
        // Look at the top two cards of target opponent's library.
        // Exile one of them face down and put the other on the bottom of that library.
        // You may look at and play the exiled card for as long as it remains exiled,
        // and you may spend mana as though it were mana of any color to cast that spell.
        addCard(Zone.HAND, playerA, "Siphon Insight");

        castSpell(1, PhaseStep.UPKEEP, playerA, "Siphon Insight", playerB);
        setChoice(playerA, nexus); // choose nexus to exile

        // Nexus should cost 4GG, not 3GG
        checkPlayableAbility("can't cast with 5 mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nexus, false);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Underground Sea");
        checkPlayableAbility("can cast with 6 mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nexus, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nexus);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Underground Sea", true, 8);
    }
}
