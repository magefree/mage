package org.mage.test.cards.single.dmu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.lang.annotation.Target;

/**
 * {@link mage.cards.t.ThranPortal Thran Portal}
 * Land Gate
 * Thran Portal enters the battlefield tapped unless you control two or fewer other lands.
 * As Thran Portal enters the battlefield, choose a basic land type.
 * Thran Portal is the chosen type in addition to its other types.
 * Mana abilities of Thran Portal cost an additional 1 life to activate.
 *
 * @author Alex-Vasile
 */
public class ThranPortalTest extends CardTestPlayerBase {

    private static final String thranPortal = "Thran Portal";

    /**
     * Test that tapping it for mana deals damage.
     * Also tests that it comes in untapped if you control 2 of fewer lands.
     */
    @Test
    public void dealsDamage() {
        String lightningBolt = "Lightning Bolt";
        addCard(Zone.HAND, playerA, thranPortal);
        addCard(Zone.HAND, playerA, lightningBolt);

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, thranPortal);
        setChoice(playerA, "Thran");
        setChoice(playerA, "Mountain");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1); // 1 life for tapping it
        assertLife(playerB, 20 - 3); // 3 life from lightning bolt
    }


    /**
     * Test that it gets properly seen as the land type that was chosen.
     * Also tests that it comes in tapped when you control 3 or more lands.
     */
    @Test
    public void seenAsChoice() {
        // Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains,
        // you may have Valakut, the Molten Pinnacle deal 3 damage to any target.
        String valakut = "Valakut, the Molten Pinnacle";

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, valakut);
        addCard(Zone.HAND, playerA, thranPortal);

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, thranPortal);
        setChoice(playerA, "Thran");
        setChoice(playerA, "Mountain");
        setChoice(playerA, "Yes");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertTapped(thranPortal, true);
        assertLife(playerB, 20 - 3);
    }

    /**
     * Test that the mana ability gained from Chromatic Lantern also costs 1 life.
     */
    @Test
    public void chromaticLanternCostInteraction() {
        // Lands you control have “{T}: Add one mana of any color.”
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Lantern");
        addCard(Zone.HAND, playerA, thranPortal);
        addCard(Zone.HAND, playerA, "Academy Loremaster");  // {U}{U}

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, thranPortal);
        setChoice(playerA, "Thran");
        setChoice(playerA, "Mountain"); // Set mountain so that it must use the ability given by chromatic lantern to get the {U}

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Academy Loremaster");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 19); // Should have lost life from tapping Thran Portal to use the ability chromatic lantern gave it
        assertPermanentCount(playerA, "Academy Loremaster" , 1);
    }


    /**
     * Tests that Manascape Refractor copies the Thran Portal's mana abilities, but not the additional 1 life cost.
     */
    @Test
    public void manascapeRefractorInteraction() {
        addCard(Zone.HAND, playerA, thranPortal);
        addCard(Zone.HAND, playerA, "Academy Loremaster");  // {U}{U}
        // Manascape Refractor enters the battlefield tapped.
        // Manascape Refractor has all activated abilities of all lands on the battlefield.
        // You may spend mana as though it were mana of any color to pay the activation costs of Manascape Refractor’s abilities.
        addCard(Zone.BATTLEFIELD, playerA, "Manascape Refractor");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, thranPortal);
        setChoice(playerA, "Thran");
        setChoice(playerA, "Island"); // Both Thran portal and Manascape will not tap for blue

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Academy Loremaster");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1); // Lost one life for Thran portal BUT NOT for Manascape Refractor
        assertPermanentCount(playerA, "Academy Loremaster" , 1);
    }

}
