package org.mage.test.cards.single.khc;

import mage.abilities.keyword.ForecastAbility;
import mage.cards.basiclands.Mountain;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.e.EtherealValkyrie Ethereal Valkyrie}
 * <p>
 * Whenever Ethereal Valkyrie enters the battlefield or attacks, draw a card, then exile a card from your hand face down.
 * It becomes foretold.
 * Its foretell cost is its mana cost reduced by {2}.
 * (On a later turn, you may cast it for its foretell cost, even if this creature has left the battlefield.)
 *
 * @author Alex-Vasile
 */
public class EtherealValkyrieTest extends CardTestPlayerBase {

    // {4}{W}{U}
    private static final String etherealValkyrie = "Ethereal Valkyrie";
    // Suspend 4—{U}
    // Target player draws three cards.
    private static final String ancestralVision = "Ancestral Vision";
    // {5}{R} Creature-Land MDFC
    private static final String akoumWarrior = "Akoum Warrior";
    private static final String akoumTeeth = "Akoum Teeth";
    // {3}{U}{U}-{1}{U} Creature-Creature MDFC
    private static final String alrund = "Alrund, God of the Cosmos";
    private static final String hakka = "Hakka, Whispering Raven";
    // MDFC with where both sides are lands
    private static final String blightclimbPathway = "Brightclimb Pathway";
    private static final String grimclimbPathway = "Grimclimb Pathway";
    // {3}
    // {T}: Add one mana of any color
    private static final String alloyMyr = "Alloy Myr";
    // Land
    private static final String exoticOrchard = "Exotic Orchard";

    /**
     * Test that a regular card is playable.
     */
    @Test
    public void testRegularCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, etherealValkyrie);
        addCard(Zone.HAND, playerA, alloyMyr); // The one to exile with ETB ability

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, etherealValkyrie);
        addTarget(playerA, alloyMyr);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, alloyMyr, 1);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, alloyMyr, 0);
        assertPermanentCount(playerA, alloyMyr, 1);
    }

    /**
     * Reported Bug: When you only have lands in hand the game enters a permanent rollback state.
     * https://github.com/magefree/mage/issues/9361
     */
    @Test
    public void testLand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, etherealValkyrie);
        addCard(Zone.HAND, playerA, exoticOrchard);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, etherealValkyrie);
        addTarget(playerA, exoticOrchard);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, exoticOrchard, 1);

        checkPlayableAbility("Can't fortell land", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell", false);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
    }

    /**
     * MDFC cards where both sides are lands should be exiled, but not fortell-able.
     */
    @Test
    public void testMDFCDualLand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, etherealValkyrie);

        addCard(Zone.HAND, playerA, blightclimbPathway);


        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, etherealValkyrie);
        addTarget(playerA, blightclimbPathway);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, blightclimbPathway, 1);
        checkPlayableAbility("Can't fortell land", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell", false);
    }

    /**
     * MDFC cards where only one side is a land should let you fortell its non-land side.
     */
    @Test
    public void testMDFCNonLandLand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, etherealValkyrie, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, akoumWarrior);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, etherealValkyrie);
        addTarget(playerA, akoumWarrior);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, akoumWarrior, 1);

        // TODO: Add functionality to test for this programmatically by changing assertAbilityCount
        showAvailableAbilities("Should only be 1 Foretell ability", 3, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {3}{R}");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, akoumWarrior, 0);
        assertPermanentCount(playerA, akoumWarrior, 1);
    }

    /**
     * MDFC cards where only one side is a land should let you fortell its non-land side.
     */
    @Test
    public void testMDFCCreatureCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.HAND, playerA, etherealValkyrie, 2);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, alrund);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, alrund);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, etherealValkyrie);
        addTarget(playerA, alrund);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, etherealValkyrie);
        addTarget(playerA, alrund);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, alrund, 2);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {1}");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {U}");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, alrund, 0);
        assertPermanentCount(playerA, alrund, 1);
        assertPermanentCount(playerA, hakka, 1);
    }

    /**
     * Test a Suspend card, which should not be playable from exile with foretell.
     */
    @Test
    public void testSuspendCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, etherealValkyrie);
        addCard(Zone.HAND, playerA, ancestralVision);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, etherealValkyrie);
        addTarget(playerA, ancestralVision);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(playerA, ancestralVision, 1);

        checkPlayableAbility("Can't fortell suspend-only card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell", false);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
    }
}
